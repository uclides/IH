package org.androidanalyzer.plugins.device;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.os.Build;

/**
 * DeviceInfoPlugin is used to gather information about general device properties
 * like the device manufacturer and model, branding (if any) and a rich set of 
 * firmware details, like the firmware, kernel and baseband versions, bootloader, etc.
 * 
 * @author Tsvetan Nachev
 *
 */
public class DeviceInfoPlugin extends AbstractPlugin {

  /*
   * Static plugin metadata constants
   */

  private static final String BOOTLOADER_FIELD = "BOOTLOADER";
  private static final String HARDWARE_FIELD = "HARDWARE";
  private static final String MANUFACTURER_FIELD = "MANUFACTURER";
  private static final String PLUGIN_VERSION = "1.0.0";
  private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
  private static final long PLUGIN_TIMEOUT = 0;
  private static final String PLUGIN_NAME = "Device Info Plugin";
  private static final String PLUGIN_DESCRIPTION = "Collects data on general device properties";
  private static final String TAG = "Analyzer-DeviceInfoPlugin";
  /**
   * Holds the plugin status. Default value is passed. If error retrieving any of plugin properties occur,
   * status is set to indicate the error
   */
  private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;
  
  /**
   * Root node
   */
  private static final String DEVICE_NODE = "Device";

  /**
   * 1st level node
   */
  private static final String PRODUCT_NODE = "Product";

  /**
   * 1st level node
   */
  private static final String FIRMWARE_NODE = "Firmware";
  
  /**
   * 2nd level product key
   */
  private static final String MANUFACTURER_NAME = "Manufacturer name";

  /**
   * 2nd level product key
   */  
  private static final String DEVICE_MODEL = "Device model";

  /**
   * 2nd level product key
   */
  private static final String DEVICE_BRAND = "Device brand";
  
  /**
   * 2nd level firmware key
   */
  private static final String HARDWARE_NAME = "Hardware name";
  
  /**
   * 2nd level firmware key
   */
  private static final String FIRMWARE_VERSION = "Firmware version";
  
  /**
   * 2nd level firmware key
   */
  private static final String FIRMWARE_CONFIGURATION_VERSION = "Firmware configuration version";
  
  /**
   * 2nd level firmware key
   */
  private static final String KERNEL_VERSION_RAW = "Kernel version raw";
  
  /**
   * 2nd level firmware key
   */
  private static final String KERNEL_VERSION = "Kernel version";
  
  /**
   * 2nd level firmware key
   */
  private static final String BASEBAND_VERSION = "Baseband version";
  
  /**
   * 2nd level firmware key
   */
  private static final String BASEBAND_CONFIGURATION_VERSION = "Baseband configuration version";
  
  /**
   * 2nd level firmware key
   */
  private static final String BUILD_NUMBER = "Build number";
  
  /**
   * 2nd level firmware key
   */
  private static final String BUILD_FINGERPRINT = "Build fingerprint";
  
  /**
   * 2nd level firmware key
   */
  private static final String BOOTLOADER_VERSION = "Bootloader version";
  
  private static final String KERNEL_FORMAT_REGEXP = "\\w+\\s+" + /* ignore: Linux */
                                               "\\w+\\s+" + /* ignore: version */
                                               "([^\\s]+)\\s+" + /* group 1: 2.6.22-omap1 */
                                               "\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+" + /* group 2: (xxxxxx@xxxxx.constant) */
                                               "\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+" + /* ignore: (gcc ..) */
                                               "([^\\s]+)\\s+" + /* group 3: #26 */
                                               "(?:PREEMPT\\s+)?" + /* ignore: PREEMPT (optional) */
                                               "(.+)"; /* group 4: date */
  
  private static final String KERNEL_SOURCE = "/proc/version";
  
  private static final String BUILD_PROPERTIES_FILE = "/system/build.prop";
  
  private static final String BASEBAND_VERSION_PROP = "gsm.version.baseband";
  
  /**
   * Optional (found on Motorola Milestone)
   */
  private static final String BASEBAND_CONFIGURATION_VERSION_PROP = "ro.gsm.flexversion";
  
  private static final String FIRMWARE_CONFIGURATION_VERSION_PROP = "ro.build.config.version";
  
  private static final String FIRMWARE_BUILD_FINGERPRINT_PROP = "ro.build.fingerprint";
  
  private static boolean readPropsFromFile = false;
  
  private Properties buildProps;

  @Override
  protected Data getData() {
    Data device = new Data();
    try {
      device.setName(DEVICE_NODE);
    } catch (Exception e) {
      Logger.ERROR(TAG, "Error creating device node: "+e.getMessage(), e);
      status = "Could not create Device root node";
      return null;
    }
    ArrayList<Data> subnodes = new ArrayList<Data>();
    try {
      Data product = new Data();
      product.setName(PRODUCT_NODE);
      ArrayList<Data> productData = readProductData();
      addToParent(product, productData);
      subnodes.add(product);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error creating product subnodes: "+t.getMessage(), t);
      status = "Could not create Product subnode";
    }
    try {
      Data firmware = new Data();
      firmware.setName(FIRMWARE_NODE);
      ArrayList<Data> firmwareData = readFirmwareData();
      addToParent(firmware, firmwareData);
      subnodes.add(firmware);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error creating firmware subnodes: "+t.getMessage(), t);
      status = "Could not create Firmware subnode";
    }
    addToParent(device, subnodes);
    return device;
  }

  @Override
  protected String getPluginClassName() {
    return DeviceInfoPlugin.class.getName();
  }

  @Override
  public String getPluginDescription() {
    return PLUGIN_DESCRIPTION;
  }

  @Override
  public String getPluginName() {
    return PLUGIN_NAME;
  }

  @Override
  protected String getPluginStatus() {
    return status;
  }

  @Override
  public long getPluginTimeout() {
    return PLUGIN_TIMEOUT;
  }

  @Override
  public String getPluginVendor() {
    return PLUGIN_VENDOR;
  }

  @Override
  public String getPluginVersion() {
    return PLUGIN_VERSION;
  }

  @Override
  public boolean isPluginUIRequired() {
    return false;
  }

  @Override
  protected void stopDataCollection() {
    Logger.DEBUG(TAG, "Stopping data collection");
    this.stopSelf();
  }
  
  private String getDeviceManufacturer() {
    String mfg = Constants.NODE_VALUE_UNKNOWN;
    try {
      // using reflection
      Field manufacturerField = Build.class.getDeclaredField(MANUFACTURER_FIELD);
      manufacturerField.setAccessible(true);
      Object myManufacturer = manufacturerField.get(null);
      // value = android.os.Build.MANUFACTURER
      mfg = String.valueOf(myManufacturer);
    } catch (Exception ex) {
      mfg = Build.PRODUCT;
      Logger.WARNING(TAG, "Could not get Manufacturer!" + ex.getMessage(), ex);
    }
    return mfg;
  }  
  
  private Data getDeviceHardware() {
    Data bData = new Data();
    try {
      bData.setName(HARDWARE_NAME);
      if (isApiAvailable(8)) {
        String hardware = Constants.NODE_VALUE_UNKNOWN;
        Field hwField = Build.class.getDeclaredField(HARDWARE_FIELD);
        hwField.setAccessible(true);
        Object myHardware = hwField.get(null);
        hardware = String.valueOf(myHardware);
        bData.setValue(hardware);
      } else {
        bData.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
        bData.setStatus(Constants.NODE_STATUS_FAILED);
        bData.setValueType(Constants.NODE_VALUE_TYPE_STRING);
      }
    } catch (Throwable t) {
      Logger.WARNING(TAG, "Could not get Hardware: "+t.getMessage(), t);
    }
    return bData;
  }
  
  private Data getDeviceBootloader() {
    Data bData = new Data();
    try {
      bData.setName(BOOTLOADER_VERSION);
      if (isApiAvailable(8)) {
        String bootloader = Constants.NODE_VALUE_UNKNOWN;
        Field hwField = Build.class.getDeclaredField(BOOTLOADER_FIELD);
        hwField.setAccessible(true);
        Object myHardware = hwField.get(null);
        bootloader = String.valueOf(myHardware);
        bData.setValue(bootloader);
      } else {
        bData.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
        bData.setStatus(Constants.NODE_STATUS_FAILED);
        bData.setValueType(Constants.NODE_VALUE_TYPE_STRING);
      }
    } catch (Throwable t) {
      Logger.WARNING(TAG, "Could not get Bootloader: "+t.getMessage(), t);
    }
    return bData;
  }
  
  private boolean isApiAvailable(int since) {
    return getAPIVersion() >= since;
  }
  
  private ArrayList<Data> readFirmwareData() {
    ArrayList<Data> firmware = new ArrayList<Data>();
    try {
      Data fwVersion = new Data();
      fwVersion.setName(FIRMWARE_VERSION);
      fwVersion.setValue(android.os.Build.VERSION.RELEASE);
      firmware.add(fwVersion);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading device firmware version: "+t.getMessage(), t);
      status = "Could not create firmware version node";
    }
    try {
      Data fwConfigVersion = new Data();
      fwConfigVersion.setName(FIRMWARE_CONFIGURATION_VERSION);
      String value = getSystemProperty(FIRMWARE_CONFIGURATION_VERSION_PROP);
      if (value == null || value.length() == 0) {
        fwConfigVersion.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
        fwConfigVersion.setStatus(Constants.NODE_STATUS_FAILED);
        fwConfigVersion.setValueType(Constants.NODE_VALUE_TYPE_STRING);
      } else {
        fwConfigVersion.setValue(value);
      }
      firmware.add(fwConfigVersion);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading firmware cofiguration version: "+t.getMessage(), t);
      status = "Could not create firmware configuration version node";
    }
    String kernelRawVersion = readKernelVersionRaw();
    try {
      Data kernelRaw = new Data();
      kernelRaw.setName(KERNEL_VERSION_RAW);
      kernelRaw.setValue(kernelRawVersion);
      firmware.add(kernelRaw);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading kernel raw version: "+t.getMessage(), t);
      status = "Could not create kernel raw version node";
    }
    try {
      Data kernel = new Data();
      kernel.setName(KERNEL_VERSION);
      kernel.setValue(formatKernelVersion(kernelRawVersion));
      firmware.add(kernel);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error formatting kernel version: "+t.getMessage(), t);
      status = "Could not create kernel version node";
    }
    try {
      Data baseband = new Data();
      baseband.setName(BASEBAND_VERSION);
      String value = getSystemProperty(BASEBAND_VERSION_PROP);
      baseband.setValue(value != null && value.length() > 0 ? value : Constants.NODE_VALUE_UNKNOWN);
      firmware.add(baseband);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading baseband version: "+t.getMessage(), t);
      status = "Could not create baseband version node";
    }
    try {
      Data basebandConfigVersion = new Data();
      basebandConfigVersion.setName(BASEBAND_CONFIGURATION_VERSION);
      String value = getSystemProperty(BASEBAND_CONFIGURATION_VERSION_PROP);
      if (value == null || value.length() == 0) {
        basebandConfigVersion.setStatus(Constants.NODE_STATUS_FAILED);
        basebandConfigVersion.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
        basebandConfigVersion.setValueType(Constants.NODE_VALUE_TYPE_STRING);
      } else {
        basebandConfigVersion.setValue(value);
      }
      firmware.add(basebandConfigVersion);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading baseband configuration version: "+t.getMessage(), t);
      status = "Could not create baseband configuration version node";
    }
    try {
      Data build = new Data();
      build.setName(BUILD_NUMBER);
      build.setValue(android.os.Build.DISPLAY);
      firmware.add(build);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading build number: "+t.getMessage(), t);
      status = "Could not create build number node";
    }    
    try {
      Data buildFingerprint = new Data();
      buildFingerprint.setName(BUILD_FINGERPRINT);
      String value = getSystemProperty(FIRMWARE_BUILD_FINGERPRINT_PROP);
      buildFingerprint.setValue(value != null && value.length() > 0 ? value : Constants.NODE_VALUE_UNKNOWN);
      firmware.add(buildFingerprint);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading build fingerprint: "+t.getMessage(), t);
      status = "Could not create build fingerpint node";
    }    
    try {
      Data bootloader = getDeviceBootloader();
      firmware.add(bootloader);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading bootloader version: "+t.getMessage(), t);
      status = "Could not create bootloader version node";
    }    
    return firmware;
  }
  
  private ArrayList<Data> readProductData() {
    ArrayList<Data> product = new ArrayList<Data>();
    try {
      Data manufacturer = new Data();
      manufacturer.setName(MANUFACTURER_NAME);
      manufacturer.setValue(getDeviceManufacturer());
      product.add(manufacturer);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading manufacturer properties: "+t.getMessage(), t);
      status = "Could not create device manufacturer node";
    }
    try {
      Data model = new Data();
      model.setName(DEVICE_MODEL);
      model.setValue(android.os.Build.MODEL);
      product.add(model);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading device model: "+t.getMessage(), t);
      status = "Could not create device model node";
    }
    try {
      Data brand = new Data();
      brand.setName(DEVICE_BRAND);
      brand.setValue(android.os.Build.BRAND);
      product.add(brand);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading device brand: "+t.getMessage(), t);
      status = "Could not create device brand node";
    }
    try {
      Data hardware = getDeviceHardware();
      product.add(hardware);
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading device hardware name: "+t.getMessage(), t);
      status = "Could not create device hardware node";
    }    
    return product;
  }

  private String getSystemProperty(String propKey) {
    String value = null;
    if (readPropsFromFile) {
      if (buildProps == null) {
        buildProps = new Properties();
        try {
          buildProps.load(new FileInputStream(BUILD_PROPERTIES_FILE));
        } catch (IOException e) {
          Logger.DEBUG(TAG, "Could not read build.prop file !", e);
        }
      }
      value = buildProps.getProperty(propKey);
    } else {
      try {
        Class sysPropClass = Class.forName("android.os.SystemProperties");
        Method getMethod = sysPropClass.getMethod("get", String.class);
        value = (String) getMethod.invoke(sysPropClass.newInstance(), propKey);
      } catch (Exception e) {
        Logger.DEBUG(TAG, "Could not get systemProperty : " + propKey, e);
      }
    }
    Logger.DEBUG(TAG, "system prop  " + propKey + " = " + value);
    return value;
  }
  
  private String readKernelVersionRaw() {
    String kernelVersionRaw = Constants.NODE_VALUE_UNKNOWN;
    try {
      BufferedReader bReader = new BufferedReader(new FileReader(KERNEL_SOURCE), 256);
      try {
        kernelVersionRaw = bReader.readLine();
      } finally {
        bReader.close();
      }
    } catch (Throwable t) {
      Logger.ERROR(TAG, "Error reading raw kernel version: "+t.getMessage(), t);
    }
    return kernelVersionRaw;
  }
  
  private String formatKernelVersion(String raw) {
    if (raw != null && raw.length() > 0 && !Constants.NODE_VALUE_UNKNOWN.equals(raw)) {
      try {
        Pattern p = Pattern.compile(KERNEL_FORMAT_REGEXP);
        Matcher m = p.matcher(raw);
        if (!m.matches()) {
          Logger.DEBUG(TAG, "Regex did not match on /proc/version: "+raw);
          return raw;
        } else if (m.groupCount() < 4) {
          Logger.DEBUG(TAG, "Regex returned only "+m.groupCount()+ "groups");
          return raw;
        } else {
          return (new StringBuilder(m.group(1)).append("\n").
                                    append(m.group(2)).append(" ").
                                    append(m.group(3)).append("\n").
                                    append(m.group(4))).toString();
        }
      } catch (Throwable t) {
        Logger.ERROR(TAG, "Error formatting raw kernel version: "+t.getMessage(), t);
      }      
    }
    return raw;
  }
}

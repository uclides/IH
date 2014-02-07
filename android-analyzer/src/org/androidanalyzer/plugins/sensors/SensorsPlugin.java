package org.androidanalyzer.plugins.sensors;

import java.util.ArrayList;
import java.util.List;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

/**
 * SensorsPlugin class used for gathering information about available sensors on the device.
 * 
 */
public class SensorsPlugin extends AbstractPlugin {

	private static final String NAME = "Sensors Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String TAG = "Analyzer-SensorsPlugin";
	private static final String SENSORS = "Sensors";
	/** Main sensors */
	private static final String SENSOR_NAME = "Name";
	private static final String SENSOR_UNKNOWN = "Unknown";
	private static final String ACCELEROMETER = "Accelerometer";
	private static final String PROXIMITY = "Proximity";
	private static final String GYROSCOPE = "Gyroscope";
	private static final String LIGHT = "Light";
	private static final String ORIENTATION = "Orientation";
	private static final String PRESSURE = "Pressure";
	private static final String TEMPERATURE = "Temperature";
	private static final String MAGNETIC = "Magnetic field";
	private static final String[] SENSOR_NAME_LIST = {SENSOR_UNKNOWN, ACCELEROMETER, MAGNETIC, ORIENTATION, GYROSCOPE, LIGHT, PRESSURE, TEMPERATURE, PROXIMITY};
	/** Sensors features */
	private static final String SENSORS_CNT = "Sensor-";
	private static final String MAX_RANGE = "Maximum range";
	private static final String SENSOR_UNIT_METRIC = "SU";
	private static final String POWER = "Power";
	private static final String POWER_METRIC = "mA";
	private static final String RESOLUTION = "Resolution";
	private static final String TYPE = "Type";
	private static final String TYPE_NAME = "Type name";
	private static final String VENDOR = "Vendor";
	private static final String VERSION = "Version";
	private static final String DESCRIPTION = "Collects data on available device sensors";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginTimeout()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginVersion()
	 */
	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginVendor()
	 */
	@Override
	public String getPluginVendor() {
		return PLUGIN_VENDOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginDescription()
	 */
	@Override
	public String getPluginDescription() {
		return DESCRIPTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#isPluginRequiredUI()
	 */
	@Override
	public boolean isPluginUIRequired() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getData()
	 */
	@Override
	protected Data getData() {
		Logger.DEBUG(TAG, "getData in Sensor Plugin");
		Data parent = new Data();
		ArrayList<Data> masterChildren = new ArrayList<Data>();
		try {
			parent.setName(SENSORS);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Sensor parent node!", e);
			status = "Could not set Sensor parent node!";
			return null;
		}
		SensorManager sensorMgr = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		masterChildren = getSensorInfo(sensorMgr, masterChildren);
		masterChildren = getSensorExtraInfo(sensorMgr, masterChildren);
		parent = addToParent(parent, masterChildren);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		Logger.DEBUG(TAG, "Service is stopped!");
		this.stopSelf();
	}

	private ArrayList<Data> getSensorInfo(SensorManager sensorMgr, ArrayList<Data> masterChildren) {
		Data accelerometer = new Data();
		try {
			accelerometer.setName(ACCELEROMETER);
			accelerometer.setValue(!sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty()
									? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			accelerometer.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Accelemoter");
			masterChildren.add(accelerometer);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set accelerometer sensor", e);
			status = "Failed to set accelerometer sensor";
		}

		Data proximity = new Data();
		try {
			proximity.setName(PROXIMITY);
			proximity.setValue(!sensorMgr.getSensorList(Sensor.TYPE_PROXIMITY).isEmpty()
								? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			proximity.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Proximity");
			masterChildren.add(proximity);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set proximity sensor", e);
			status = "Failed to set proximity sensor";
		}

		Data gyroscope = new Data();
		try {
			gyroscope.setName(GYROSCOPE);
			gyroscope.setValue(!sensorMgr.getSensorList(Sensor.TYPE_GYROSCOPE).isEmpty()
								? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			gyroscope.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Gyroscope");
			masterChildren.add(gyroscope);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set giroscope sensor", e);
			status = "Failed to set giroscope sensor";
		}

		Data light = new Data();
		try {
			light.setName(LIGHT);
			light.setValue(!sensorMgr.getSensorList(Sensor.TYPE_LIGHT).isEmpty()
							? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			light.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Light");
			masterChildren.add(light);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set light sensor", e);
			status = "Failed to set light sensor";
		}

		Data orientation = new Data();
		try {
			orientation.setName(ORIENTATION);
			orientation.setValue(!sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION).isEmpty()
									? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			orientation.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Orientation");
			masterChildren.add(orientation);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set orientation sensor", e);
			status = "Failed to set orientation sensor";
		}

		Data pressure = new Data();
		try {
			pressure.setName(PRESSURE);
			pressure.setValue(!sensorMgr.getSensorList(Sensor.TYPE_PRESSURE).isEmpty()
								? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			pressure.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Pressure");
			masterChildren.add(pressure);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set pressure sensor", e);
			status = "Failed to set pressure sensor";
		}

		Data temperature = new Data();
		try {
			temperature.setName(TEMPERATURE);
			temperature.setValue(!sensorMgr.getSensorList(Sensor.TYPE_TEMPERATURE).isEmpty()
									? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			temperature.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			masterChildren.add(temperature);
			Logger.DEBUG(TAG, "Temperature");
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set temperature sensor", e);
			status = "Failed to set temperature sensor";
		}

		Data magnetic = new Data();
		try {
			magnetic.setName(MAGNETIC);
			magnetic.setValue(!sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).isEmpty()
								? Constants.NODE_VALUE_YES : Constants.NODE_VALUE_NO);
			magnetic.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			Logger.DEBUG(TAG, "Magnetic");
			masterChildren.add(magnetic);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Failed to set magnetic sensor", e);
			status = "Failed to set magnetic sensor";
		}

		return masterChildren;
	}

	private ArrayList<Data> getSensorExtraInfo(SensorManager sensorMgr, ArrayList<Data> masterChildren) {
		Data parentSensorHolder;
		List<Sensor> allSensors = sensorMgr.getSensorList(Sensor.TYPE_ALL);
		int counter = 0;
		for (Sensor sensor : allSensors) {
			Data sensorHolder = new Data();
			ArrayList<Data> sensorExtraInfoChildren = new ArrayList<Data>();
			try {
				sensorHolder.setName(SENSORS_CNT + counter);

				Data snsName = new Data();
				snsName.setName(SENSOR_NAME);
				String sName = sensor.getName();
				snsName.setValue(sName);
				snsName.setStatus(Constants.NODE_STATUS_OK);
				snsName.setValueType(Constants.NODE_VALUE_TYPE_STRING);
				Logger.DEBUG(TAG, "Sensor name: " + sName);
				sensorExtraInfoChildren.add(snsName);

				Data snsMaxRange = new Data();
				snsMaxRange.setName(MAX_RANGE);
				float maxRange = sensor.getMaximumRange();
				snsMaxRange.setValue(String.valueOf(maxRange));
				snsMaxRange.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				snsMaxRange.setValueMetric(SENSOR_UNIT_METRIC);
				Logger.DEBUG(TAG, "Sensor max range: " + maxRange);
				sensorExtraInfoChildren.add(snsMaxRange);

				Data snsPower = new Data();
				snsPower.setName(POWER);
				float power = sensor.getPower();
				snsPower.setValue(String.valueOf(power));
				snsPower.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				snsPower.setValueMetric(POWER_METRIC);
				Logger.DEBUG(TAG, "Sensor power: " + power);
				sensorExtraInfoChildren.add(snsPower);

				Data snsResolution = new Data();
				snsResolution.setName(RESOLUTION);
				float resolution = sensor.getResolution();
				snsResolution.setValue(String.valueOf(resolution));
				snsResolution.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				snsResolution.setValueMetric(SENSOR_UNIT_METRIC);
				Logger.DEBUG(TAG, "Sensor resolution: " + resolution);
				sensorExtraInfoChildren.add(snsResolution);

				Data snsType = new Data();
				snsType.setName(TYPE);
				int type = sensor.getType();
				snsType.setValue(String.valueOf(type));
				snsType.setValueType(Constants.NODE_VALUE_TYPE_INT);
				Logger.DEBUG(TAG, "Sensor type: " + type);
				sensorExtraInfoChildren.add(snsType);

				String typeName = getSensorNameByType(type);
				if (typeName != null ) {
					Data snsTypeName = new Data();
					snsTypeName.setName(TYPE_NAME);
					snsTypeName.setValue(typeName);
					snsTypeName.setValueType(Constants.NODE_VALUE_TYPE_STRING);
					Logger.DEBUG(TAG, "Sensor type name: " + typeName);
					sensorExtraInfoChildren.add(snsTypeName);
				}
				
				Data snsVendor = new Data();
				snsVendor.setName(VENDOR);
				String vendor = sensor.getVendor();
				snsVendor.setValue(vendor);
				snsVendor.setValueType(Constants.NODE_VALUE_TYPE_STRING);
				Logger.DEBUG(TAG, "Sensor vendor: " + vendor);
				sensorExtraInfoChildren.add(snsVendor);

				Data snsVersion = new Data();
				snsVersion.setName(VERSION);
				int version = sensor.getVersion();
				snsVersion.setValue(String.valueOf(version));
				snsVersion.setValueType(Constants.NODE_VALUE_TYPE_INT);
				Logger.DEBUG(TAG, "Sensor version: " + version);
				sensorExtraInfoChildren.add(snsVersion);

				parentSensorHolder = addToParent(sensorHolder, sensorExtraInfoChildren);
				masterChildren.add(parentSensorHolder);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Sensor node!", e);
			}
			counter++;
		}
		return masterChildren;
	}
	
	public String getSensorNameByType(int type) {
		return type >= 0 && type < SENSOR_NAME_LIST.length ? SENSOR_NAME_LIST[type] : null;
	}

}

package org.androidanalyzer.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.androidanalyzer.Constants;
import org.androidanalyzer.R;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.transport.Reporter;
import org.androidanalyzer.transport.Reporter.Response;
import org.androidanalyzer.transport.impl.json.HTTPJSONReporter;
import org.androidanalyzer.transport.impl.json.JSONFormatter;
import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

/**
 * Core class for managing plugins and performing analysis on the device.
 * 
 */
public class AnalyzerCore {

	private static final String TAG = "Analyzer-Core";
	private static int TIME_TO_WAIT_FOR_PLUGIN_CONNECTION;
	private static int DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
	private static int DEFAULT_MIN_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
	private static AnalyzerCore core;

	/* Context to be used for communication with the plugins */
	private Context ctx;
	/* UI callback for communication with the UI */
	private UICallback uiCallb;
	private Executor exec;

	private ArrayList<String> pluginCache = new ArrayList<String>();
	private ArrayList<String> sortedEnabledPlugins;
	private PluginServiceConnection runningPluginConn = null;
	private boolean stopAnalyzing = false;
	private boolean pluginAnalyzing = false;
	private Data root = null;
	private Data reportPlugins = null;
	private Data reportMetadata = null;
	private Data tempReport = null;
	private UninstallBReceiver unRecv = null;
	private int uiPluginsCounter = 0;
	private int sdkVersion = 0;
	private boolean setStatus = false;

	public static AnalyzerCore getInstance() {
		if (core == null)
			core = new AnalyzerCore();
		return core;
	}

	/**
	 * Initializes the Core
	 * 
	 * @param ctx
	 *          Android Context for plugin communication
	 */
	public void init(Context ctx) {
		// AnalyzerCore.core = this;
		this.ctx = ctx;
		this.uiCallb = null;
		readProperties();
		Intent regPluginsIntent = new Intent();
		regPluginsIntent.setAction(Constants.PLUGINS_DISCOVERY_INTENT);
		ctx.sendBroadcast(regPluginsIntent);
		exec = Executors.newFixedThreadPool(5);

		/*
		 * registering braodcast receiver to handle uninstalled external plugins
		 */
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		unRecv = new UninstallBReceiver();
		ctx.registerReceiver(unRecv, filter);
	}

	/**
	 * Sets UI callback for sending updates to the UI
	 * 
	 * @param uiCallb
	 *          UI object implementing UICallback class
	 */
	public void setUICallback(UICallback uiCallb) {
		this.uiCallb = uiCallb;
	}

	/**
	 * Can be called to clear cached content from the Core
	 */
	public void clearCachedContent() {
		if (unRecv != null) {
			ctx.unregisterReceiver(unRecv);
		}
	}

	public void readFromCache() {
		cleanReport();
		Logger.DEBUG(TAG, "Read from chache");
		ArrayList<String> enabledPlugins = new ArrayList<String>();
		if (pluginCache != null) {
			SharedPreferences prefs = ctx.getSharedPreferences(Constants.AA_STATUS_PREFS, 0);
			String record;
			PluginStatus decoded;
			for (String pluginClass : pluginCache) {
				record = prefs.getString(pluginClass, null);
				if (record != null) {
					decoded = PluginStatus.decodeStatus(record);
					if (decoded != null && decoded.isEnabled()) {
						enabledPlugins.add(pluginClass);
					}
				} else {// If no record is found in prefs. we assume plugin is enabled
					// This will happen when analyzer is started for the very first time
					enabledPlugins.add(pluginClass);
				}
			}
		}
		Logger.DEBUG(TAG, "Non sorted enabledPlugins - " + enabledPlugins);
		ArrayList<String> sortedEnabledPlugins = sortPluginList(enabledPlugins);
		Logger.DEBUG(TAG, "Sorted enabledPlugins - " + sortedEnabledPlugins);
	}

	/**
	 * Starts Analysis in the Core. Each plugin is taken from the registry Cache
	 * and called for analysis.
	 * 
	 * @return Data object containing report from all the plugins that were
	 *         registered
	 */
	public Data startAnalyzing() {
		Logger.DEBUG(TAG, "Start Analyzing");
		Hashtable<String, Object> progressValues = null;
		int pluginCounter = 0;
		Data plugins = null;
		String status = null;
		if (uiCallb != null)
			progressValues = new Hashtable<String, Object>(5);
		Logger.DEBUG(TAG, "pluginCache : " + pluginCache);
		int size = sortedEnabledPlugins.size();
		// Updating UI on Analysis start
		if (progressValues != null) {
			progressValues.put(UICallback.NUMBER_OF_PLUGINS, size);
			uiCallb.updateAnalysisProgress(progressValues);
		}
		String pluginName = null;
		String description = "";

		if (size > 0) {
			for (String plugin : sortedEnabledPlugins) {
				Data currentPlugin = null;
				runningPluginConn = connectToPlugin(plugin);
				if (runningPluginConn != null && runningPluginConn.plugin != null) {
					try {
						currentPlugin = getPluginMetaData(runningPluginConn.plugin, pluginCounter++);
						runningPluginConn.plugin.setDebugEnabled(Logger.getDebug());
					} catch (RemoteException exp) {
						Logger.DEBUG(TAG, "Error while trying to activate debug for plugin : " + plugin);
					}
					try {
						/* Updating UI on plugin Analysis start */
						if (progressValues != null) {
							progressValues.remove(UICallback.PLUGIN_FAILED);
							progressValues.remove(UICallback.PLUGIN_FINISHED_ANALYZING);
							progressValues.put(UICallback.NAME_OF_PLUGIN, runningPluginConn.plugin.getName());
							progressValues.put(UICallback.PLUGIN_STARTED_ANALYZING, true);
							uiCallb.updateAnalysisProgress(progressValues);
						}
					} catch (RemoteException e1) {
					}
					boolean isPluginRequiredUI = false;
					try {
						isPluginRequiredUI = runningPluginConn.plugin.isUIRequired();
					} catch (RemoteException e) {
						e.printStackTrace();
						Logger.ERROR(TAG, "Could not get if Plugin required UI!");
					}
					long timeout = DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
					try {
						timeout = runningPluginConn.plugin.getTimeout();
					} catch (RemoteException e) {
						Logger.ERROR(TAG, "Could not get timeout for plugin!");
						timeout = DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
					}
					if (timeout > DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION)
						timeout = DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
					if (timeout <= 100)
						timeout = DEFAULT_MIN_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION;
					tempReport = null;
					pluginAnalyzing = true;
					exec.execute(new PluginAnalysisHandler(runningPluginConn.plugin, progressValues, currentPlugin));
					if (!isPluginRequiredUI) {
						Logger.DEBUG(TAG, "Waiting with TIME OUT");
						while (pluginAnalyzing == true && tempReport == null && timeout > 0) {
							try {
								Thread.sleep(100);// UI-less plugins are usually fast
								timeout -= 100;
							} catch (InterruptedException e) {
								Logger.ERROR(TAG, "Could not Sleep thread in Core!");
							}
						}
					} else {
						Logger.DEBUG(TAG, "Waiting without TIME OUT");
						while (pluginAnalyzing == true && tempReport == null) {
							try {
								Thread.sleep(250);// UI plugins take more time
							} catch (InterruptedException e) {
								Logger.ERROR(TAG, "Could not Sleep thread in Core!");
							}
						}
					}
					try {
						status = runningPluginConn.plugin.getStatus();
						pluginName = runningPluginConn.plugin.getName();
						description = runningPluginConn.plugin.getDescription();
					} catch (RemoteException e1) {
						Logger.ERROR(TAG, "Failed to get for PluginInfo", e1);
					}
					updatePluginStatus(pluginName, plugin, status, description);
					if (plugins == null) {
						plugins = new Data();
						try {
							plugins.setName(Constants.METADATA_PLUGINS);
						} catch (Exception e) {
							Logger.ERROR(TAG, "Could not set Metadata!", e);
						}
					}
					// Data currentPlugin = getPluginMetaData(runningPluginConn.plugin, pluginCounter++);
					currentPlugin = setPluginStatus(currentPlugin, status);
					try {
						plugins.setValue(currentPlugin);
					} catch (Exception e) {
						Logger.ERROR(TAG, "Could not set Metadata!", e);
					}
					ctx.unbindService(runningPluginConn);
					if (tempReport != null) {
						addDataToReport(tempReport);
					} else {
						Logger.DEBUG(TAG, "tempReport : " + tempReport);
					}
				}
				/* Updating UI on plugin Analysis finish */
				if (progressValues != null) {
					progressValues.remove(UICallback.PLUGIN_STARTED_ANALYZING);
					progressValues.put(UICallback.PLUGIN_FINISHED_ANALYZING, true);
					uiCallb.updateAnalysisProgress(progressValues);
				}
				if (stopAnalyzing) {
					stopAnalyzing = false;
					break;
				}
			}
			if (reportMetadata == null) {
				reportMetadata = new Data();
				try {
					reportMetadata.setName(Constants.ROOT_METADATA);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set Metadata name!", e);
				}
			}
			if (plugins != null) {
				try {
					reportMetadata.setValue(plugins);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set Metadata!", e);
				}
			}
			runningPluginConn = null;
		}
		/* Create Mandatory data for the report */
		TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			try {
				String value = null;
				Data manufacturer = new Data();
				manufacturer.setName(Constants.M_MANUFACTURER);
				try {
					// using reflection
					Field manufacturerField = Build.class.getDeclaredField("MANUFACTURER");
					manufacturerField.setAccessible(true);
					Object myManufacturer = manufacturerField.get(null);
					// value = android.os.Build.MANUFACTURER
					value = String.valueOf(myManufacturer);
				} catch (Exception ex) {
					value = Build.PRODUCT;
					Logger.WARNING(TAG, "Could not get Manufacturer!" + ex.toString());
				}
				Logger.DEBUG(TAG, "Manufacturer is " + value);
				manufacturer.setValue(value);
				Logger.DEBUG(TAG, "Brand: " + Build.BRAND);
				Logger.DEBUG(TAG, "Device: " + Build.DEVICE);
				reportMetadata.setValue(manufacturer);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Manufacturer data!", e);
			}

			try {
				Data deviceModel = new Data();
				deviceModel.setName(Constants.M_DEVICE_MODEL);
				String model = Build.MODEL;
				deviceModel.setValue(model);
				Logger.DEBUG(TAG, "Model is " + model);
				reportMetadata.setValue(deviceModel);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Device Model data!", e);
			}

			try {
				Data apiLevel = new Data();
				apiLevel.setName(Constants.M_ANDROID_API_LEVEL);
				int apiVersion = getAPIVersion();
				apiLevel.setValue("" + apiVersion);
				Logger.DEBUG(TAG, "API Level is " + apiVersion);
				reportMetadata.setValue(apiLevel);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set API Level data!", e);
			}

			try {
				Data firmwareVersion = new Data();
				firmwareVersion.setName(Constants.M_FIRMWARE_VERSION);
				String model = Build.VERSION.RELEASE;
				firmwareVersion.setValue(model);
				Logger.DEBUG(TAG, "Firmware version is " + model);
				reportMetadata.setValue(firmwareVersion);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Firmware version data!", e);
			}

			try {
				Data operatorName = new Data();
				operatorName.setName(Constants.M_OPERATOR);
				String name = telephonyManager.getNetworkOperatorName();
				Logger.DEBUG(TAG, "Operator name is " + name);
				if (name == null || name.length() == 0) {
					name = Constants.NODE_VALUE_UNKNOWN;
				}
				operatorName.setValue(name);
				reportMetadata.setValue(operatorName);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Operator name data!", e);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Mandatory data!", e);
		}

		/* Create Metadata - Device ID */

		String deviceID = telephonyManager.getDeviceId();
		Data device = new Data();
		try {
			device.setName(Constants.METADATA_DEVICE);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Metadata!", e);
		}
		// hack: the following line is a fallback for a case when the emulator
		// returns null for device ID
		deviceID = deviceID == null ? "" + System.currentTimeMillis() : deviceID;

		if (reportPlugins != null) {
			String md5 = Reporter.mD5H(deviceID.getBytes());
			try {
				device.setValue(md5);
				device.setValueMetric(Constants.METADATA_DEVICE_ID_METRIC);
				reportMetadata.setValue(device);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Metadata!", e);
			}
		} else {
			Logger.ERROR(TAG, "Device ID Check Failed");
		}

		/* Create Metadata - Analyzer Version */
		try {
			Data analyzerVersion = new Data();
			analyzerVersion.setName(Constants.METADATA_ANALYZER);
			Data version = new Data();
			String versionS = ctx.getResources().getString(R.string.app_version_name);
			version.setName(Constants.METADATA_ANALYZER_VERSION);
			version.setValue(versionS);
			analyzerVersion.setValue(version);
			Logger.DEBUG(TAG, "Version is: " + versionS);
			reportMetadata.setValue(analyzerVersion);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Metadata!", e);
		}

		/* Create Metadata - Date and Time */
		try {
			Data date = new Data();
			date.setName(Constants.METADATA_DATE);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date currentTime = new Date();
			String dateString = formatter.format(currentTime);
			date.setValue(dateString);
			date.setValueMetric(Constants.METADATA_DATE_METRIC);
			Logger.DEBUG(TAG, "Date is: " + dateString);
			reportMetadata.setValue(date);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Metadata!", e);
		}
		Logger.DEBUG(TAG, "Core finished Analysis!");
		Logger.DEBUG(TAG, "Report : " + reportPlugins);
		try {
			if (root == null)
				root = new Data();
			root.setName(Constants.ROOT);
			root.setValue(reportPlugins);
			root.setValue(reportMetadata);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not create final report!", e);
		}
		for (String plugin : pluginCache) {
			Logger.DEBUG(TAG, "Clas name: " + plugin);
		}
		return root;
	}

	/**
	 * Gets the current API version as an integer value
   * TODO: pull out the same code from AbstractPlugin.getAPIversion to the same place
	 * 
	 * @return the current API version as an int value
	 */
	public int getAPIVersion() {
		if (sdkVersion > 0) {
			return sdkVersion;
		}
		String SDK_INT_FIELD = "SDK_INT";
		String SDK_STRING_FIELD = "SDK";
		try {
			Field sdkField = android.os.Build.VERSION.class.getDeclaredField(SDK_INT_FIELD);
			if (sdkField != null) {
				sdkVersion = sdkField.getInt(android.os.Build.VERSION.class.newInstance());
				Logger.DEBUG(TAG, "API version : " + sdkVersion);
				if (sdkVersion > 0)
					return sdkVersion;
			}
		} catch (Exception e) {
			Logger.DEBUG(TAG, "Could not get SDK_INT field for sdk version !");
		}

		try {
			Field sdkField = android.os.Build.VERSION.class.getDeclaredField(SDK_STRING_FIELD);
			if (sdkField != null) {
				Object temp = sdkField.get(android.os.Build.VERSION.class.newInstance());
				if (temp instanceof String) {
					String sdkVerString = (String) temp;
					try {
						sdkVersion = Integer.parseInt(sdkVerString);
						Logger.DEBUG(TAG, "API version : " + sdkVersion);
						if (sdkVersion > 0)
							return sdkVersion;
					} catch (Exception e) {
						Logger.DEBUG(TAG, "Could not get API version from sdkVerString : " + sdkVerString);
					}
				}
			}
		} catch (Exception e) {
			Logger.DEBUG(TAG, "Could not get SDK_INT field for sdk version !");
		}
		Logger.DEBUG(TAG, "Could not get API version !");
		return 0;
	}

	/**
	 * Stops ongoing Analysis from the Core
	 */
	public void stopAnalyzing() {
		stopAnalyzing = true;
		synchronized (runningPluginConn) {
			if (runningPluginConn != null && runningPluginConn.plugin != null)
				try {
					runningPluginConn.plugin.stopAnalysis();
				} catch (RemoteException e) {
					Logger.ERROR(TAG, "Plugin disconected during stopAnalysis", e);
				}
		}
	}

	/**
	 * Sends report from analysis to server
	 * 
	 * @throws Exception
	 */
	public Response sendReport(Data data, URL host, Hashtable extra) throws Exception {
		if (data != null && host != null) {
			HTTPJSONReporter reporter = new HTTPJSONReporter();
			return reporter.send(data, host, extra);
		}
		return null;
	}

	/**
	 * Save report to local file
	 * 
	 * @return fName - Saved file name of the report
	 */
	public String writeToFile(Data data) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		String fName = null;
		JSONObject jObject = JSONFormatter.format(data);
		Logger.DEBUG(TAG, "[send] jObject: " + jObject);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
			Date currentTime = new Date();
			String dateString = formatter.format(currentTime);
			fName = Constants.FILE_NAME + "-" + dateString + ".txt";
			File root = Environment.getExternalStorageDirectory();
			if (root != null && root.canWrite()) {
				Logger.DEBUG(TAG, "SD card is available");
				File sdfile = new File(root, fName);
				fos = new FileOutputStream(sdfile);
				fName = " sdcard/" + fName;
			} else {
				Logger.DEBUG(TAG, "SD card is not available");
				fos = ctx.openFileOutput(fName, Context.MODE_PRIVATE);
				fName = " data/data/org.androidanalyzer/files/" + fName;
			}
			osw = new OutputStreamWriter(fos);
			osw.write(jObject.toString());
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				fos.close();
			} catch (IOException e) {
				Logger.ERROR(TAG, "Failed to close streams!");
			}
		}
		return fName;
	}

	public int getPluginUIRequired() {
		return uiPluginsCounter;
	}

	/**
	 * Adds data to main Report in Core
	 * 
	 * @param data
	 *          to be added
	 */
	private void addDataToReport(Data data) {

		if (data != null) {
			Logger.DEBUG(TAG, "addDataToReport for plugin : " + data.getName());
			if (reportPlugins == null)
				createReport();

			Object temp = reportPlugins.getValue();
			ArrayList<Data> list = null;
			if (temp instanceof ArrayList<?>) {
				list = (ArrayList<Data>) temp;
			}
			if (list != null) {
				for (Data reportedData : list) {
					if (reportedData.getName() != null && data.getName() != null && reportedData.getName().equals(data.getName())) {
						combineReports(reportedData, data);
						return;
					}
				}
			}
			if (data.getName() != null && data.getValue() != null)
				try {
					reportPlugins.setValue(data);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Analyzer Core : could not attach to report data : " + data.getName(), e);
				}
		} else {
			Logger.ERROR(TAG, "Called addDataToReport with data : " + data);
		}

	}

	/**
	 * creates main report in Core
	 */
	private void createReport() {
		reportPlugins = new Data();
		try {
			reportPlugins.setName(Constants.ROOT_DATA);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Analyzer Core could not create report!");
		}
	}

	/**
	 * Cleans main Core report
	 */
	private void cleanReport() {
		root = null;
		reportPlugins = null;
		reportMetadata = null;
		tempReport = null;
		uiPluginsCounter = 0;
		sortedEnabledPlugins = null;
	}

	/**
	 * Used to combine reports from different plugins
	 * 
	 * @param oldData
	 *          Data object to be written into
	 * @param newData
	 *          new Data object(report) from which data to be taken and put into
	 *          origData object
	 */
	private void combineReports(Data oldData, Data newData) {
		if (oldData != null && newData != null) {
			if (oldData.getValue() instanceof String && newData.getValue() instanceof String) {
				try {
					oldData.setValue(newData.getValue());
				} catch (Exception e) {
					Logger.DEBUG(TAG, "AC : combineReports could not set data as value!");
				}
			}
			if (oldData.getValue() instanceof String && newData.getValue() instanceof ArrayList<?>
					|| oldData.getValue() instanceof ArrayList<?> && newData.getValue() instanceof String) {
				Logger.ERROR(TAG, "combineReports() Data objects with different value types!");
			}
			if (oldData.getValue() instanceof ArrayList<?> && newData.getValue() instanceof ArrayList<?>) {
				ArrayList<Data> oldList = (ArrayList<Data>) oldData.getValue();
				ArrayList<Data> newList = (ArrayList<Data>) newData.getValue();
				for (Data data : newList) {
					Data oldDataFound = null;
					for (Data oData : oldList) {
						if (oData.getName().equals(data.getName())) {
							oldDataFound = oData;
							break;
						}
					}
					if (oldDataFound != null) {
						combineReports(oldDataFound, newData);
					} else {
						try {
							oldData.setValue(data);
						} catch (Exception e) {
							Logger.ERROR(TAG, "AC : combineReports could not set Data as value!", e);
						}
					}
				}
			}
		}
	}

	/**
	 * Class implementation of IPluginRegistry aidl interface
	 */
	public class Registry extends IPluginRegistry.Stub {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IPluginRegistry#registerPlugin
		 * (org.androidanalyzer.core.IAnalyzerPlugin)
		 */
		@Override
		public void registerPlugin(String pluginClass) throws RemoteException {
			if (pluginClass != null && (pluginCache == null || !pluginCache.contains(pluginClass))) {
				if (pluginCache == null) {
					pluginCache = new ArrayList<String>();
				}
				pluginCache.add(pluginClass);
			}
			Logger.DEBUG(TAG, "registered plugin : " + pluginClass);
		}

	}

	/**
	 * Class used to return Registry class on connecton from plugin
	 */
	public static final class RegistryService extends Service {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.app.Service#onBind(android.content.Intent)
		 */
		@Override
		public IBinder onBind(Intent intent) {
			Logger.DEBUG(TAG, "onBind in RegistryService");
			if (IPluginRegistry.class.getName().equals(intent.getAction())) {
				Logger.DEBUG(TAG, "Returning Registry interface in Core");
				if (AnalyzerCore.core != null)
					return AnalyzerCore.core.getRegistry();
				return null;
			}
			return null;
		}

	}

	/**
	 * Returns new Registry object to be used for registering plugin
	 * 
	 * @return new Registry Object
	 */
	private Registry getRegistry() {
		return new Registry();
	}

	/**
	 * Connects to plugin via aidl and returns PluginServiceConnection for use
	 * 
	 * @param plugin
	 *          Plugin identity string for bindService
	 * @return
	 */
	private PluginServiceConnection connectToPlugin(String pluginClass) {

		PluginServiceConnection conn = new PluginServiceConnection();

		boolean connSuccess = ctx.bindService(new Intent(pluginClass), conn, Context.BIND_AUTO_CREATE);
		int timeToWait = TIME_TO_WAIT_FOR_PLUGIN_CONNECTION;
		while (conn.plugin == null && timeToWait > 0) {
			// Logger.DEBUG(TAG, "plugin connection : " +
			// conn.plugin);
			timeToWait -= 100;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return conn;
	}

	/**
	 * Helper class providing SericeConnection and IAnalyzerPlugin interface to
	 * plugin
	 */
	class PluginServiceConnection implements ServiceConnection {
		public IAnalyzerPlugin plugin = null;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.content.ServiceConnection#onServiceConnected
		 * (android.content.ComponentName, android.os.IBinder)
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			plugin = (IAnalyzerPlugin.Stub.asInterface((IBinder) service));
			if (uiCallb != null)
				uiCallb.notifyPluginRegistered(plugin);
			Logger.DEBUG(TAG, "Core Connected to plugin : " + name);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @seeandroid.content.ServiceConnection# onServiceDisconnected
		 * (android.content.ComponentName)
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

	};

	class PluginAnalysisHandler implements Runnable {

		private IAnalyzerPlugin plugin;
		private Hashtable progressValues;
		private Data currentPlugin;

		/**
		 * @param currentPlugin 
     * 
     */
		public PluginAnalysisHandler(IAnalyzerPlugin plugin, Hashtable progressValues, Data currentPlugin) {
			this.plugin = plugin;
			this.progressValues = progressValues;
			this.currentPlugin = currentPlugin;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String pluginName = null;
			try {
				pluginName = plugin.getName();
				tempReport = plugin.startAnalysis();
			} catch (Exception e) {
				if (progressValues != null) {
					progressValues.remove(UICallback.PLUGIN_STARTED_ANALYZING);
					progressValues.put(UICallback.PLUGIN_FAILED, true);
					uiCallb.updateAnalysisProgress(progressValues);
				}
				Logger.ERROR(TAG, "Plulgin disconected durring analysis!", e);
			} finally {
				/*
				 * if (tempReport == null) { try { if (pluginName != null &&
				 * pluginName.length() > 0) { tempReport = new Data();
				 * tempReport.setName(pluginName);
				 * tempReport.setStatus(Constants.NODE_STATUS_FAILED); } } catch
				 * (Exception e1) { Logger.ERROR(TAG,
				 * "Could create dummy node for failed Plugin!", e1); tempReport = null;
				 * } }
				 */
				if (tempReport == null) {
					Logger.DEBUG(TAG, "Plugin Failed!");
					setPluginStatus(currentPlugin, Constants.NODE_STATUS_FAILED_UNKNOWN);
				}
				pluginAnalyzing = false;
			}
		}
	}

	class UninstallBReceiver extends BroadcastReceiver {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.content.BroadcastReceiver#onReceive(android
		 * .content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			String pkg = intent.getDataString().substring(8);
			Logger.DEBUG(TAG, "uninstalled package : " + pkg);
			for (String plugin : pluginCache) {
				if (plugin.contains(pkg)) {
					pluginCache.remove(plugin);
					break;
				}
			}
		}

	}

	private Data getPluginMetaData(IAnalyzerPlugin plugin, int counter) {
		Data currentPlugin = new Data();
		Data currentPluginName = new Data();
		Data currentPluginClassName = new Data();
		Data currentPluginVersion = new Data();
		Data currentPluginVendor = new Data();
		setStatus = false;
		try {
			currentPlugin.setName(Constants.METADATA_PLUGIN_ + counter);

			currentPluginName.setName(Constants.METADATA_PLUGIN_HUMAN_NAME);
			currentPluginName.setValue(plugin.getName());
			currentPlugin.setValue(currentPluginName);

			currentPluginClassName.setName(Constants.METADATA_PLUGIN_CLASS_NAME);
			currentPluginClassName.setValue(plugin.getClassName());
			currentPlugin.setValue(currentPluginClassName);

			currentPluginVersion.setName(Constants.METADATA_PLUGIN_VERSION);
			currentPluginVersion.setValue(plugin.getVersion());
			currentPlugin.setValue(currentPluginVersion);

			currentPluginVendor.setName(Constants.METADATA_PLUGIN_VENDOR);
			currentPluginVendor.setValue(plugin.getVendor());
			currentPlugin.setValue(currentPluginVendor);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set current plugin data!", e);
		}
		return currentPlugin;
	}

	private Data setPluginStatus(Data currentPlugin, String status) {
		if (!setStatus) {
			Data currentPluginStatus = new Data();
			try {
				currentPluginStatus.setName(Constants.METADATA_PLUGIN_STATUS);
				// String status = plugin.getStatus();
				if (status.equals(Constants.METADATA_PLUGIN_STATUS_PASSED) || status.equals(Constants.NODE_STATUS_OK)) {
					currentPluginStatus.setValue(Constants.NODE_VALUE_YES);
					currentPluginStatus.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
				} else {
					currentPluginStatus.setValue(Constants.NODE_VALUE_NO);
					currentPluginStatus.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);

					Data currentPluginStatusDescription = new Data();
					currentPluginStatusDescription.setName(Constants.METADATA_PLUGIN_FAILURE_DETAILS);
					currentPluginStatusDescription.setValue(status);
					currentPluginStatus.setValue(currentPluginStatusDescription);
				}

				currentPlugin.setValue(currentPluginStatus);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set current plugin data!", e);
			}
			setStatus = true;
		}
		return currentPlugin;
	}

	private void readProperties() {
		Resources resources = ctx.getResources();
		try {
			InputStream rawResource = resources.openRawResource(R.raw.aa);
			Properties properties = new Properties();
			properties.load(rawResource);
			Logger.DEBUG(TAG, "The properties are now loaded");
			Logger.DEBUG(TAG, "properties: " + properties);
			TIME_TO_WAIT_FOR_PLUGIN_CONNECTION = (Integer.parseInt((String) properties.get("plugin.connection")));
			Logger.DEBUG(TAG, "plugin.connection: " + TIME_TO_WAIT_FOR_PLUGIN_CONNECTION);
			DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION = (Integer.parseInt((String) properties
					.get("max.time.analysis.completion")));
			Logger.DEBUG(TAG, "max.time.analysis.completion: " + DEFAULT_MAX_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION);
			DEFAULT_MIN_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION = (Integer.parseInt((String) properties
					.get("min.time.analysis.completion")));
			Logger.DEBUG(TAG, "min.time.analysis.completion: " + DEFAULT_MIN_TIME_TO_WAIT_FOR_PLUGIN_ANALYSIS_COMPLETION);
		} catch (NumberFormatException nfe) {
			Logger.ERROR(TAG, "Value is not integer: " + nfe);
		} catch (NotFoundException e) {
			Logger.ERROR(TAG, "Did not find raw resource: " + e);
		} catch (IOException e) {
			Logger.ERROR(TAG, "Failed to open aa property file");
		}
	}

	private void updatePluginStatus(String pluginName, String pluginClass, String status, String description) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.AA_STATUS_PREFS, 0);
		String record = prefs.getString(pluginClass, null);
		PluginStatus pluginStatus = null;
		if (record != null) {
			pluginStatus = PluginStatus.decodeStatus(record);
			pluginStatus.setPluginDescription(description);
			pluginStatus.setPluginName(pluginName);
		}
		if (pluginStatus == null) {
			pluginStatus = new PluginStatus(pluginName, pluginClass, -1, 0, description);
		}
		int currentRun = Constants.METADATA_PLUGIN_STATUS_PASSED.equals(status) ? PluginStatus.STATUS_PASSED
				: PluginStatus.STATUS_FAILED;
		pluginStatus.setStatus(currentRun);
		pluginStatus.setLastRun(Calendar.getInstance().getTimeInMillis());
		String encode = PluginStatus.encodeStatus(pluginStatus);
		if (encode != null) {
			Editor edit = prefs.edit();
			edit.putString(pluginClass, encode);
			edit.commit();
		}
	}

	private ArrayList<String> sortPluginList(ArrayList<String> enabledPlugins) {
		sortedEnabledPlugins = new ArrayList<String>(enabledPlugins.size());
		if (enabledPlugins != null && enabledPlugins.size() > 0) {
			for (String plugin : enabledPlugins) {
				PluginServiceConnection runningPluginConn = connectToPlugin(plugin);
				if (runningPluginConn != null && runningPluginConn.plugin != null) {
					try {
						boolean uI = runningPluginConn.plugin.isUIRequired();
						Logger.DEBUG(TAG, "Plugin name: " + runningPluginConn.plugin.getName());
						if (uI) {
							Logger.DEBUG(TAG, "Plugin " + plugin + " required UI");
							sortedEnabledPlugins.add(0, plugin);
							uiPluginsCounter++;
							Logger.DEBUG(TAG, "uiPluginsCounter : " + uiPluginsCounter);
						} else {
							Logger.DEBUG(TAG, "Plugin " + plugin + " doesn't required UI");
							sortedEnabledPlugins.add(plugin);
						}
						ctx.unbindService(runningPluginConn);
						runningPluginConn = null;
					} catch (Exception e) {
						Logger.DEBUG(TAG, "Error while trying to activate debug for plugin : " + plugin);
					}
				}
			}
		}
		return sortedEnabledPlugins;
	}

	public void loadPluginMetadata() {
		PluginServiceConnection runningPlugin;
		PluginStatus status;
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.AA_STATUS_PREFS, 0);
		Editor editor = prefs.edit();
		for (String plugin : pluginCache) {
			runningPlugin = connectToPlugin(plugin);
			if (runningPlugin != null && runningPlugin.plugin != null) {
				try {
					String name = runningPlugin.plugin.getName();
					String description = runningPlugin.plugin.getDescription();
					status = new PluginStatus(name, plugin, PluginStatus.STATUS_NOT_RUN, -1, description);
					editor.putString(plugin, PluginStatus.encodeStatus(status));
					ctx.unbindService(runningPlugin);
					runningPlugin = null;
				} catch (Exception e) {
					Logger.ERROR(TAG, "Error loading metadata from plugin: " + plugin, e);
				}
			}
		}
		editor.commit();
	}
}

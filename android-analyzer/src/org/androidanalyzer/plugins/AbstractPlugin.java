/**
 * 
 */
package org.androidanalyzer.plugins;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.IAnalyzerPlugin;
import org.androidanalyzer.core.IPluginRegistry;
import org.androidanalyzer.core.utils.Logger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Abstract class representing analyzer plugin It handles AIDL communication
 * with the Analyzer Core and defines abstract methods for the plugins to
 * implement
 * 
 */
public abstract class AbstractPlugin extends Service {

	private static final String TAG = "Analyzer-AbstractPlugin";
	private static int sdkVersion = 0;

	private IPluginRegistry pRegistry;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		boolean conServ = bindService(new Intent(IPluginRegistry.class.getName()), regConnection, Context.BIND_AUTO_CREATE);
		Logger.DEBUG(TAG, "service connected :" + conServ);
		Logger.DEBUG(TAG, "Started Plugin service for plugin : " + AbstractPlugin.this.getClass().getName());
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.PLUGINS_DISCOVERY_INTENT);
		this.registerReceiver(breceiver, filter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new PluginConnection();
	}

	public class PluginConnection extends IAnalyzerPlugin.Stub {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getName()
		 */
		@Override
		public String getName() throws RemoteException {
			return getPluginName();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#startAnalyze ()
		 */
		@Override
		public Data startAnalysis() throws RemoteException {
			Logger.DEBUG(TAG, "StartAnalysing in Plugin : " + AbstractPlugin.this.getClass().getName());
			return getData();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#stopAnalyze ()
		 */
		@Override
		public void stopAnalysis() throws RemoteException {
			stopDataCollection();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getTimeout()
		 */
		@Override
		public long getTimeout() throws RemoteException {
			return getPluginTimeout();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#setDebugEnabled(boolean)
		 */
		@Override
		public void setDebugEnabled(boolean enabled) throws RemoteException {
			Logger.setDebug(enabled);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getClassName()
		 */
		@Override
		public String getClassName() throws RemoteException {
			return getPluginClassName();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getVersion()
		 */
		@Override
		public String getVersion() throws RemoteException {
			return getPluginVersion();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getVendor()
		 */
		@Override
		public String getVendor() throws RemoteException {
			return getPluginVendor();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#getStatus()
		 */
		@Override
		public String getStatus() throws RemoteException {
			return getPluginStatus();
		}

    @Override
    public String getDescription() throws RemoteException {
      return getPluginDescription();
    }

		/*
		 * (non-Javadoc)
		 * @see org.androidanalyzer.core.IAnalyzerPlugin#isUIRequired()
		 */
    @Override
		public boolean isUIRequired() throws RemoteException {
			return isPluginUIRequired();
		}


	}

	private BroadcastReceiver breceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean conServ = bindService(new Intent(IPluginRegistry.class.getName()), regConnection, Context.BIND_AUTO_CREATE);
			Logger.DEBUG(TAG, "BRecv service connected :" + conServ);
			Logger.DEBUG(TAG, "BRecv started Plugin service for plugin : " + AbstractPlugin.this.getClass().getName());
		}
	};

	private ServiceConnection regConnection = new ServiceConnection() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.content.ServiceConnection#onServiceConnected
		 * (android.content.ComponentName, android.os.IBinder)
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Logger.DEBUG(TAG, "OnServConnected plugin");
			pRegistry = (IPluginRegistry.Stub.asInterface((IBinder) service));
			try {
				pRegistry.registerPlugin(getPluginClassName());
			} catch (RemoteException e) {
				// TODO implement error handling
			}
			unbindService(regConnection);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.content.ServiceConnection#onServiceDisconnected
		 * (android.content.ComponentName)
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

	};

	protected Data addToParent(Data parent, ArrayList<Data> children) {
		if (parent != null) {
			if (children != null && children.size() > 0) {
				for (Data child : children) {
					try {
						parent.setValue(child);
					} catch (Exception e) {
						Logger.ERROR(TAG, "Could not set child!", e);
					}
				}
				try {
					parent.setStatus(Constants.NODE_STATUS_OK);
					parent.setValueType(Constants.NODE_VALUE_TYPE_DATA);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set status!", e);
				}
			} else {
				try {
					parent.setStatus(Constants.NODE_STATUS_FAILED);
					parent.setValue(Constants.NODE_STATUS_FAILED_UNKNOWN);
					parent.setValueType(Constants.NODE_VALUE_TYPE_STRING);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set status!", e);
				}
			}
		}
		return parent;
	}

	/**
	 * Gets the current API version as an integer value
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
	 * Return human readable plugin name
	 * 
	 * @return pluginName
	 */
	abstract public String getPluginName();

	/**
	 * Return plugin timeout. Once the timeout is reached plugin job is aborted
	 * 
	 * @return timeout
	 */
	abstract public long getPluginTimeout();

	/**
	 * Return plugin version
	 * 
	 * @return pluginVersion
	 */
	abstract public String getPluginVersion();

	/**
	 * Return plugin vendor
	 * 
	 * @return pluginVendor
	 */
	abstract public String getPluginVendor();
	
	/**
	 * Return plugin description
	 * @return pluginDescription. Human-readable name explaining purpose of this plugin.
	 */
	abstract public String getPluginDescription();
	
	/**
	 * Return if plugin requires user interaction
	 * @return boolean.
	 */
	abstract public boolean isPluginUIRequired();

	/**
	 * Return plugin class name
	 * 
	 * @return pluginClassName
	 */
	abstract protected String getPluginClassName();

	/**
	 * Return data collected from plugin
	 * 
	 * @return data
	 * @throws RemoteException
	 */
	abstract protected Data getData();

	/**
	 * Force plugin to stop its execution
	 * 
	 * @return
	 */
	abstract protected void stopDataCollection();
	
	/**
	 * Return plugin status. 
	 * 
	 * @return pluginStatus.If error occurs during plugin execution pluginStatus is FAILED
	 *         otherwise is PASSED
	 */
	abstract protected String getPluginStatus();
}

package org.androidanalyzer.plugins.browser;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.os.Looper;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * BrowserPlugin class used for gathering browser application and engine capabilities data
 * 
 */
public class BrowserPlugin extends AbstractPlugin {

	private static final String TAG = "Analyzer-BrowserPlugin";
	private static final String NAME = "Browser Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String PARENT_NODE_NAME = "Browser";

	private static final String WEBKIT = "WebKit";
	private static final String WEBKIT_USER_AGENT = "User agent";
	
	private static final String DESCRIPTION = "Collects data on browser application and engine capabilities";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;
	
	//android.webkit.WebSettings.getUserAgentString

	@Override
	public String getPluginName() {
		return NAME;
	}

	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
	}

	@Override
	public String getPluginVendor() {
		return PLUGIN_VENDOR;
	}

	@Override
	public String getPluginDescription() {
		return DESCRIPTION;
	}

	@Override
	public boolean isPluginUIRequired() {
		return false;
	}

	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	@Override
	protected void stopDataCollection() {
		this.stopSelf();
	}

	@Override
	protected String getPluginStatus() {
		return status;
	}

	@Override
	protected Data getData() {
		ArrayList<Data> children = new ArrayList<Data>(4);
		Data parent = new Data();
		try {
			parent.setName(PARENT_NODE_NAME);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Browser parent node!", e);
			status = "Could not set Browser parent node!";
			return null;
		}

		Data webKit = new Data();
		try {
			/* WebKit */
			webKit.setName(WEBKIT);
			children.add(webKit);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set WebKit parent node!", e);
			status = "Could not set WebKit parent node!";
			return null;
		}
		
		Data userAgent = null;
		try {
			/* User Agent*/
			userAgent = new Data();
			userAgent.setName(WEBKIT_USER_AGENT);
			//Note: we are not playing nice here and just prepare the Looper so that no exceptions are thrown
			//during the init ot WebView. However, some errors will be logged right after we quit the Looper
			//because Android would post some messages for the loop after we have quitted it
			//This doesn't matter for the plugin, since correct data is extracted - at least for the user agent
			//for the time being
			Looper.prepare();
			WebView wv = new WebView(this);
			WebSettings ws = wv.getSettings();
			Looper.myLooper().quit();
			userAgent.setValue(ws.getUserAgentString());
			webKit.setValue(userAgent);
		} catch (Throwable e) {
			Logger.ERROR(TAG, "Could not set User agent node!", e);
			status = "Could not set User agent node!";
		}
		addToParent(parent, children);
		return parent;
	}

}

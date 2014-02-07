package org.androidanalyzer.plugins.api;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

/**
 * APIPlugin class that represents all API sets and versions available on the device.
 * 
 */
public class APIPlugin extends AbstractPlugin {

	private static final String NAME = "API Plugin";
	private static final String PLUGIN_VERSION = "1.0.1";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String API = "API";
	private static final String ANDROID_API_LEVEL = "Android API Level";
	private static final String GOOGLE = "Google";
	private static final String GMAPS = "Google Maps Application";
	private static final String CONTACTS = "Contacts";
	private static final String MY_CONTACT_CART = "MyContactCard type";
	private static final String TAG = "Analyzer-APIPlugin";
	private static final String DESCRIPTION = "Collects data on available Android and Google APIs and their versions";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginTimeout()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginVersion()
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#uiRequired()
	 */
	@Override
	public boolean isPluginUIRequired() {
		return false;
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
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getData()
	 */
	@Override
	protected Data getData(){
		Logger.DEBUG(TAG, "getData in API Plugin");
		Data parent = new Data();
		ArrayList<Data> masterChildren = new ArrayList<Data>(2);
		try {
			parent.setName(API);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set API parent node!", e);
			status = "Could not set API parent node!";
			return null;
		}
		Data apiLevelHolder = new Data();
		/* API Level */
		try {
			apiLevelHolder.setName(ANDROID_API_LEVEL);
			int apiVersion = getAPIVersion();
			if (apiVersion > 0) {
				apiLevelHolder.setValue("" + apiVersion);
				apiLevelHolder.setStatus(Constants.NODE_STATUS_OK);
				apiLevelHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			} else {
				apiLevelHolder.setStatus(Constants.NODE_STATUS_FAILED);
				apiLevelHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
				apiLevelHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			}
			apiLevelHolder.setConfirmationLevel(Constants.NODE_CONFIRMATION_LEVEL_TEST_CASE_CONFIRMED);
			apiLevelHolder.setInputSource(Constants.NODE_INPUT_SOURCE_AUTOMATIC);
			masterChildren.add(apiLevelHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set API Level node!", e);
			status = "Could not set API Level node!";
		}

		//TODO (s.djukic) The entire Google API code below is commented out temporarily.
		//In order to use a library we must declare uses-library in the manifest
		//I tried it but couldn't make it work event on Google 2.2 AVD. Also,
		//they say that the app won't install unless you have the library installed
		//on the device as well, which will be a huge blocker (though needs to be verified)
		//Alternative to this, as I see it currently, is to have a separate apk being
		//installed and uninstalled at runtime with user consent, but this would hinder
		//the end user experience a lot. Ideas welcome!

/*
		// Google
		Data googleHolder = new Data();
		
		// Google -> com.google.android.maps
		Data mapViewHolder = new Data();
		try {
			googleHolder.setName(GOOGLE);

			ArrayList<Data> googleChildren = new ArrayList<Data>(2);
			mapViewHolder.setName(GMAPS);
			Object classMapView = null;
			try {
				//classMapView = Class.forName("com.google.android.maps.MapView");//this way we avoid class linking
				//classMapView = com.google.android.maps.MapView.class;//alternative, in case we nede to explicitly import the class
			} catch (Throwable e) {
				Logger.ERROR(TAG, "Could not obtain MapView", e);
			}
			if (classMapView == null) {
				mapViewHolder.setValue(Constants.NODE_VALUE_NO);
			} else {
				mapViewHolder.setValue(Constants.NODE_VALUE_YES);
			}
			mapViewHolder.setStatus(Constants.NODE_STATUS_OK);
			mapViewHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			googleChildren.add(mapViewHolder);
			
			// TODO Check for others APIs <API Owner Name> <API Package Name>
			
			googleHolder = addToParent(googleHolder, googleChildren);
			masterChildren.add(googleHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Google node!", e);
			status = "Could not set Google node!";
		}
*/
		
		// Google
		Data googleHolder = new Data();
		
		// Google -> com.google.android.maps
		Data mapViewHolder = new Data();
		try {
			googleHolder.setName(GOOGLE);

			ArrayList<Data> googleChildren = new ArrayList<Data>(2);
			mapViewHolder.setName(GMAPS);
			ActivityInfo gmapsActivityInfo = null;
			try {
				ComponentName cname = new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				gmapsActivityInfo = getPackageManager().getActivityInfo(cname, 0);
			} catch (Throwable e) {
				Logger.ERROR(TAG, "Could not obtain Google Maps Component or Package Manager", e);
			}
			if (gmapsActivityInfo == null) {
				mapViewHolder.setValue(Constants.NODE_VALUE_NO);
			} else {
				mapViewHolder.setValue(Constants.NODE_VALUE_YES);
			}
			mapViewHolder.setStatus(Constants.NODE_STATUS_OK);
			mapViewHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			googleChildren.add(mapViewHolder);
			
			// TODO Check for others APIs <API Owner Name> <API Package Name>
			
			googleHolder = addToParent(googleHolder, googleChildren);
			masterChildren.add(googleHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Google node!", e);
			status = "Could not set Google node!";
		}
		
		// Contacts
		Data contactsHolder = new Data();
		
		// Contacts -> MyContactCard
		Data myContactCardHolder = new Data();
		try {
			contactsHolder.setName(CONTACTS);

			ArrayList<Data> contactsChildren = new ArrayList<Data>(2);
			myContactCardHolder.setName(MY_CONTACT_CART);
			myContactCardHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			try{				
				String contactCardType = getContentResolver().getType(Uri.parse("content://contacts/myContactCard"));
				if (contactCardType == null){
					myContactCardHolder.setValue(Constants.NODE_VALUE_UNKNOWN);		
				} else {
					myContactCardHolder.setValue(contactCardType);
				}
				myContactCardHolder.setStatus(Constants.NODE_STATUS_OK);
			} catch (Exception e){
				Logger.ERROR(TAG, "Could not read content resolver", e);
				myContactCardHolder.setStatus(Constants.NODE_STATUS_FAILED);
				myContactCardHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			} 
							
			contactsChildren.add(myContactCardHolder);
			
			
			contactsHolder = addToParent(contactsHolder, contactsChildren);
			masterChildren.add(contactsHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Contacts node!", e);
			status = "Could not set Contacts node!";
		}
		
		parent = addToParent(parent, masterChildren);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		Logger.DEBUG(TAG, "Service is stopped!");
		this.stopSelf();
	}
}

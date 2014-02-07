package org.androidanalyzer.plugins.keyboard;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.content.res.Configuration;

/**
 * KeyboardPlugin class that represents the main KeyboardPlugin
 * functionality and data gathering
 *  
 * @author friedger
 */
public class KeyboardPlugin extends AbstractPlugin {

  private static final String TAG = "Analyzer-KeyboardPlugin";
  private static final String NAME = "Keyboard Plugin";
  private static final String PLUGIN_VERSION = "1.0.0";
  private static final String PLUGIN_VENDOR = "OpenIntents UG";
  private static final String PARENT_NODE_NAME = "Keyboard";

  private static final String HARD_KEYBOARD = "Hard keyboard";
  private static final String KEYBOARD_NOKEYS = "Custom keys";
  private static final String KEYBOARD_12KEY = "Numeric pad";
  private static final String KEYBOARD_QWERTY = "QWERTY";
  private static final String KEYBOARD_UNDEFINED = "Unknown";
  
  private static final String NAVIGATION = "Navigation";
  private static final String NAVIGATION_NONAV = "No navigation";
  private static final String NAVIGATION_WHEEL = "Wheel";
  private static final String NAVIGATION_DPAD = "Directional pad";
  private static final String NAVIGATION_TRACKBALL = "Trackball";
  private static final String NAVIGATION_UNDEFINED = "Unknown";
  
  
  
  private static String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

  
  private static final String DESCRIPTION = "Collects data on available hardkeyboard and orientation";
  
  /*
   * (non-Javadoc)
   * @see
   * org.androidanalyzer.plugins.camera.PluginCommunicator#
   * getData()
   */
  @Override
  public Data getData() {	  
    ArrayList<Data> children = new ArrayList<Data>(4);
    Data parent = new Data();
    try {
      parent.setName(PARENT_NODE_NAME);
    } catch (Exception e) {
      Logger.ERROR(TAG, "Could not set Keyboard parent node!", e);
      status = "Could not set Keyboard parent node!";
      return null;
    }

   
    Configuration c = getResources().getConfiguration();

	try {
		Data hardKeyboard = new Data();
		hardKeyboard.setName(HARD_KEYBOARD);
		
		if (c.keyboard == Configuration.KEYBOARD_NOKEYS) {
			hardKeyboard.setValue(KEYBOARD_NOKEYS);
		} else if (c.keyboard == Configuration.KEYBOARD_12KEY){
			hardKeyboard.setValue(KEYBOARD_12KEY);			
		} else if (c.keyboard == Configuration.KEYBOARD_QWERTY){
			hardKeyboard.setValue(KEYBOARD_QWERTY);
		} else if (c.keyboard == Configuration.KEYBOARD_UNDEFINED){
			hardKeyboard.setValue(KEYBOARD_UNDEFINED);
		} else {
			hardKeyboard.setStatus(Constants.NODE_STATUS_FAILED);
			hardKeyboard.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
		}
		children.add(hardKeyboard);
	} catch (Exception e) {
		Logger.ERROR(TAG, "Could not create hard keyboard node!", e);
		status = "Could not create hard keyboard node!";
	}
	
	try {
		Data navigation = new Data();
		navigation.setName(NAVIGATION);
		
		if (c.navigation == Configuration.NAVIGATION_NONAV) {
			navigation.setValue(NAVIGATION_NONAV);
		} else if (c.navigation == Configuration.NAVIGATION_DPAD){
			navigation.setValue(NAVIGATION_DPAD);			
		} else if (c.navigation == Configuration.NAVIGATION_TRACKBALL){
			navigation.setValue(NAVIGATION_TRACKBALL);
		} else if (c.navigation == Configuration.NAVIGATION_WHEEL){
			navigation.setValue(NAVIGATION_WHEEL);
		} else if (c.navigation == Configuration.NAVIGATION_UNDEFINED){
			navigation.setValue(NAVIGATION_UNDEFINED);
		} else {
			navigation.setStatus(Constants.NODE_STATUS_FAILED);
			navigation.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
		}
		children.add(navigation);
	} catch (Exception e) {
		Logger.ERROR(TAG, "Could not create navigation node!", e);
		status = "Could not create navigation node!";
	}
  
	addToParent(parent, children);	
	return parent;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.androidanalyzer.plugins.camera.PluginCommunicator#
   * getPluginName()
   */
  @Override
  public String getPluginName() {
    return NAME;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.androidanalyzer.plugins.PluginCommunicator#getTimeout
   * ()
   */
  @Override
  public long getPluginTimeout() {
    return 10000;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.androidanalyzer.plugins.AbstractPlugin#getPluginVersion
   * ()
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}
	
  /*
   * (non-Javadoc)
   * @see org.androidanalyzer.plugins.camera.
   * PluginCommunicatorAbstract #getPluginClassName()
   */
  @Override
  protected String getPluginClassName() {
    return this.getClass().getName();
  }

  /*
   * (non-Javadoc)
   * @see org.androidanalyzer.plugins.PluginCommunicator#
   * stopDataCollection()
   */
  @Override
  protected void stopDataCollection() {
    this.stopSelf();
  }


  @Override
  public String getPluginDescription() {
    return DESCRIPTION;
  }

  @Override
  public boolean isPluginUIRequired() {
	return false;
  }

}

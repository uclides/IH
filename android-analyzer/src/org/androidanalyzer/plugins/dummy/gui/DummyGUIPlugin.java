package org.androidanalyzer.plugins.dummy.gui;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;
import org.androidanalyzer.plugins.dummy.gui.cameraview.CameraViewActivity;

import android.content.Intent;

/**
 * 
 *
 */
public class DummyGUIPlugin extends AbstractPlugin {

	public static final String CAMERA_VIEW_INTENT = "org.androidanalyzer.plugins.dummy.gui.cameraview";

	private static final String TAG = "Analyzer-DummyGUIPlugin";
	private static final String NAME = "Dummy GUI Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String PARENT_NODE_NAME = "DummyGUIPlugin";
	private static final String DESCRIPTION = "Start internal activity which starts camera view with extra OpenGL process";

	private static int TIMEOUT = 10000;
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	private String answer = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.memory.PluginCommunicator# getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator# getPluginVersion()
	 */
	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getTimeout ()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
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
	 * @see org.androidanalyzer.plugins.memory.PluginCommunicator# getData()
	 */
	@Override
	protected Data getData() {
		ArrayList<Data> masterChildren = new ArrayList<Data>();
		Data parent = new Data();
		try {
			parent.setName(PARENT_NODE_NAME);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set DummyGUI parent node!", e);
			status = "Could not set DummyGUI parent node!";
			return null;
		}

		Intent cameraViewIntent = new Intent(this, CameraViewActivity.class);
		cameraViewIntent.setAction(CAMERA_VIEW_INTENT);
		cameraViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		cameraViewIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(cameraViewIntent);
		Logger.DEBUG(TAG, "Init answer :" + answer);
		answer = null;
		while (answer == null) {
			answer = DummyGUIPreferencesManager.loadStringPreference(this, DummyGUIConstants.ANSWER_KEY);
			Logger.DEBUG(TAG, "Answer is : " + answer);
			try {
				Thread.sleep(100);
				TIMEOUT -= 100;
			} catch (InterruptedException e) {
				Logger.ERROR(TAG, "Could not Sleep thread in DummyGUI Plugin!");
			}
		}
		Logger.DEBUG(TAG, "Exit from loop");
		DummyGUIPreferencesManager.deletePreference(this, DummyGUIConstants.ANSWER_KEY);
		Data answerData = new Data();
		try {
			answerData.setName(DummyGUIConstants.ANSWER);
			if (answer == null) {
				answer = Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE;
			}
			answerData.setValue(answer);
			masterChildren.add(answerData);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Answer node!", e);
			status = "Could not set Answer parent node!";
		}

		addToParent(parent, masterChildren);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.memory. PluginCommunicatorAbstract
	 * #getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator# stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		this.stopSelf();

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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#isPluginUIRequired()
	 */
	@Override
	public boolean isPluginUIRequired() {
		return true;
	}

}

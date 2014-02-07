package org.androidanalyzer.plugins.dummy.gui;

import org.androidanalyzer.core.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DummyGUIBReceiver extends BroadcastReceiver {

	private static final String TAG = "Analyzer-CameraView-BReceiver";
	private String answer = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, DummyGUIPlugin.class));
		Logger.DEBUG(TAG, "Plugin broadcast received for " + DummyGUIPlugin.class);
		answer = intent.getStringExtra(DummyGUIConstants.ANSWER_KEY);
		Logger.DEBUG(TAG, "Answer received is: " + answer);
		DummyGUIPreferencesManager.savePreference(context, DummyGUIConstants.ANSWER_KEY, answer);
	}

}

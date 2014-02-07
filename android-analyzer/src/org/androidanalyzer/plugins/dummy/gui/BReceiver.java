/**
 * 
 */
package org.androidanalyzer.plugins.dummy.gui;

import org.androidanalyzer.core.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast Receiver to fire up the plugin registering it to the Core
 * 
 */
public class BReceiver extends BroadcastReceiver {

	private static final String TAG = "Analyzer-DummyGUIPlugin-BReceiver";

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
	}

}
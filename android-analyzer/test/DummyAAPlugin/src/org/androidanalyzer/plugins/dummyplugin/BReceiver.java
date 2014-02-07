/**
 * 
 */
package org.androidanalyzer.plugins.dummyplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author k.raev
 *
 */
public class BReceiver extends BroadcastReceiver {
	
	private static final String TAG = "Analyzer-DummyExternal-BReceiver";
  /* (non-Javadoc)
   * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
   */
  @Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, DummyExternalPlugin.class));
		Log.d(TAG, "Plugin broadcast received for " + DummyExternalPlugin.class);
	}

}
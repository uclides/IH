package org.androidanalyzer.plugins.locationaccuracy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class is used to store and read data from Preference Manager. The data
 * is received from GPS Benchmark application
 * 
 */
public class LocationAccuracyPreferencesManager {

	private static final String PREFS_NAME = "org.androidanalyzer.gui.GPSBenchmarkPreferencesManager";

	/*
	 * Save key value received from GPS Benchmark application
	 */
	public static void savePreference(Context ctx, String key, String value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static String loadStringPreference(Context ctx, String key) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		String result = prefs.getString(key, null);
		return result;
	}
}

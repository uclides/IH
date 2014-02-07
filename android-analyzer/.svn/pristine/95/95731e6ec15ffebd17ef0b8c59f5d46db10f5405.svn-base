package org.androidanalyzer.plugins.dummy.gui;

import org.androidanalyzer.core.utils.Logger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class is used to store and read data from Preference Manager. The data
 * is received from GPS Benchmark application
 * 
 */
public class DummyGUIPreferencesManager {

	private static final String PREFS_NAME = "org.androidanalyzer.plugins.dummy.gui.DummyGUIPreferencesManager";
	private static final String TAG = "Analyzer-DummyGUIPreferencesManager";

	/*
	 * Save key value received from GPS Benchmark application
	 */
	public static void savePreference(Context ctx, String key, String value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.putString(key, value);
		edit.commit();
		Logger.DEBUG(TAG, "Save key - " + key + " and value - " + value);
	}

	public static String loadStringPreference(Context ctx, String key) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		String result = prefs.getString(key, null);
		Logger.DEBUG(TAG, "Load key - " + key + " and value - " + result);
		return result;
	}

	public static void deletePreference(Context ctx, String key) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		Editor edit = prefs.edit();
		edit.remove(key);
		edit.commit();
		Logger.DEBUG(TAG, "Delete key - " + key);
	}
}

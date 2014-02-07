package org.androidanalyzer.gui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Helper class that saves and loads data from the preferences 
 *
 */
public class PreferencesManager {
  
  private static final String PREFS_NAME = "org.androidanalyzer.gui.SettingsActivity";  
  
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
  
  public static void savePreference(Context ctx, String key, boolean value) {
    SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
    Editor edit = prefs.edit();
    edit.putBoolean(key, value);
    edit.commit();    
  }
  
  public static boolean loadBooleanPreference(Context ctx, String key) {
    SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
    boolean result = prefs.getBoolean(key, false);
    return result;
  }
  
}

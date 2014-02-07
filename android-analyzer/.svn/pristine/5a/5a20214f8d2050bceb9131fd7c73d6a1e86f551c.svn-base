package org.androidanalyzer.plugins.locationaccuracy;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.androidanalyzer.core.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast Receiver class is used to get data from Intent sent from GPS Benchmark
 * application and saved it for further use.
 * 
 */
public class GPSBenchmarkBReceiver extends BroadcastReceiver {

	private static final String TAG = "Analyzer-LocationAccuracyPlugin-GPSBenchmarkBReceiver";
	private static final String HM = "gpsbenchmark";
	private int counter = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android .content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Serializable data = intent.getSerializableExtra(HM);
		if (data != null) {
			// Hashtable table = (Hashtable) data;
			Hashtable<String, String> table = new Hashtable<String, String>((HashMap<String, String>) data);
			Logger.DEBUG(TAG, "Hashtable " + table);
			Enumeration keys = table.keys();
			while (keys.hasMoreElements()) {
				String rawKey = (String) keys.nextElement();
				String value = (String) table.get(rawKey);
				if (value != null && value.length() == 0)
					value = LocationAccuracyConstants.UNAVAILABLE;
				String key = parseRawKey(rawKey);
				LocationAccuracyPreferencesManager.savePreference(context, key, value);
			}
		} else {
			Logger.ERROR(TAG, "Hashmap is null");
		}
		Logger.DEBUG(TAG, "Plugin broadcast received for " + LocationAccuracyPlugin.class);
		counter = 0;
	}

	private String parseRawKey(String key) {
		String r_key = null;
		if (key.equals(LocationAccuracyConstants.RAW_TEST_ID)) {
			r_key = LocationAccuracyConstants.TEST_ID;
		} else if (key.equals(LocationAccuracyConstants.RAW_APP_VERSION)) {
			r_key = LocationAccuracyConstants.APP_VERSION;
			/*
			 * } else if (key.equals(LocationAccuracyConstants.RAW_DEVICE_BRAND)) {
			 * r_key = LocationAccuracyConstants.DEVICE_BRAND; } else if
			 * (key.equals(LocationAccuracyConstants.RAW_DEVICE_PRODUCT)) { r_key =
			 * LocationAccuracyConstants.DEVICE_PRODUCT; } else if
			 * (key.equals(LocationAccuracyConstants.RAW_ANDROID_VERSION)) { r_key =
			 * LocationAccuracyConstants.ANDROID_VERSION;
			 */
		} else if (key.equals(LocationAccuracyConstants.RAW_START_TIME)) {
			r_key = LocationAccuracyConstants.START_TIME;
		} else if (key.equals(LocationAccuracyConstants.RAW_END_TIME)) {
			r_key = LocationAccuracyConstants.END_TIME;
		} else if (key.equals(LocationAccuracyConstants.RAW_TIME_TO_FIRST_FIX)) {
			r_key = LocationAccuracyConstants.TIME_TO_FIRST_FIX;
		} else if (key.equals(LocationAccuracyConstants.RAW_LOCATION_PROVIDER)) {
			r_key = LocationAccuracyConstants.LOCATION_PROVIDER;
		} else if (key.equals(LocationAccuracyConstants.RAW_SAMPLE_SIZE)) {
			r_key = LocationAccuracyConstants.SAMPLE_SIZE;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_MIN)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_MIN;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_MAX)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_MAX;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_MEAN)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_MEAN;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_50THPERCENTILE)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_50THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_68THPERCENTILE)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_68THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_95THPERCENTILE)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_95THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_HORIZONTAL_ERROR_STANDARDDEVIATION)) {
			r_key = LocationAccuracyConstants.HORIZONTAL_ERROR_STANDARD_DEVIATION;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_MIN)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_MIN;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_MAX)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_MAX;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_MEAN)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_MEAN;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_MEAN_ABSOLUTE)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_MEAN_ABSOLUTE;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_50THPERCENTILE)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_50THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_68THPERCENTILE)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_68THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_95THPERCENTILE)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_95THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_RMSE)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_RMSE;
		} else if (key.equals(LocationAccuracyConstants.RAW_VERTICAL_ERROR_STANDARD_DEVIATION)) {
			r_key = LocationAccuracyConstants.VERTICAL_ERROR_STANDARD_DEVIATION;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_MIN)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MIN;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_MAX)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MAX;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_MEAN)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_RMSE)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_RMSE;
		} else if (key.equals(LocationAccuracyConstants.RAW_ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION)) {
			r_key = LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION;
			/*
			 * } else if
			 * (key.equals(LocationAccuracyConstants.RAW_GROUND_TRUTH_LATITUDE)) {
			 * r_key = LocationAccuracyConstants.GROUND_TRUTH_LATITUDE; } else if
			 * (key.equals(LocationAccuracyConstants.RAW_GROUND_TRUTH_LONGITUDE)) {
			 * r_key = LocationAccuracyConstants.GROUND_TRUTH_LONGITUDE; } else if
			 * (key.equals(LocationAccuracyConstants.RAW_GROUND_TRUTH_ALTITUDE)) {
			 * r_key = LocationAccuracyConstants.GROUND_TRUTH_ALTITUDE;
			 */
		} else if (key.equals(LocationAccuracyConstants.RAW_FIRST_FIX_TIME)) {
			r_key = LocationAccuracyConstants.FIRST_FIX_TIME;
		} else if (key.equals(LocationAccuracyConstants.RAW_SAMPLING_INTERVAL)) {
			r_key = LocationAccuracyConstants.SAMPLING_INTERVAL;
		} else if (key.equals(LocationAccuracyConstants.RAW_SAMPLING_DISTANCE)) {
			r_key = LocationAccuracyConstants.SAMPLING_DISTANCE;
		} else if (key.equals(LocationAccuracyConstants.RAW_AVERAGE_TIME_BETWEEN_FIXES)) {
			r_key = LocationAccuracyConstants.AVERAGE_TIME_BETWEEN_FIXES;
		} else if (key.equals(LocationAccuracyConstants.RAW_OVERRIDE_REFRESH_RATE)) {
			r_key = LocationAccuracyConstants.OVERRIDE_REFRESH_RATE;
		} else if (key.equals(LocationAccuracyConstants.RAW_KEEP_SCREEN_ON)) {
			r_key = LocationAccuracyConstants.KEEP_SCREEN_ON;
		} else if (key.equals(LocationAccuracyConstants.RAW_TIME_INJECTED_TIME_DATA)) {
			r_key = LocationAccuracyConstants.TIME_INJECTED_TIME_DATA;
		} else if (key.equals(LocationAccuracyConstants.RAW_TIME_INJECTED_XTRA_DATA)) {
			r_key = LocationAccuracyConstants.TIME_INJECTED_XTRA_DATA;
		} else if (key.equals(LocationAccuracyConstants.RAW_TIME_CLEARED_ASSIST_DATA)) {
			r_key = LocationAccuracyConstants.TIME_CLEARED_ASSIST_DATA;
			/*
			 * } else if (key.equals(LocationAccuracyConstants.RAW_WIFI_STATUS)) {
			 * r_key = LocationAccuracyConstants.WIFI_STATUS;
			 */
		} else {
			// TODO Check more precisely
			r_key = LocationAccuracyConstants.UNKNOWN + counter;
			counter++;
			Logger.WARNING(TAG, "The Key " + key + " is not recognized");
		}
		Logger.DEBUG(TAG, "The Key " + key + " is parsed");
		return r_key;
	}
}
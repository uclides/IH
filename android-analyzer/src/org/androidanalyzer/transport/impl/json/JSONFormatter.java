package org.androidanalyzer.transport.impl.json;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility class that generates valid JSON object from given data.
 */
public class JSONFormatter {

	private static final String TAG = "Analyzer-JSONFormatter";

	/**
	 * Return JSON object from given data
	 */

	public static JSONObject format(Data data) {
		Object object = data.getValue();
		Logger.DEBUG(TAG, "object: " + object);
		String comment = data.getComment();
		Logger.DEBUG(TAG, "comment: " + comment);
		String confLevel = data.getConfirmationLevel();
		Logger.DEBUG(TAG, "confLevel: " + confLevel);
		String inpSrc = data.getInputSource();
		Logger.DEBUG(TAG, "inpSrc: " + inpSrc);
		String name = data.getName();
		Logger.DEBUG(TAG, "name: " + name);
		String valueType = data.getValueType();
		Logger.DEBUG(TAG, "valueType: " + valueType);
		String valueMetric = data.getValueMetric();
		Logger.DEBUG(TAG, "valueMetric: " + valueMetric);
		JSONObject holder = new JSONObject();
		if (object instanceof ArrayList) {
			ArrayList<Data> list = (ArrayList<Data>) object;
			for (int i = 0; i < list.size(); i++) {
				Data data_in = list.get(i);
				try {
					JSONObject leaf = format(data_in);
					Logger.DEBUG(TAG, "leaf: " + leaf);
					holder.put(data_in.getName(), leaf);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				holder.put(Constants.NODE_CONFIRMATION_LEVEL, confLevel);
				holder.put(Constants.NODE_INPUT_SOURCE, inpSrc);
				holder.put(Constants.NODE_VALUE_TYPE, valueType);
				if (valueMetric != null)
					holder.put(Constants.NODE_VALUE_METRIC, valueMetric);
				holder.put(Constants.NODE_COMMENT, comment);
				if (name.equals(Constants.ROOT)) {
					JSONObject root = new JSONObject();
					holder = root.put(name, holder);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (object instanceof String) {
			try {
				holder.put(Constants.NODE_VALUE, object);
				holder.put(Constants.NODE_CONFIRMATION_LEVEL, confLevel);
				holder.put(Constants.NODE_INPUT_SOURCE, inpSrc);
				holder.put(Constants.NODE_VALUE_TYPE, valueType);
				if (valueMetric != null)
					holder.put(Constants.NODE_VALUE_METRIC, valueMetric);
				holder.put(Constants.NODE_COMMENT, comment);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return holder;
	}
}

package org.androidanalyzer.json.test;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Description
 * 
 * @author <Georgi Chepilev>
 * @version <1.0.0>
 */
public class JSONFormatter {

	public JSONObject format(Data data) {
		String tag = "JSON";
		JSONObject capability = null;
		Object object = data.getValue();
		Log.d(tag, "object: " + object);
		String comment = data.getComment();
		Log.d(tag, "comment: " + comment);
		String confLevel = data.getConfirmationLevel();
		Log.d(tag, "confLevel: " + confLevel);
		String inpSrc = data.getInputSource();
		Log.d(tag, "inpSrc: " + inpSrc);
		String name = data.getName();
		Log.d(tag, "name: " + name);
		JSONObject holder = new JSONObject();
		if (object instanceof ArrayList) {
			ArrayList<Data> list = (ArrayList<Data>) object;
			for (int i = 0; i < list.size(); i++) {
				Data data_in = list.get(i);
				try {
					JSONObject leaf = format(data_in);
					Log.d(tag, "leaf: " + leaf);
					holder.put(data_in.getName(), leaf);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				holder.put(Constants.NODE_CONFIRMATION_LEVEL, confLevel);
				holder.put(Constants.NODE_INPUT_SOURCE, inpSrc);
				holder.put(Constants.NODE_COMMENT, comment);
				if (name.equals("ROOT")) {
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
				holder.put(Constants.NODE_COMMENT, comment);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return holder;
	}
}

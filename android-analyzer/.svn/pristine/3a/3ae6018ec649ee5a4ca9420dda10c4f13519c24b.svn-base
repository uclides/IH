package org.androidanalyzer.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import org.androidanalyzer.Constants;
import org.androidanalyzer.R;
import org.androidanalyzer.core.AnalyzerCore;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.transport.Reporter;
import org.androidanalyzer.transport.Reporter.Response;
import org.apache.http.client.HttpResponseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity provides means for processing (save to file or/and send to the
 * back-end) the collected data.
 */
public class ReportActivity extends Activity {
	
	private static final int SEND_REPORT_PROGRESS = 0;
	private static final int SAVE_REPORT_PROGRESS = 1;
	private static final String TAG = "Analyzer-ReportActivity";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		final Data analysisResult = (Data) data.get(Constants.GUI_HANDLER_SEND);
//		final AnalyzerCore core = AnalyzerCore.getInstance();
		final String host = (String) data.get(Constants.HOST);
		Button sendB = (Button) findViewById(R.id.first_button);
		sendB.setText(R.string.send_report_button);
		sendB.setOnClickListener(new View.OnClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				EditText mUserText = (EditText) findViewById(R.id.edittext1);
				String comment = mUserText.getText().toString();
				analysisResult.setComment(comment);
				Bundle bundle = new Bundle();
				bundle.putParcelable(Constants.GUI_HANDLER_SEND, analysisResult);
				bundle.putString(Constants.HOST, host);
				showDialog(SEND_REPORT_PROGRESS);
				new ProgressThread(SEND_REPORT_PROGRESS, bundle, handler).start();
			}
		});
		Button saveB = (Button) findViewById(R.id.second_button);
		saveB.setText(R.string.save_report_button);
		saveB.setOnClickListener(new View.OnClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putParcelable(Constants.GUI_HANDLER_SEND, analysisResult);
				showDialog(SAVE_REPORT_PROGRESS);
				new ProgressThread(SAVE_REPORT_PROGRESS, bundle, handler).start();
			}
		});

		TextView result = (TextView) findViewById(R.id.text);
		StringBuffer sb = new StringBuffer();
		addNodeValue(sb, analysisResult, "");
		result.setText(sb.toString());
	}

	private void addNodeValue(StringBuffer sb, Data data, String indent) {
		sb.append(indent).append(">>").append(data.getName()).append('\n');
		if (data.getValue() instanceof ArrayList<?>){
			ArrayList<Parcelable> children = (ArrayList<Parcelable>) data.getValue();
			for (int i = 0; i < children.size(); i++) {
				Data child = (Data) children.get(i);
				addNodeValue(sb, child, indent + "  ");				
			}
		} else {
			sb.append(indent).append("  = " + data.getValue() + "\n");
		}
		//sb.append(indent).append ("<<").append(data.getName()).append("\n");
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog progress = new ProgressDialog(this);
		switch (id) {
		case SAVE_REPORT_PROGRESS:
			progress.setMessage(getString(R.string.save_progress_message));
			break;
		case SEND_REPORT_PROGRESS:
			progress.setMessage(getString(R.string.send_progress_message));
		}
		return progress;
	}

	void showResultDialog(String message, int action, boolean negative, String reportID) {
		Logger.DEBUG(TAG, "message - " + message + " action - " + action + " negative - " + negative);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		switch (action) {
		case SAVE_REPORT_PROGRESS:
			dialog.setTitle(message != null ? R.string.alert_dialog_pos_title : R.string.alert_dialog_warning_title);
			dialog.setIcon(message != null ? R.drawable.ok_icon : R.drawable.warning_icon_yellow);
			Logger.DEBUG(TAG, "message (save report) - " + message);
			dialog.setMessage(message != null ? getString(R.string.alert_dialog_pos_ssd) + message : getString(R.string.alert_dialog_warning_ssd_msg));
			break;
		case SEND_REPORT_PROGRESS:
			dialog.setTitle(negative ? R.string.alert_dialog_warning_title : R.string.alert_dialog_pos_title);
			dialog.setIcon(negative ? R.drawable.warning_icon_yellow : R.drawable.ok_icon);
			if( message != null && message.length() != 0)
				message = getString(R.string.alert_dialog_warning_ss_msg) + " " + message;
			Logger.DEBUG(TAG, "message (send report) -- " + message);
			String prefix = reportID == null ? getString(R.string.alert_dialog_pos_ss)
					 						 : getString(R.string.alert_dialog_pos_ss_with_id) + reportID;
			Logger.DEBUG(TAG, "message (prefix) -- " + prefix);
			dialog.setMessage(negative ? message : prefix + (message == null ? "" : "\n" + message));
			break;
		}
		dialog.setCancelable(false);
		dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.content.DialogInterface.OnClickListener#onClick(android.content
			 * .DialogInterface, int)
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	final Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.getData().getBoolean(Constants.REPORT_UI_DONE)) {
				int id = msg.getData().getInt(Constants.REPORT_UI_ACTION);
				boolean negative = msg.getData().getBoolean(Constants.REPORT_UI_RESPONSE_NEGATIVE);
				dismissDialog(id);
				String response = msg.getData().getString(Constants.REPORT_UI_RESPONSE);
				String reportID = msg.getData().getString(Constants.REPORT_LAST_ID);
				PreferencesManager.savePreference(ReportActivity.this, Constants.REPORT_LAST_ID, reportID);
				showResultDialog(response, id, negative, reportID);
			}
		}

	};

	private class ProgressThread extends Thread {

		int action;
		Bundle data;
		Handler handler;
		AnalyzerCore core;

		public ProgressThread(int action, Bundle data, Handler handler) {
			this.action = action;
			this.data = data;
			this.handler = handler;
			core = AnalyzerCore.getInstance();
		}

		public void run() {
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putBoolean(Constants.REPORT_UI_DONE, true);
			bundle.putInt(Constants.REPORT_UI_ACTION, action);
			String response = null;
			String reportID = null;
			boolean negative = false;
			Data result = (Data) data.get(Constants.GUI_HANDLER_SEND);
			switch (action) {
				case SAVE_REPORT_PROGRESS:
					response = core.writeToFile(result);
					if (response != null)
						bundle.putString(Constants.REPORT_UI_RESPONSE, response);
					break;
				case SEND_REPORT_PROGRESS:
					try {
						String host = (String) data.get(Constants.HOST);
						URL lHost = new URL(host);
						Hashtable<String, String> extra = null;
						
						String userUID = PreferencesManager.loadStringPreference(ReportActivity.this, Constants.USER_UID);
						if ( userUID != null ) {
							extra = new Hashtable<String, String>(2);
							extra.put(Reporter.KEY_USER_UID, userUID);
						}
						Response responseObject = core.sendReport(result, lHost, extra);
						if ( responseObject != null ) {
							response = responseObject.responseStatus;
							reportID = responseObject.reportID;
						}
					} catch (HttpResponseException ex) {
						Logger.ERROR(TAG, "[HttpResponseException] Error while sending data", ex);
						negative = true;
						int code = ex.getStatusCode();
						String messsage = ex.getMessage();
						response = "Status - " + code + " " + messsage;
					} catch (Exception ex) {
						negative = true;
						Logger.ERROR(TAG, "[Exception] Error while sending data", ex);
						String messsage = ex.getMessage();
						response = "Status - " + messsage;
					}
					if (response != null) {
						Logger.DEBUG(TAG, "response: " + response);
						bundle.putString(Constants.REPORT_UI_RESPONSE, response);
					}
					if (reportID != null) {
						Logger.DEBUG(TAG, "reportID: " + reportID);
						bundle.putString(Constants.REPORT_LAST_ID, reportID);
					}
					break;
			}
			bundle.putBoolean(Constants.REPORT_UI_RESPONSE_NEGATIVE, negative);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

}

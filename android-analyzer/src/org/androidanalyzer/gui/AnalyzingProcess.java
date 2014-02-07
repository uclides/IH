package org.androidanalyzer.gui;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.AnalyzerCore;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Start analyzing operation in a separate process and sends updates via Progress
 * Handler
 */
public class AnalyzingProcess implements Runnable {

	private static final String TAG = "Analyzer-AnalyzingProcess";

	private Handler dataHandler;
	private AnalyzerCore core;

	public AnalyzingProcess(Handler dataHandler, AnalyzerCore core) {
		this.dataHandler = dataHandler;
		this.core = core;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Data data = core.startAnalyzing();
		Logger.DEBUG(TAG, "[run] data: " + data);
		Message msg = dataHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putParcelable(Constants.GUI_HANDLER_SEND, data);
		msg.setData(bundle);
		dataHandler.sendMessage(msg);
	}
}
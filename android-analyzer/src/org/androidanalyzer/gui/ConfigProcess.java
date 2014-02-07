package org.androidanalyzer.gui;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.AnalyzerCore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ConfigProcess implements Runnable {

	private Handler dataHandler;
	private AnalyzerCore core;

	public ConfigProcess(Handler dialogHandler, AnalyzerCore core) {
		this.dataHandler = dialogHandler;
		this.core = core;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		core.loadPluginMetadata();
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putBoolean(Constants.REPORT_UI_DONE, true);
		msg.setData(bundle);
		dataHandler.sendMessage(msg);
	}

}

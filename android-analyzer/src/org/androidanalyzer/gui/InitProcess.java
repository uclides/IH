package org.androidanalyzer.gui;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.AnalyzerCore;

import android.os.Bundle;
import android.os.Message;

public class InitProcess implements Runnable {

	private DialogHandler dataHandler;
	private AnalyzerCore core;

	public InitProcess(DialogHandler dialogHandler, AnalyzerCore core) {
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
		core.readFromCache();
		int counter = core.getPluginUIRequired();
		Message msg = dataHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putInt(Constants.GUI_HANDLER_PLUGIN_UI, counter);
		msg.setData(bundle);
		dataHandler.sendMessage(msg);
	}

}

package org.androidanalyzer.gui;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * This Handler is used to update the progress bar during the analyzing process
 *
 */
public class DialogHandler extends Handler {
  private static final String TAG = "Analyzer-ProgressHandler";
  
  AnalyzerList analyzerList;
  
  public DialogHandler(AnalyzerList progressActivity) {
    this.analyzerList = progressActivity;
  }

  /*
   * (non-Javadoc)
   * @see android.os.Handler#handleMessage(android.os.Message)
   */
  @Override
	public void handleMessage(Message msg) {
		Bundle bundle = msg.getData();
		if (bundle.containsKey(Constants.GUI_HANDLER_PROGRESS)) {
			Logger.DEBUG(TAG, "Update progress dialog");
			int total = bundle.getInt(Constants.GUI_HANDLER_PROGRESS_TOTAL_PLUGINS);
			int current_plugin = bundle.getInt(Constants.GUI_HANDLER_PROGRESS_CURRENT_PLUGIN);
			String name = bundle.getString(Constants.GUI_HANDLER_PROGRESS_PLUGIN_NAME);
			analyzerList.updateProgress(total, current_plugin, name);
		} else if (bundle.containsKey(Constants.GUI_HANDLER_SEND)) {
			Logger.DEBUG(TAG, "Close progress dialog");
			Data result = (Data) bundle.get(Constants.GUI_HANDLER_SEND);
			analyzerList.hideProgress(result);
		} else if (bundle.containsKey(Constants.GUI_HANDLER_PLUGIN_UI)) {
			Logger.DEBUG(TAG, "Start progress dialog");
			int isPluginUIRequired = bundle.getInt(Constants.GUI_HANDLER_PLUGIN_UI);
			analyzerList.closeWaitDialog();
			if (isPluginUIRequired > 0) {
				analyzerList.showCustomDialog(isPluginUIRequired);
			} else {
				analyzerList.showDialog(Constants.PROGRESS_DIALOG);
				analyzerList.startAnalisys();
			}
		}
	}
}

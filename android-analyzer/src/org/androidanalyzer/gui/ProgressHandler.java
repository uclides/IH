package org.androidanalyzer.gui;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * This Handler is used to update the progress bar during the analyzing process
 *
 */
public class ProgressHandler extends Handler {

  AnalyzerList analyzerList;
  
  public ProgressHandler(AnalyzerList progressActivity) {
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
      int total = bundle.getInt(Constants.GUI_HANDLER_PROGRESS_TOTAL_PLUGINS);
      int current_plugin = bundle.getInt(Constants.GUI_HANDLER_PROGRESS_CURRENT_PLUGIN);
      String name = bundle.getString(Constants.GUI_HANDLER_PROGRESS_PLUGIN_NAME);
      analyzerList.updateProgress(total, current_plugin, name);
    } else if (bundle.containsKey(Constants.GUI_HANDLER_SEND)) {
      Data result = (Data)bundle.get(Constants.GUI_HANDLER_SEND);
      analyzerList.hideProgress(result);
    }
  }
  
  
}

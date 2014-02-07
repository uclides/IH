package org.androidanalyzer.core;

import java.util.StringTokenizer;

import org.androidanalyzer.R;
import org.androidanalyzer.core.utils.Logger;

/**
 * This class is responsible for encoding and decoding plugin status   
 */
public class PluginStatus {
  
  public static final int STATUS_PASSED = R.drawable.ok_icon_3;
  public static final int STATUS_FAILED = R.drawable.failed_icon_3;
  public static final int STATUS_NOT_RUN = R.drawable.not_run_icon_5;
  
  private static final String DELIM = ":";
  
  public static String encodeStatus(PluginStatus pluginStatus) {
    if (pluginStatus == null) {
      Logger.DEBUG("PluginStatu", "pluginStatus is null!");
      return null;
    }
    try {
      StringBuffer sb = new StringBuffer(pluginStatus.getPluginClass());
      sb.append(DELIM).append(pluginStatus.getPluginName());
      sb.append(DELIM).append(pluginStatus.getStatus());
      sb.append(DELIM).append(pluginStatus.getLastRun());
      sb.append(DELIM).append(pluginStatus.isEnabled());
      sb.append(DELIM).append(pluginStatus.getPluginDescription());
      return sb.toString();
    } catch (Throwable t) {
      Logger.ERROR("PluginStatus", t.getMessage(), t);
    }
    return null;
  }
  
  public static final PluginStatus decodeStatus(String pluginStatus) {
    if (pluginStatus == null) {
      Logger.DEBUG("PluginStatus", "pluginStatus is null!");
      return null;
    }
    try {
      StringTokenizer sTok = new StringTokenizer(pluginStatus, DELIM, false);
      String pluginClass = sTok.nextToken();
      String pluginName = sTok.nextToken();
      int status = Integer.valueOf(sTok.nextToken());
      long lastRun = Long.valueOf(sTok.nextToken());
      boolean enabled = Boolean.valueOf(sTok.nextToken());
      String description = sTok.nextToken();
      PluginStatus decoded = new PluginStatus(pluginName, pluginClass, status, lastRun, description);
      decoded.setEnabled(enabled);
      return decoded;
    } catch (Throwable t) {
      Logger.ERROR("PluginStatus", t.getMessage(), t);
    }
    return null;
  }
  
  private String pluginName;
  private String pluginClass;
  private String pluginDescription;
  private int status = STATUS_NOT_RUN;
  long lastRun = -1;
  boolean enabled = true;
  
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getPluginName() {
    return pluginName;
  }

  public void setPluginName(String pluginName) {
    this.pluginName = pluginName;
  }

  public String getPluginClass() {
    return pluginClass;
  }

  public void setPluginClass(String pluginClass) {
    this.pluginClass = pluginClass;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getLastRun() {
    return lastRun;
  }

  public void setLastRun(long lastRun) {
    this.lastRun = lastRun;
  }

  public void setPluginDescription(String pluginDescription) {
    this.pluginDescription = pluginDescription;
  }

  public String getPluginDescription() {
    return pluginDescription;
  }

  public PluginStatus(String pluginName, String pluginClass, int status, long lastRun, String description) {
    this.pluginName = pluginName;
    this.pluginClass = pluginClass;
    this.status = status;
    this.lastRun = lastRun;
    this.pluginDescription = description;
  }
  

}

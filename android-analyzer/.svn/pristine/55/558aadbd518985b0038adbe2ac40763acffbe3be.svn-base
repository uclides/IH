package org.androidanalyzer.gui;

import org.androidanalyzer.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * This class holds the Tabs (Core/Plugins/About) of the Settings Activity 
 *
 */
public class AnalyzerSettings extends TabActivity {
	
	private static final String TAB_CORE = "core";
	private static final String TAB_PLUGINS = "plugins";
	private static final String TAB_ABOUT = "about";
	
	/** Called when the activity is first created. */
  /*
   * (non-Javadoc)
   * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
   */
	@Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      int index = 0;
      Resources res = getResources(); // Resource object to get Drawables
      TabHost tabHost = getTabHost();
      TabHost.TabSpec spec;  // Resusable TabSpec for each tab
      Intent intent;  // Reusable Intent for each tab
      // Create an Intent to launch an Activity for the tab (to be reused)
      intent = new Intent().setClass(this, SettingsActivity.class);

      // Initialize a TabSpec for each tab and add it to the TabHost
      spec = tabHost.newTabSpec(TAB_CORE).setIndicator(getString(R.string.tab_core_settings),
                        res.getDrawable(R.drawable.prefs_select))
                    .setContent(intent);
      tabHost.addTab(spec);

      // Do the same for the other tabs
      intent = new Intent().setClass(this, PluginConfiguration.class);
      spec = tabHost.newTabSpec(TAB_PLUGINS).setIndicator(getString(R.string.tab_plugin_settings),
                        res.getDrawable(R.drawable.prefs_select))
                    .setContent(intent);
      tabHost.addTab(spec);

      intent = new Intent().setClass(this, AboutActivity.class);
      spec = tabHost.newTabSpec(TAB_ABOUT).setIndicator(getString(R.string.tab_about),
                        res.getDrawable(R.drawable.about_select))
                    .setContent(intent);
      tabHost.addTab(spec);

      tabHost.setCurrentTab(index);        
  }
  
}

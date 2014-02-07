/**
 * 
 */
package org.androidanalyzer.core;

import java.util.Hashtable;

/**
 * Callback interface used by the Analyzer Core to notify the UI for progress
 * with the analysis
 * 
 */
public interface UICallback {

	/**
	 * Constant for the number of plugins that are available
	 */
	public static final String NUMBER_OF_PLUGINS = "org.androidanalyzer.core.number_of_plugins";
	/**
	 * Constant for the name of the plugin the action relates to
	 */
	public static final String NAME_OF_PLUGIN = "org.androidanalyzer.core.name_of_plugin_analyzing";
	/**
	 * Constant for flag on plugin failure
	 */
	public static final String PLUGIN_FAILED = "org.androidanalyzer.core.plugin_failed";
	/**
	 * Constant for plugin started
	 */
	public static final String PLUGIN_STARTED_ANALYZING = "org.androidanalyzer.core.plugin_started";
	/**
	 * Constant for plugin finished
	 */
	public static final String PLUGIN_FINISHED_ANALYZING = "org.androidanalyzer.core.plugin.finished";

	/**
	 * For calling the UI with status of the Analysis progress
	 * 
	 * The Analyzer Core calls this method to notify the UI for the events of the
	 * analysis process. The hashtable provided contains values with this class
	 * fields. The number of all plugins are as value with key NUMBER_OF_PLUGINS.
	 * The name of the plugin analyzing in the moment is set with key
	 * NAME_OF_PLUGIN. If the plugin failed PLUGIN_FAILED is set/present in the
	 * table. When plugin is starting its analysis PLUGIN_STARTED_ANALYZING key is
	 * present in the hashtable. When plugin has finished its analysis
	 * PLUGIN_FINISHED_ANALYZING key is present in the hashtable.
	 * 
	 * @param props
	 *          Hashtable with values for the update
	 */
	void updateAnalysisProgress(Hashtable progress);

	/**
	 * For calling the UI when plugin is registered
	 * 
	 * @param iAnalyzerPlugin
	 */
	void notifyPluginRegistered(IAnalyzerPlugin iAnalyzerPlugin);

	/**
	 * For calling the UI when plugin is unregistered
	 * 
	 * @param iAnalyzerPlugin
	 */
	void notifyPluginUnregistered(IAnalyzerPlugin iAnalyzerPlugin);

}

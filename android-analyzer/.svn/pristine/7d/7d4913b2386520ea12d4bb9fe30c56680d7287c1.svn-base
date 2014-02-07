package org.androidanalyzer.plugins.dummyplugin;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.plugins.AbstractPlugin;

/**
 * @author k.raev
 */
public class DummyExternalPlugin extends AbstractPlugin {

	private static final String NAME = "Dummy plugin external";
	private static final String DUMMY_DATA_NAME = "Dummy Data";
	private static final String DUMMY_DATA_INFO_NAME = "Dummy Info";
	private static final String DUMMY_DATA_INFO_VALUE = "No info here";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String DESCRIPTION = "Demonstrates how to create plugin and how it works";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginName ()k
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginTimeout ()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginVersion ()
	 */
	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginVendor()
	 */
	@Override
	public String getPluginVendor() {
		return PLUGIN_VENDOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginDescription()
	 */
	@Override
	public String getPluginDescription() {
		return DESCRIPTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#isPluginRequiredUI()
	 */
	@Override
	public boolean isPluginRequiredUI() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin# getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getData()
	 */
	@Override
	protected Data getData() {
		Data dummy = new Data();
		try {
			dummy.setName(DUMMY_DATA_NAME);
			Data dummyInfo = new Data();
			dummyInfo.setName(DUMMY_DATA_INFO_NAME);
			dummyInfo.setValue(DUMMY_DATA_INFO_VALUE);
			dummy.setValue(dummyInfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dummy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin# stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		this.stopSelf();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}
}

package org.androidanalyzer.plugins.radiointerfaces;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * RadioInterfacesPlugin class used for gathering data about radio interfaces
 * that available on the device.
 * 
 */
public class RadioInterfacesPlugin extends AbstractPlugin {
	private static final String NAME = "Radio Interfaces Plugin";
	private static final String PLUGIN_VERSION = "1.0.1";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String RADIOINTERFACES = "Radio Interfaces";
	private static final String TAG = "Analyzer-RadioInterfacesPlugin";
	private static final String GSM = "GSM";
	private static final String CDMA = "CDMA";
	private static final String GPRS = "GPRS";
	private static final String EDGE = "EDGE";
	private static final String UMTS = "UMTS";
	private static final String LTE  = "LTE";
	private static final String UNKNOWN = "UNKNOWN";

	private static final String HSUPA = "HSUPA";
	private static final String HSDPA = "HSDPA";
	private static final String HSPA = "HSPA";
	private static final String EVDO_0 = "EVDO_0";
	private static final String EVDO_A = "EVDO_A";
	private static final String IDEN = "IDEN";
	private static final String TYPE_1xRTT = "1xRTT";

	private static final String G2G = "2G";
	private static final String G3G = "3G";
	private static final String G4G = "4G";
	/** GSM Bands */
	private static final String BAND = "Bands";
	private static final String BAND_METRIC = "MHz";
	private static final String B380 = "380";
	private static final String B410 = "410";
	private static final String B450 = "450";
	private static final String B480 = "480";
	private static final String B710 = "710";
	private static final String B750 = "750";
	private static final String B810 = "810";
	private static final String B850 = "850";
	private static final String B900 = "900";
	private static final String B1800 = "1800";
	private static final String B1900 = "1900";
	private static final String DESCRIPTION = "Collects data on the radio interface and capabilities";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginName ()
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
	public boolean isPluginUIRequired() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getData()
	 */
	@Override
	protected Data getData() {
		Logger.DEBUG(TAG, "getData in Radio interfaces Plugin");
		Data parent = new Data();
		ArrayList<Data> masterChildren = new ArrayList<Data>();
		try {
			parent.setName(RADIOINTERFACES);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Radio interfaces parent node!", e);
			status = "Could not set Radio interfaces parent node!";
			return null;
		}
		TelephonyManager mTeleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (mTeleManager != null) {
			int phoneTypeInt = -1;
			Data radioSupported = new Data();
			try {
				radioSupported.setName("Supported");
				phoneTypeInt = mTeleManager.getPhoneType();
				if (phoneTypeInt != TelephonyManager.PHONE_TYPE_NONE) {
					radioSupported.setValue(Constants.NODE_VALUE_YES);
				} else {
					radioSupported.setValue(Constants.NODE_VALUE_NO);
				}
				radioSupported.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
				masterChildren.add(radioSupported);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create supported node for Radio interfaces!", e);
				status = "Could not create supported node for Radio interfaces!";
			}
			if ( phoneTypeInt > 0 ) {
				Data phoneType = new Data();
				try {
					phoneType.setName("Phone Type");
					switch ( phoneTypeInt ) {
						case 1://GSM
							phoneType.setValue(GSM);
							break;
						case 2://CDMA
							phoneType.setValue(CDMA);
							break;
						default://future versions may support other networks
							phoneType.setValue(UNKNOWN+"("+phoneTypeInt+")");
							break;
					}
					masterChildren.add(phoneType);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not create supported node for Radio interfaces!", e);
					status = "Could not create supported node for Radio interfaces";
				}
	
				// if (mTeleManager.getPhoneType() ==
				// TelephonyManager.PHONE_TYPE_GSM) {
				// int networkType = mTeleManager.getNetworkType();
				// Logger.DEBUG(TAG, "Network: " + networkType);
				// String type =
				// getNetworkTypeReadableName(networkType);
				// Logger.DEBUG(TAG, "Network: " + type);
				// ArrayList<Data> twoChildren = new
				// ArrayList<Data>(2);
				// Data twoG = get2GFeatures(twoChildren, type);
				// masterChildren.add(twoG);
				// }
				/*
				 * else if (mTeleManager.getPhoneType() ==
				 * TelephonyManager.PHONE_TYPE_CDMA) { // check the phone type, cdma is
				 * not available before API 2.0 try { Class.forName("CdmaCellLocation"); }
				 * catch (Exception ex) { Logger.ERROR(TAG,
				 * "Failed to initialize CDMA cell class", ex); } }
				 */
	
				// Creating Current Network node
				Data currentNetwork = new Data();
				try {
					currentNetwork.setName("Current Network");
					Data networkType = new Data();
					networkType.setName("Type");
					networkType.setValue(getNetworkTypeReadableName(mTeleManager.getNetworkType()));
					currentNetwork.setValue(networkType);
					masterChildren.add(currentNetwork);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not create supported node for Radio interfaces!", e);
					status = "Could not create supported node for Radio interfaces!";
				}
			}
		}
		parent = addToParent(parent, masterChildren);
		return parent;
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin# stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		Logger.DEBUG(TAG, "Service is stopped!");
		this.stopSelf();
	}

	private String getNetworkTypeReadableName(int networkType) {
		switch (networkType) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return GPRS;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return EDGE;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return UMTS;
		case 8: // NETWORK_TYPE_HSDPA
			return HSDPA;
		case 9: // NETWORK_TYPE_HSUPA
			return HSUPA;
		case 10: // NETWORK_TYPE_HSPA
			return HSPA;
		case 4: // NETWORK_TYPE_CDMA
			return CDMA;
		case 5: // NETWORK_TYPE_EVDO_0
			return EVDO_0;
		case 6: // NETWORK_TYPE_EVDO_A
			return EVDO_A;
		case 7: // NETWORK_TYPE_1xRTT
			return TYPE_1xRTT;
		case 11: // NETWORK_TYPE_IDEN
			return IDEN;
		case 13: //NETWORK_TYPE_LTE (since API Level 11)
			return LTE;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
		default:
			break;
		}
		return UNKNOWN;
	}

	private Data get2GFeatures(ArrayList<Data> twoChildren, String type) {
		Data twoGHolder = new Data();
		Data networkTypeHolder = new Data();
		Data bankHolder = new Data();
		try {
			networkTypeHolder.setName(type);
			networkTypeHolder.setValue(Constants.NODE_VALUE_YES);
			networkTypeHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			networkTypeHolder.setStatus(Constants.NODE_STATUS_OK);
			twoChildren.add(networkTypeHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			bankHolder.setName(BAND);
			bankHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			bankHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			bankHolder.setValueMetric(BAND_METRIC);
			twoChildren.add(bankHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			twoGHolder.setName(G2G);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set 2G node!", e);
		}
		twoGHolder = addToParent(twoGHolder, twoChildren);
		return twoGHolder;
	}

	private Data get3GFeatures(ArrayList<Data> twoChildren, String type) {
		Data threeGHolder = new Data();
		Data networkTypeHolder = new Data();
		Data bankHolder = new Data();
		try {
			networkTypeHolder.setName(type);
			networkTypeHolder.setValue(Constants.NODE_VALUE_YES);
			networkTypeHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			networkTypeHolder.setStatus(Constants.NODE_STATUS_OK);
			twoChildren.add(networkTypeHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			bankHolder.setName(BAND);
			bankHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			bankHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			bankHolder.setValueMetric(BAND_METRIC);
			twoChildren.add(bankHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			threeGHolder.setName(G2G);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set 2G node!", e);
		}
		threeGHolder = addToParent(threeGHolder, twoChildren);
		return threeGHolder;
	}

	private Data get4GFeatures(ArrayList<Data> twoChildren, String type) {
		Data fourGHolder = new Data();
		Data networkTypeHolder = new Data();
		Data bandHolder = new Data();
		try {
			networkTypeHolder.setName(type);
			networkTypeHolder.setValue(Constants.NODE_VALUE_YES);
			networkTypeHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			networkTypeHolder.setStatus(Constants.NODE_STATUS_OK);
			twoChildren.add(networkTypeHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			bandHolder.setName(BAND);
			bandHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			bandHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			bandHolder.setValueMetric(BAND_METRIC);
			twoChildren.add(bandHolder);
		} catch (Exception e1) {
			Logger.ERROR(TAG, "Could not set 2G node!", e1);
		}
		try {
			fourGHolder.setName(G2G);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set 2G node!", e);
		}
		fourGHolder = addToParent(fourGHolder, twoChildren);
		return fourGHolder;
	}
}

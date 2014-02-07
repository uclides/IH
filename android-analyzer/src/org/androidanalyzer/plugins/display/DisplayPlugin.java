package org.androidanalyzer.plugins.display;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * DisplayPlugin class used to gathering display capabilities of the device.
 */
public class DisplayPlugin extends AbstractPlugin {
	
	private static final String NAME = "Display Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String TAG = "Analyzer-DisplayPlugin";
	private static final String LOCATION = "Location";
	private static final String DISPLAY = "Display";
	private static final String SIZE = "Size";
	private static final String DSPL_SIZE_METRIC = "inch";
	private static final String DSPL_METRIC = "pixel";
	private static final String HRES = "Horizontal resolution";
	private static final String VRES = "Vertical resolution";
	private static final String TOUCH = "Touch support";
	private static final String TOUCH_METHOD = "Touch method";
	private static final String TOUCH_METHOD_UNKNOWN = "Unknown";
	private static final String TOUCH_METHOD_STYLUS = "Stylus";
	private static final String TOUCH_METHOD_FINGER = "Finger";
	private static final String COLOR_DEPTH = "Color depth";
	private static final String COLOR_DEPTH_METRIC = "bit";
	private static final String DENSITY = "Density";
	private static final String DENSITY_LOGICAL = "Logical";
	private static final String DENSITY_LOGICAL_DPI = "Logical DPI";
	private static final String DENSITY_LOGICAL_DPI_FIELD_NAME = "densityDpi";
	private static final String DENSITY_SCALED = "Scaled";
	private static final String DENSITY_HORIZONTAL = "Horizontal";
	private static final String DENSITY_VERTICAL = "Vertical";
	private static final String DENSITY_METRIC = "dpi";
	private static final String REFRESH_RATE = "Refresh rate";
	private static final String REFRESH_RATE_METRIC = "fps";
	private static final String DISPLAY_NAME = "Display-";
	private static final String DESCRIPTION = "Collects data on available displays and their capabilities";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginTimeout()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginVersion()
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getData()
	 */
	@Override
	protected Data getData() {
		Logger.DEBUG(TAG, "getData in Display Plugin");
		Data parent = new Data();
		ArrayList<Data> masterChildren = new ArrayList<Data>(2);
		try {
			parent.setName(DISPLAY);
			Display[] displays = getDisplays();
			ArrayList<Data> displayChildren = new ArrayList<Data>(2);
			for (Display display : displays) {
				Data mDisplay = getDisplayMetrics(display, displayChildren);
				Logger.DEBUG(TAG, "mDisplay: " + mDisplay);
				masterChildren.add(mDisplay);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display parent node!", e);
			status = "Could not set Display parent node!";
			return null;
		}
		parent = addToParent(parent, masterChildren);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		Logger.DEBUG(TAG, "Service is stopped!");
		this.stopSelf();
	}

	private Display[] getDisplays() {
		WindowManager winMgr = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		// TODO How to get all displays connected to the device
		Display[] displays = new Display[1];
		displays[0] = winMgr.getDefaultDisplay();
		Logger.DEBUG(TAG, "displays connected to the device: " + displays[0]);
		return displays;
	}

	private Data getDisplayMetrics(Display display, ArrayList<Data> displayChildren) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		Data displayHolder = new Data();
		Data locationHolder = new Data();
		Data sizeHolder = new Data();
		Data densityHolder = new Data();
		Data xResolutionHolder = new Data();
		Data yResolutionHolder = new Data();
		Data logicalDensityHolder = new Data();
		Data logicalDPIDensityHolder = new Data();
		Data scaledDensityHolder = new Data();
		Data horizontalDensityHolder = new Data();
		Data verticalDensityHolder = new Data();
		Data touchHolder = new Data();
		Data colorDepthHolder = new Data();
		Data refreshRateHolder = new Data();

		try {
			locationHolder.setName(LOCATION);
			locationHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			locationHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			locationHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(locationHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Location node!", e);
			status = "Could not set Display Location node!";
		}

		try {
			sizeHolder.setName(SIZE);
			double diagonalSizeInInch = Math.sqrt(Math.pow(outMetrics.widthPixels / outMetrics.xdpi, 2)
					+ Math.pow(outMetrics.heightPixels / outMetrics.ydpi, 2));
			Logger.DEBUG(TAG, "Real diagonal size: " + diagonalSizeInInch);
			DecimalFormat twoPlaces = new DecimalFormat("0.0");
			String size = twoPlaces.format(diagonalSizeInInch);
			sizeHolder.setValue(size);
			Logger.DEBUG(TAG, "Display size:" + size);
			sizeHolder.setValueMetric(DSPL_SIZE_METRIC);
			sizeHolder.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
			sizeHolder.setStatus(Constants.NODE_STATUS_OK);
			sizeHolder.setConfirmationLevel(Constants.NODE_CONFIRMATION_LEVEL_UNCONFIRMED);
			displayChildren.add(sizeHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Size node!", e);
			status = "Could not set Display Size node!";
		}

		try {
			xResolutionHolder.setName(HRES);
			xResolutionHolder.setValue(String.valueOf(outMetrics.widthPixels));
			Logger.DEBUG(TAG, "H Res:" + outMetrics.xdpi);
			xResolutionHolder.setValueMetric(DSPL_METRIC);
			xResolutionHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			xResolutionHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(xResolutionHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Horizontal Resolution node!", e);
			status = "Could not set Display Horizontal Resolution node!";
		}

		try {
			yResolutionHolder.setName(VRES);
			yResolutionHolder.setValue(String.valueOf(outMetrics.heightPixels));
			Logger.DEBUG(TAG, "V Res:" + outMetrics.ydpi);
			yResolutionHolder.setValueMetric(DSPL_METRIC);
			yResolutionHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			yResolutionHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(yResolutionHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Vertical Resolution node!", e);
			status = "Could not set Display Vertical Resolution node!";
		}
		try {//Density
			densityHolder.setName(DENSITY);
			displayChildren.add(densityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density node!", e);
			status = "Could not set Density node!"; 
		}
		try {//Density -> Logical
			logicalDensityHolder.setName(DENSITY_LOGICAL);
			logicalDensityHolder.setValue(getFormattedDouble(outMetrics.density, "#0.0"));
			Logger.DEBUG(TAG, "Logical:" + outMetrics.density);
			logicalDensityHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			logicalDensityHolder.setStatus(Constants.NODE_STATUS_OK);
			logicalDensityHolder.setValueMetric(DENSITY_METRIC);
			densityHolder.setValue(logicalDensityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density Logical node!", e);
			status = "Could not set Density Logical node!"; 
		}

		try {//Density -> Logical DPI
			logicalDPIDensityHolder.setName(DENSITY_LOGICAL_DPI);
			String logicalDPI = getLogicalDPI(outMetrics);
			Logger.DEBUG(TAG, "Logical DPI:" + outMetrics.density);
			if ( logicalDPI != null && logicalDPI.length() > 0 ) {
				logicalDPIDensityHolder.setValue(logicalDPI);
				logicalDPIDensityHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
				logicalDPIDensityHolder.setStatus(Constants.NODE_STATUS_OK);
				logicalDPIDensityHolder.setValueMetric(DENSITY_METRIC);
			} else {
				logicalDPIDensityHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
				logicalDPIDensityHolder.setStatus(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			}
			densityHolder.setValue(logicalDPIDensityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density Logical DPI node!", e);
			status = "Could not set Density Logical DPI node!"; 
		}
		
		try {//Density -> Scaled
			scaledDensityHolder.setName(DENSITY_SCALED);
			scaledDensityHolder.setValue(getFormattedDouble(outMetrics.scaledDensity, "#0.0"));
			Logger.DEBUG(TAG, "Scaled:" + outMetrics.scaledDensity);
			scaledDensityHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			scaledDensityHolder.setStatus(Constants.NODE_STATUS_OK);
			scaledDensityHolder.setValueMetric(DENSITY_METRIC);
			densityHolder.setValue(scaledDensityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density Scaled node!", e);
			status = "Could not set Density Scaled node!"; 
		}
		
		try {//Density -> Horizontal
			horizontalDensityHolder.setName(DENSITY_HORIZONTAL);
			horizontalDensityHolder.setValue(getFormattedDouble(outMetrics.xdpi, "#0.0"));
			Logger.DEBUG(TAG, "Horizontal:" + outMetrics.xdpi);
			horizontalDensityHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			horizontalDensityHolder.setStatus(Constants.NODE_STATUS_OK);
			horizontalDensityHolder.setValueMetric(DENSITY_METRIC);
			densityHolder.setValue(horizontalDensityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density Horizontal node!", e);
			status = "Could not set Density Horizontal node!"; 
		}
		
		try {//Density -> Vertical
			verticalDensityHolder.setName(DENSITY_VERTICAL);
			verticalDensityHolder.setValue(getFormattedDouble(outMetrics.ydpi, "#0.0"));
			Logger.DEBUG(TAG, "Vertical:" + outMetrics.ydpi);
			verticalDensityHolder.setValueType(Constants.NODE_VALUE_TYPE_INT);
			verticalDensityHolder.setStatus(Constants.NODE_STATUS_OK);
			verticalDensityHolder.setValueMetric(DENSITY_METRIC);
			densityHolder.setValue(verticalDensityHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Density Vertical node!", e);
			status = "Could not set Density Vertical node!"; 
		}
		
		//Touch support
	    Configuration c = getResources().getConfiguration();
	    int touchMethod = c.touchscreen;
		try {
			touchHolder.setName(TOUCH);
			if ( touchMethod == Configuration.TOUCHSCREEN_UNDEFINED || touchMethod == Configuration.TOUCHSCREEN_NOTOUCH )
				touchHolder.setValue(Constants.NODE_VALUE_NO);
			else 
				touchHolder.setValue(Constants.NODE_VALUE_YES);
			touchHolder.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			touchHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(touchHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Touch Support node!", e);
			status = "Could not set Display Touch Support node!";
		}
		if ( touchMethod != Configuration.TOUCHSCREEN_NOTOUCH ) {
			Data touchMethodHolder = new Data();
			try {
				touchMethodHolder.setName(TOUCH_METHOD);
				if ( touchMethod == Configuration.TOUCHSCREEN_STYLUS )
					touchMethodHolder.setValue(TOUCH_METHOD_STYLUS);
				else if ( touchMethod == Configuration.TOUCHSCREEN_FINGER ) 
					touchMethodHolder.setValue(TOUCH_METHOD_FINGER);
				else
					touchMethodHolder.setValue(TOUCH_METHOD_UNKNOWN);
				touchMethodHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
				touchMethodHolder.setStatus(Constants.NODE_STATUS_OK);
				displayChildren.add(touchMethodHolder);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Display Touch Method node!", e);
				status = "Could not set Display Touch Method node!";
			}
		}
		
		try {
			colorDepthHolder.setName(COLOR_DEPTH);
			colorDepthHolder.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
			colorDepthHolder.setValueType(Constants.NODE_VALUE_TYPE_STRING);
			colorDepthHolder.setValueMetric(COLOR_DEPTH_METRIC);
			colorDepthHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(colorDepthHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Color Depth node!", e);
			status = "Could not set Display Color Depth node!";
		}

		try {
			refreshRateHolder.setName(REFRESH_RATE);
			refreshRateHolder.setValue(String.valueOf(display.getRefreshRate()));
			Logger.DEBUG(TAG, "Refresh rate:" + display.getRefreshRate());
			refreshRateHolder.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
			refreshRateHolder.setValueMetric(REFRESH_RATE_METRIC);
			refreshRateHolder.setStatus(Constants.NODE_STATUS_OK);
			displayChildren.add(refreshRateHolder);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display Refresh rate node!", e);
			status = "Could not set Display Refresh rate node!";
		}
		/*
		 * TODO: There is no APIs to get values for: Location, Display size, Display
		 * technology, Touch support, Color depth
		 */
		displayHolder = addToParent(displayHolder, displayChildren);
		try {
			displayHolder.setName(DISPLAY_NAME + display.getDisplayId());
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Display node!", e);
			status = "Could not set Display node!";
		}
		return displayHolder;

	}

	private String getLogicalDPI(DisplayMetrics outMetrics) {
		String result = null;
		try {
			Field field = DisplayMetrics.class.getDeclaredField(DENSITY_LOGICAL_DPI_FIELD_NAME);
			Integer i = (Integer) field.get(outMetrics);
			result = String.valueOf(i);
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		
		return result;
	}
	
	private String getFormattedDouble(double size, String format) {
		return new DecimalFormat(format).format(size);
	}
}
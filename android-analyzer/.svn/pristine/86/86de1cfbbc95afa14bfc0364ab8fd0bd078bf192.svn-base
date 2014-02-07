package org.androidanalyzer.plugins.locationaccuracy;

import java.util.ArrayList;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

/**
 * LocationAccuracyPlugin class used to read previously saved data for GPS
 * capabilities and send it to the Core
 * 
 */

public class LocationAccuracyPlugin extends AbstractPlugin {

	private static final String TAG = "Analyzer-LocationPlugin";
	private static final String NAME = "Location Accuracy Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String PLUGIN_DESCRIPTION = "Benchmarks the accuracy of the positioning technology.";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginTimeout()
	 */
	@Override
	public long getPluginTimeout() {
		return 10000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginVersion()
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#isPluginRequiredUI()
	 */
	@Override
	public boolean isPluginUIRequired() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginDescription()
	 */
	@Override
	public String getPluginDescription() {
		return PLUGIN_DESCRIPTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginClassName()
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
		Logger.DEBUG(TAG, "getData in Location Accuracy Plugin");
		Data parent = new Data();
		ArrayList<Data> masterChildren = new ArrayList<Data>();
		try {
			parent.setName(NAME);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Location Accuracy parent node!", e);
			status = "Could not set Location Accuracy parent node!";
			return null;
		}
		/* Test ID */
		try {
			Data testID = new Data();
			testID.setName(LocationAccuracyConstants.TEST_ID);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.TEST_ID);
			if (value != null && value.length() > 0) {
				testID.setValue(value);
				testID.setValueType(Constants.NODE_VALUE_TYPE_INT);
				masterChildren.add(testID);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Test ID node!", e);
		}

		/* Application version */
		try {
			Data appVersion = new Data();
			appVersion.setName(LocationAccuracyConstants.APP_VERSION);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.APP_VERSION);
			if (value != null && value.length() > 0) {
				appVersion.setValue(value);
				masterChildren.add(appVersion);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Application version node!", e);
		}

		/* Device */
		/*try {
			Data device = new Data();
			device.setName(LocationAccuracyConstants.DEVICE);
			ArrayList<Data> deviceChildren = new ArrayList<Data>();
			try {
				Data deviceBrand = new Data();
				deviceBrand.setName(LocationAccuracyConstants.DEVICE_BRAND);
				String brand = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.DEVICE_BRAND);
				if (brand != null && brand.length() > 0) {
					deviceBrand.setValue(brand);
					deviceChildren.add(deviceBrand);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Device brand node!", e);
			}

			try {
				Data deviceProduct = new Data();
				deviceProduct.setName(LocationAccuracyConstants.DEVICE_PRODUCT);
				String product = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.DEVICE_PRODUCT);
				if (product != null && product.length() > 0) {
					deviceProduct.setValue(product);
					deviceChildren.add(deviceProduct);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Device brand node!", e);
			}
			device = addToParent(device, deviceChildren);
			masterChildren.add(device);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Device node!", e);
		}*/

		/* Android version */
		/*try {
			Data androidVersion = new Data();
			androidVersion.setName(LocationAccuracyConstants.ANDROID_VERSION);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.ANDROID_VERSION);
			if (value != null && value.length() > 0) {
				androidVersion.setValue(value);
				masterChildren.add(androidVersion);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Android version node!", e);
		}*/

		/* Start time */
		try {
			Data startTime = new Data();
			startTime.setName(LocationAccuracyConstants.START_TIME);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.START_TIME);
			if (value != null && value.length() > 0) {
				startTime.setValue(value);
				startTime.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
				masterChildren.add(startTime);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Start time node!", e);
		}

		/* End time */
		try {
			Data endTime = new Data();
			endTime.setName(LocationAccuracyConstants.END_TIME);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.END_TIME);
			if (value != null && value.length() > 0) {
				endTime.setValue(value);
				endTime.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
				masterChildren.add(endTime);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set End time node!", e);
		}

		/* Time to first fix */
		try {
			Data timeToFirstFix = new Data();
			timeToFirstFix.setName(LocationAccuracyConstants.TIME_TO_FIRST_FIX);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.TIME_TO_FIRST_FIX);
			if (value != null && value.length() > 0) {
				timeToFirstFix.setValue(value);
				timeToFirstFix.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				timeToFirstFix.setValueMetric(LocationAccuracyConstants.METRIC_SECOND);
				masterChildren.add(timeToFirstFix);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set TimeToFirstFix node!", e);
		}

		/* Location provider */
		try {
			Data locationProvider = new Data();
			locationProvider.setName(LocationAccuracyConstants.LOCATION_PROVIDER);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.LOCATION_PROVIDER);
			if (value != null && value.length() > 0) {
				locationProvider.setValue(value);
				locationProvider.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				locationProvider.setValueMetric(LocationAccuracyConstants.METRIC_SECOND);
				masterChildren.add(locationProvider);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Location provider node!", e);
		}

		/* Sample size */
		try {
			Data sampleSize = new Data();
			sampleSize.setName(LocationAccuracyConstants.SAMPLE_SIZE);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.SAMPLE_SIZE);
			if (value != null && value.length() > 0) {
				sampleSize.setValue(value);
				sampleSize.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				sampleSize.setValueMetric(LocationAccuracyConstants.METRIC_SECOND);
				masterChildren.add(sampleSize);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Sample size node!", e);
		}

		/* Horizontal Error */
		try {
			Data horError = new Data();
			horError.setName(LocationAccuracyConstants.HORIZONTAL);
			ArrayList<Data> horErrorChildren = new ArrayList<Data>();
			try {
				Data min = new Data();
				min.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_MIN);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_MIN);
				if (value != null && value.length() > 0) {
					min.setValue(value);
					min.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					min.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(min);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Min node!", e);
			}

			try {
				Data max = new Data();
				max.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_MAX);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_MAX);
				if (value != null && value.length() > 0) {
					max.setValue(value);
					max.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					max.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(max);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Max node!", e);
			}

			try {
				Data mean = new Data();
				mean.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_MEAN);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_MEAN);
				if (value != null && value.length() > 0) {
					mean.setValue(value);
					mean.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					mean.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(mean);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Mean node!", e);
			}

			try {
				Data fifthPercentile = new Data();
				fifthPercentile.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_50THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_50THPERCENTILE);
				if (value != null && value.length() > 0) {
					fifthPercentile.setValue(value);
					fifthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					fifthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(fifthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 50th Percentile node!", e);
			}

			try {
				Data sixthPercentile = new Data();
				sixthPercentile.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_68THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_68THPERCENTILE);
				if (value != null && value.length() > 0) {
					sixthPercentile.setValue(value);
					sixthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					sixthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(sixthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 68th Percentile node!", e);
			}

			try {
				Data ninethPercentile = new Data();
				ninethPercentile.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_95THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_95THPERCENTILE);
				if (value != null && value.length() > 0) {
					ninethPercentile.setValue(value);
					ninethPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					ninethPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(ninethPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 95th Percentile node!", e);
			}

			try {
				Data standardDev = new Data();
				standardDev.setName(LocationAccuracyConstants.HORIZONTAL_ERROR_STANDARD_DEVIATION);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.HORIZONTAL_ERROR_STANDARD_DEVIATION);
				if (value != null && value.length() > 0) {
					standardDev.setValue(value);
					standardDev.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					standardDev.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					horErrorChildren.add(standardDev);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Standard deviation node!", e);
			}

			if (horErrorChildren.size() > 0) {
				horError = addToParent(horError, horErrorChildren);
				masterChildren.add(horError);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Horizontal Error node!", e);
		}

		/* Vertical Error */
		try {
			Data vertError = new Data();
			vertError.setName(LocationAccuracyConstants.VERTICAL);
			ArrayList<Data> vertErrorChildren = new ArrayList<Data>();
			try {
				Data min = new Data();
				min.setName(LocationAccuracyConstants.VERTICAL_ERROR_MIN);
				String value = LocationAccuracyPreferencesManager
						.loadStringPreference(this, LocationAccuracyConstants.VERTICAL_ERROR_MIN);
				if (value != null && value.length() > 0) {
					min.setValue(value);
					min.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					min.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(min);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Min node!", e);
			}

			try {
				Data max = new Data();
				max.setName(LocationAccuracyConstants.VERTICAL_ERROR_MAX);
				String value = LocationAccuracyPreferencesManager
						.loadStringPreference(this, LocationAccuracyConstants.VERTICAL_ERROR_MAX);
				if (value != null && value.length() > 0) {
					max.setValue(value);
					max.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					max.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(max);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Max node!", e);
			}

			try {
				Data mean = new Data();
				mean.setName(LocationAccuracyConstants.VERTICAL_ERROR_MEAN);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_MEAN);
				if (value != null && value.length() > 0) {
					mean.setValue(value);
					mean.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					mean.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(mean);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Mean node!", e);
			}

			try {
				Data meanAbs = new Data();
				meanAbs.setName(LocationAccuracyConstants.VERTICAL_ERROR_MEAN_ABSOLUTE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_MEAN_ABSOLUTE);
				if (value != null && value.length() > 0) {
					meanAbs.setValue(value);
					meanAbs.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					meanAbs.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(meanAbs);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Mean Absolute node!", e);
			}

			try {
				Data fifthPercentile = new Data();
				fifthPercentile.setName(LocationAccuracyConstants.VERTICAL_ERROR_50THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_50THPERCENTILE);
				if (value != null && value.length() > 0) {
					fifthPercentile.setValue(value);
					fifthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					fifthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(fifthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 50th Percentile node!", e);
			}

			try {
				Data sixthPercentile = new Data();
				sixthPercentile.setName(LocationAccuracyConstants.VERTICAL_ERROR_68THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_68THPERCENTILE);
				if (value != null && value.length() > 0) {
					sixthPercentile.setValue(value);
					sixthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					sixthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(sixthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 68th Percentile node!", e);
			}

			try {
				Data ninethPercentile = new Data();
				ninethPercentile.setName(LocationAccuracyConstants.VERTICAL_ERROR_95THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_95THPERCENTILE);
				if (value != null && value.length() > 0) {
					ninethPercentile.setValue(value);
					ninethPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					ninethPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(ninethPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 95th Percentile node!", e);
			}

			try {
				Data rmse = new Data();
				rmse.setName(LocationAccuracyConstants.VERTICAL_ERROR_RMSE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_RMSE);
				if (value != null && value.length() > 0) {
					rmse.setValue(value);
					rmse.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					rmse.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(rmse);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set RMSE node!", e);
			}

			try {
				Data standardDev = new Data();
				standardDev.setName(LocationAccuracyConstants.VERTICAL_ERROR_STANDARD_DEVIATION);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.VERTICAL_ERROR_STANDARD_DEVIATION);
				if (value != null && value.length() > 0) {
					standardDev.setValue(value);
					standardDev.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					standardDev.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					vertErrorChildren.add(standardDev);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Standard deviation node!", e);
			}
			if (vertErrorChildren.size() > 0) {
				vertError = addToParent(vertError, vertErrorChildren);
				masterChildren.add(vertError);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Vertical Error node!", e);
		}

		/* Estimated Horizontal Accuracy Error */
		try {
			Data ehaeError = new Data();
			ehaeError.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR);
			ArrayList<Data> ehaeErrorChildren = new ArrayList<Data>();
			try {
				Data min = new Data();
				min.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MIN);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MIN);
				if (value != null && value.length() > 0) {
					min.setValue(value);
					min.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					min.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(min);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Min node!", e);
			}

			try {
				Data max = new Data();
				max.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MAX);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MAX);
				if (value != null && value.length() > 0) {
					max.setValue(value);
					max.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					max.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(max);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Max node!", e);
			}

			try {
				Data mean = new Data();
				mean.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN);
				if (value != null && value.length() > 0) {
					mean.setValue(value);
					mean.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					mean.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(mean);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Mean node!", e);
			}

			try {
				Data meanAbs = new Data();
				meanAbs.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE);
				if (value != null && value.length() > 0) {
					meanAbs.setValue(value);
					meanAbs.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					meanAbs.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(meanAbs);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Mean Absolute node!", e);
			}

			try {
				Data fifthPercentile = new Data();
				fifthPercentile.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE);
				if (value != null && value.length() > 0) {
					fifthPercentile.setValue(value);
					fifthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					fifthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(fifthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 50th Percentile node!", e);
			}

			try {
				Data sixthPercentile = new Data();
				sixthPercentile.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE);
				if (value != null && value.length() > 0) {
					sixthPercentile.setValue(value);
					sixthPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					sixthPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(sixthPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 68th Percentile node!", e);
			}

			try {
				Data ninethPercentile = new Data();
				ninethPercentile.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE);
				if (value != null && value.length() > 0) {
					ninethPercentile.setValue(value);
					ninethPercentile.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					ninethPercentile.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(ninethPercentile);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set 95th Percentile node!", e);
			}

			try {
				Data rmse = new Data();
				rmse.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_RMSE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_RMSE);
				if (value != null && value.length() > 0) {
					rmse.setValue(value);
					rmse.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					rmse.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(rmse);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set RMSE node!", e);
			}

			try {
				Data standardDev = new Data();
				standardDev.setName(LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION);
				if (value != null && value.length() > 0) {
					standardDev.setValue(value);
					standardDev.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					standardDev.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					ehaeErrorChildren.add(standardDev);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Standard deviation node!", e);
			}
			if (ehaeErrorChildren.size() > 0) {
				ehaeError = addToParent(ehaeError, ehaeErrorChildren);
				masterChildren.add(ehaeError);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Estimated Horizontal Accuracy Error node!", e);
		}

		/* Ground Truth */
		/*
		 * try { Data groundTruthError = new Data();
		 * groundTruthError.setName(LocationAccuracyConstants.GROUND_TRUTH);
		 * ArrayList<Data> groundTruthErrorChildren = new ArrayList<Data>(); try {
		 * Data altitude = new Data();
		 * altitude.setName(LocationAccuracyConstants.GROUND_TRUTH_ALTITUDE); String
		 * value = LocationAccuracyPreferencesManager.loadStringPreference(this,
		 * LocationAccuracyConstants.GROUND_TRUTH_ALTITUDE); if (value != null &&
		 * value.length() > 0) { altitude.setValue(value);
		 * altitude.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
		 * groundTruthErrorChildren.add(altitude); } } catch (Exception e) {
		 * Logger.ERROR(TAG, "Could not set Altitude node!", e); }
		 * 
		 * try { Data latitude = new Data();
		 * latitude.setName(LocationAccuracyConstants.GROUND_TRUTH_LATITUDE); String
		 * value = LocationAccuracyPreferencesManager.loadStringPreference(this,
		 * LocationAccuracyConstants.GROUND_TRUTH_LATITUDE); if (value != null &&
		 * value.length() > 0) { latitude.setValue(value);
		 * latitude.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
		 * groundTruthErrorChildren.add(latitude); } } catch (Exception e) {
		 * Logger.ERROR(TAG, "Could not set Latitude node!", e); }
		 * 
		 * try { Data longtitude = new Data();
		 * longtitude.setName(LocationAccuracyConstants.GROUND_TRUTH_LONGITUDE);
		 * String value =
		 * LocationAccuracyPreferencesManager.loadStringPreference(this,
		 * LocationAccuracyConstants.GROUND_TRUTH_LONGITUDE); if (value != null &&
		 * value.length() > 0) { longtitude.setValue(value);
		 * longtitude.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
		 * groundTruthErrorChildren.add(longtitude); } } catch (Exception e) {
		 * Logger.ERROR(TAG, "Could not set Longtitude node!", e); }
		 * groundTruthError = addToParent(groundTruthError,
		 * groundTruthErrorChildren); masterChildren.add(groundTruthError); } catch
		 * (Exception e) { Logger.ERROR(TAG, "Could not set Ground Truth node!", e);
		 * }
		 */

		/* First Fix Time */
		try {
			Data firstFixTime = new Data();
			firstFixTime.setName(LocationAccuracyConstants.FIRST_FIX_TIME);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.FIRST_FIX_TIME);
			if (value != null && value.length() > 0) {
				firstFixTime.setValue(value);
				firstFixTime.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
				masterChildren.add(firstFixTime);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set First Fix Time node!", e);
		}

		/* Sampling */
		try {
			Data sampling = new Data();
			sampling.setName(LocationAccuracyConstants.SAMPLING);
			ArrayList<Data> samplingChildren = new ArrayList<Data>();
			try {
				Data distance = new Data();
				distance.setName(LocationAccuracyConstants.SAMPLING_DISTANCE);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.SAMPLING_DISTANCE);
				if (value != null && value.length() > 0) {
					distance.setValue(value);
					distance.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					distance.setValueMetric(LocationAccuracyConstants.METRIC_METER);
					samplingChildren.add(distance);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Sampling Distance node!", e);
			}
			try {
				Data interval = new Data();
				interval.setName(LocationAccuracyConstants.SAMPLING_INTERVAL);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.SAMPLING_INTERVAL);
				if (value != null && value.length() > 0) {
					interval.setValue(value);
					interval.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
					interval.setValueMetric(LocationAccuracyConstants.METRIC_MILLISECOND);
					samplingChildren.add(interval);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Sampling Interval node!", e);
			}
			if (samplingChildren.size() > 0) {
				sampling = addToParent(sampling, samplingChildren);
				masterChildren.add(sampling);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Sampling node!", e);
		}

		/* Average Time Between Fixes */
		try {
			Data averageTBF = new Data();
			averageTBF.setName(LocationAccuracyConstants.AVERAGE_TIME_BETWEEN_FIXES);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
					LocationAccuracyConstants.AVERAGE_TIME_BETWEEN_FIXES);
			if (value != null && value.length() > 0) {
				averageTBF.setValue(value);
				averageTBF.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
				averageTBF.setValueMetric(LocationAccuracyConstants.METRIC_SECOND);
				masterChildren.add(averageTBF);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Average Time Between Fixes node!", e);
		}

		/* Override Refresh Rate */
		try {
			Data overrideRR = new Data();
			overrideRR.setName(LocationAccuracyConstants.OVERRIDE_REFRESH_RATE);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
					LocationAccuracyConstants.OVERRIDE_REFRESH_RATE);
			if (value != null && value.length() > 0) {
				overrideRR.setValue(getState(value));
				overrideRR.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
				masterChildren.add(overrideRR);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Override Refresh Rate node!", e);
		}

		/* Keep Screen On */
		try {
			Data keepScreenOn = new Data();
			keepScreenOn.setName(LocationAccuracyConstants.KEEP_SCREEN_ON);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this, LocationAccuracyConstants.KEEP_SCREEN_ON);
			if (value != null && value.length() > 0) {
				keepScreenOn.setValue(getState(value));
				keepScreenOn.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
				masterChildren.add(keepScreenOn);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Keep Screen On node!", e);
		}

		/* Time Injected */
		try {
			Data timeInjected = new Data();
			timeInjected.setName(LocationAccuracyConstants.TIME_INJECTED);
			ArrayList<Data> timeInjectedChildren = new ArrayList<Data>();
			try {
				Data timeData = new Data();
				timeData.setName(LocationAccuracyConstants.TIME_INJECTED_TIME_DATA);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.TIME_INJECTED_TIME_DATA);
				if (value != null && value.length() > 0) {
					timeData.setValue(value);
					timeData.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
					timeInjectedChildren.add(timeData);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set Time Data node!", e);
			}
			try {
				Data xtraData = new Data();
				xtraData.setName(LocationAccuracyConstants.TIME_INJECTED_XTRA_DATA);
				String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
						LocationAccuracyConstants.TIME_INJECTED_XTRA_DATA);
				if (value != null && value.length() > 0) {
					xtraData.setValue(value);
					xtraData.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
					timeInjectedChildren.add(xtraData);
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not set XTRA Data node!", e);
			}
			if(timeInjectedChildren.size() > 0){
				timeInjected = addToParent(timeInjected, timeInjectedChildren);
				masterChildren.add(timeInjected);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Time Injected node!", e);
		}

		/* Time Cleared Assist Data */
		try {
			Data timeCAD = new Data();
			timeCAD.setName(LocationAccuracyConstants.TIME_CLEARED_ASSIST_DATA);
			String value = LocationAccuracyPreferencesManager.loadStringPreference(this,
					LocationAccuracyConstants.TIME_CLEARED_ASSIST_DATA);
			if (value != null && value.length() > 0) {
				timeCAD.setValue(value);
				timeCAD.setValueMetric(LocationAccuracyConstants.METRIC_DATE);
				masterChildren.add(timeCAD);
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Time Cleared Assist Data node!", e);
		}

		/* WiFi Status */
		/*
		 * try { Data wifiStatus = new Data();
		 * wifiStatus.setName(LocationAccuracyConstants.WIFI_STATUS); String value =
		 * LocationAccuracyPreferencesManager.loadStringPreference(this,
		 * LocationAccuracyConstants.WIFI_STATUS); if (value != null &&
		 * value.length() > 0) { wifiStatus.setValue(value);
		 * masterChildren.add(wifiStatus); } } catch (Exception e) {
		 * Logger.ERROR(TAG, "Could not set WiFi Status Data node!", e); }
		 */

		parent = addToParent(parent, masterChildren);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.AbstractPlugin#stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		Logger.DEBUG(TAG, "Service is stopped!");
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

	private String getState(String param) {
		String state = null;
		if (param != null) {
			if (param.equals("0")) {
				state = Constants.NODE_VALUE_NO;
			} else if (param.equals("1")) {
				state = Constants.NODE_VALUE_YES;
			}
		}
		return state;
	}
}

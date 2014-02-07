package org.androidanalyzer.plugins.locationaccuracy;

/**
 * This class contains constants used in the Location Accuracy Plugin
 */
public class LocationAccuracyConstants {
	/* Constants for internal Analyzer data structure */
	protected static final String TEST_ID = "Test ID";
	protected static final String APP_VERSION = "Application Version";
	/*
	 * protected static final String DEVICE = "Device"; protected static final
	 * String DEVICE_BRAND = "Brand"; protected static final String DEVICE_PRODUCT
	 * = "Product"; protected static final String ANDROID_VERSION =
	 * "Android Version";
	 */
	protected static final String START_TIME = "Start Time";
	protected static final String END_TIME = "End Time";
	protected static final String TIME_TO_FIRST_FIX = "Time To First Fix";
	protected static final String LOCATION_PROVIDER = "Location Provider";
	protected static final String SAMPLE_SIZE = "Sample Size";
	protected static final String HORIZONTAL = "Horizontal";
	protected static final String HORIZONTAL_ERROR_MIN = "Min";
	protected static final String HORIZONTAL_ERROR_MAX = "Max";
	protected static final String HORIZONTAL_ERROR_MEAN = "Mean";
	protected static final String HORIZONTAL_ERROR_50THPERCENTILE = "50th Percentile";
	protected static final String HORIZONTAL_ERROR_68THPERCENTILE = "68th Percentile";
	protected static final String HORIZONTAL_ERROR_95THPERCENTILE = "95th Percentile";
	protected static final String HORIZONTAL_ERROR_STANDARD_DEVIATION = "Standard Deviation";
	protected static final String VERTICAL = "Vertical";
	protected static final String VERTICAL_ERROR_MIN = "Min";
	protected static final String VERTICAL_ERROR_MAX = "Max";
	protected static final String VERTICAL_ERROR_MEAN = "Mean";
	protected static final String VERTICAL_ERROR_MEAN_ABSOLUTE = "Mean Absolute";
	protected static final String VERTICAL_ERROR_50THPERCENTILE = "50th Percentile";
	protected static final String VERTICAL_ERROR_68THPERCENTILE = "68th Percentile";
	protected static final String VERTICAL_ERROR_95THPERCENTILE = "95th Percentile";
	protected static final String VERTICAL_ERROR_RMSE = "RMSE";
	protected static final String VERTICAL_ERROR_STANDARD_DEVIATION = "Standard Deviation";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR = "Estimated Horizontal Accuracy Error";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_MIN = "Min";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_MAX = "Max";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_MEAN = "Mean";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE = "Mean Absolute";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE = "50th Percentile";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE = "68th Percentile";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE = "95th Percentile";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_RMSE = "RMSE";
	protected static final String ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION = "Standard Deviation";
	/*
	 * protected static final String GROUND_TRUTH = "Ground Truth"; protected
	 * static final String GROUND_TRUTH_LATITUDE = "Latitude"; protected static
	 * final String GROUND_TRUTH_LONGITUDE = "Longitude"; protected static final
	 * String GROUND_TRUTH_ALTITUDE = "Altitude";
	 */
	protected static final String FIRST_FIX_TIME = "First Fix Time";
	protected static final String SAMPLING = "Sampling";
	protected static final String SAMPLING_INTERVAL = "Interval";
	protected static final String SAMPLING_DISTANCE = "Distance";
	protected static final String AVERAGE_TIME_BETWEEN_FIXES = "Average Time Between Fixes";
	protected static final String OVERRIDE_REFRESH_RATE = "Override Refresh Rate";
	protected static final String KEEP_SCREEN_ON = "Keep Screen On";
	protected static final String TIME_INJECTED = "Time Injected";
	protected static final String TIME_INJECTED_TIME_DATA = "Time Data";
	protected static final String TIME_INJECTED_XTRA_DATA = "XTRA Data";
	protected static final String TIME_CLEARED_ASSIST_DATA = "Time Cleared Assist Data";
	/* protected static final String WIFI_STATUS = "WiFi Status"; */
	protected static final String METRIC_METER = "meter";
	protected static final String METRIC_SECOND = "second";
	protected static final String METRIC_MILLISECOND = "millisecond";
	protected static final String METRIC_DATE = "date";
	protected static final String UNKNOWN = "Unknown";
	protected static final String UNAVAILABLE = "n/a";

	/* Constants for raw data from GPS Benchmark application */
	protected static final String RAW_TEST_ID = "testID";
	protected static final String RAW_APP_VERSION = "AppVersion";
	protected static final String RAW_DEVICE_BRAND = "deviceBrand";
	protected static final String RAW_DEVICE_PRODUCT = "deviceProduct";
	protected static final String RAW_ANDROID_VERSION = "AndroidVersion";
	protected static final String RAW_START_TIME = "startTime";
	protected static final String RAW_END_TIME = "endTime";
	protected static final String RAW_TIME_TO_FIRST_FIX = "TimeToFirstFix(s)";
	protected static final String RAW_LOCATION_PROVIDER = "locationProvider";
	protected static final String RAW_SAMPLE_SIZE = "sampleSize";
	protected static final String RAW_HORIZONTAL_ERROR_MIN = "HorizontalErrorMin(m)";
	protected static final String RAW_HORIZONTAL_ERROR_MAX = "HorizontalErrorMax(m)";
	protected static final String RAW_HORIZONTAL_ERROR_MEAN = "HorizontalErrorMean(m)";
	protected static final String RAW_HORIZONTAL_ERROR_50THPERCENTILE = "HorizontalError50thPercentile(m)";
	protected static final String RAW_HORIZONTAL_ERROR_68THPERCENTILE = "HorizontalError68thPercentile(m)";
	protected static final String RAW_HORIZONTAL_ERROR_95THPERCENTILE = "HorizontalError95thPercentile(m)";
	protected static final String RAW_HORIZONTAL_ERROR_STANDARDDEVIATION = "HorizontalErrorStandardDeviation(m)";
	protected static final String RAW_VERTICAL_ERROR_MIN = "VerticalErrorMin(m)";
	protected static final String RAW_VERTICAL_ERROR_MAX = "VerticalErrorMax(m)";
	protected static final String RAW_VERTICAL_ERROR_MEAN = "VerticalErrorMean(m)";
	protected static final String RAW_VERTICAL_ERROR_MEAN_ABSOLUTE = "VerticalErrorMeanAbsolute";
	protected static final String RAW_VERTICAL_ERROR_50THPERCENTILE = "VerticalError50thPercentile(m)";
	protected static final String RAW_VERTICAL_ERROR_68THPERCENTILE = "VerticalError68thPercentile(m)";
	protected static final String RAW_VERTICAL_ERROR_95THPERCENTILE = "VerticalError95thPercentile(m)";
	protected static final String RAW_VERTICAL_ERROR_RMSE = "VerticalErrorRMSE(m)";
	protected static final String RAW_VERTICAL_ERROR_STANDARD_DEVIATION = "VerticalErrorStandardDeviation(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_MIN = "EstimatedHorAccuracyErrorMin(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_MAX = "EstimatedHorAccuracyErrorMax(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_MEAN = "EstimatedHorAccuracyErrorMean(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_MEAN_ABSOLUTE = "EstimatedHorAccuracyErrorMeanAbsolute";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_50THPERCENTILE = "EstimatedHorAccuracyError50thPercentile(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_68THPERCENTILE = "EstimatedHorAccuracyError68thPercentile(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_95THPERCENTILE = "EstimatedHorAccuracyError95thPercentile(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_RMSE = "EstimatedHorAccuracyErrorRMSE(m)";
	protected static final String RAW_ESTIMATED_HOR_ACCURACY_ERROR_STANDARD_DEVIATION = "EstimatedHorAccuracyErrorStandardDeviation(m)";
	/*
	 * protected static final String RAW_GROUND_TRUTH_LATITUDE =
	 * "GroundTruthLatitude"; protected static final String
	 * RAW_GROUND_TRUTH_LONGITUDE = "GroundTruthLongitude"; protected static final
	 * String RAW_GROUND_TRUTH_ALTITUDE = "GroundTruthAltitude";
	 */
	protected static final String RAW_FIRST_FIX_TIME = "FirstFixTime";
	protected static final String RAW_SAMPLING_INTERVAL = "SamplingInterval(ms)";
	protected static final String RAW_SAMPLING_DISTANCE = "SamplingDistance(m)";
	protected static final String RAW_AVERAGE_TIME_BETWEEN_FIXES = "AverageTimeBetweenFixes(s)";
	protected static final String RAW_OVERRIDE_REFRESH_RATE = "OverrideRefreshRate";
	protected static final String RAW_KEEP_SCREEN_ON = "KeepScreenOn";
	protected static final String RAW_TIME_INJECTED_TIME_DATA = "TimeInjectedTimeData";
	protected static final String RAW_TIME_INJECTED_XTRA_DATA = "TimeInjectedXTRAData";
	protected static final String RAW_TIME_CLEARED_ASSIST_DATA = "TimeClearedAssistData";
	/* protected static final String RAW_WIFI_STATUS = "WiFiStatus"; */
}

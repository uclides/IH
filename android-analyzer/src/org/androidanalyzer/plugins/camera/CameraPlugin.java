package org.androidanalyzer.plugins.camera;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;

/**
 * CameraPlugin class is used to gathering main camera functionality and data
 * 
 * New in version 1.1.0: correctly detect number of cameras on devices runing Android OS 2.3 and above
 * 
 */
public class CameraPlugin extends AbstractPlugin {

	private static final String RAW_METADATA = "Raw metadata";
	private static final String CAMERA_LOCATION_UNKNOWN = "Unknown";
	private static final String CAMERA_LOCATION_FRONT = "Front";
	private static final String CAMERA_LOCATION_BACK = "Back";
	private static final String CAMERA_LOCATION = "Location";
	private static final String FIELD_CAMERAINFO_FACING = "facing";
	private static final String TAG = "Analyzer-CameraPlugin";
	private static final String NAME = "Camera Plugin";
	private static final String PLUGIN_VERSION = "1.1.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String PARENT_NODE_NAME = "Camera";
	private static final String PARENT_NODE_NAME_IMAGE = "Image";
	private static final String PARENT_NODE_NAME_VIDEO = "Video";
	private static final String PARENT_NODE_NAME_FORMAT = "Format";
	private static final String PARENT_NODE_NAME_RESOLUTION = "Resolution";
	private static final String PARENT_NODE_NAME_FORMATS = "Formats";
	private static final String PARENT_NODE_NAME_VIDEO_ENCODING = "Video Encoding";
	private static final String PARENT_NODE_NAME_AUDIO_ENCODING = "Audio Encoding";
	private static final String PARENT_NODE_NAME_RESOLUTIONS = "Resolutions";

	private static final String NUMBER_OF_CAMERAS = "Number of cameras";
	private static final String LOCATION = CAMERA_LOCATION;
	private static final String RESOLUTION = "Resolution";
	private static final String FLASH = "Flash";
	private static final String IMAGE_PARENT_NODE = "Image";
	private static final String IMAGE_FOCUS_MODES = "Focus modes";
	private static final String IMAGE_ZOOM = "Zoom";
	private static final String IMAGE_GEOTAGGING = "Geotagging";
	private static final String IMAGE_HAND_JITTER_REDUCTION = "Hand jitter reduction";
	private static final String IMAGE_RED_EYE_REDUCTION = "Red eye reduction";
	private static final String IMAGE_FACE_RECOGNITION = "Face recognition";
	private static final String IMAGE_DYNAMIC_RANGE_OPTIMIZATION = "Dynamic range optimization";
	private static final String IMAGE_OTHER_FEATURES = "Other features";
	private static final String IMAGE_FORMAT = "Format";
	private static final String IMAGE_SUPPORTED_FORMATS = "Supported formats";
	private static final String IMAGE_RESOLUTIONS = "Resolutions";
	private static final String IMAGE_RESOLUTIONS_MAXIMUM_RESOLUTION = "Maximum resolution";
	private static final String IMAGE_RESOLUTIONS_SUPPORTED_RESOLUTIONS = "Supported resolutions";
	private static final String IMAGE_SUPPORTED_COMPRESSION_RATIOS = "Supported compression ratios";
	private static final String VIDEO_PARENT_NODE = "Video";
	private static final String VIDEO_SELF_TIMER = "Self timer";
	private static final String VIDEO_FLASH_MOVIE_LIGHT = "Flash / Movie light";
	private static final String ZOOM = "Zoom";
	private static final String VIDEO_WHITE_BALANCE_BRIGHTNESS = "White Balance / Brightness";
	private static final String VIDEO_COLOR_TONE = "Color tone";
	private static final String VIDEO_SELF_PORTRAIT_VIDEO_MODE = "Self-portrait video mode";
	private static final String VIDEO_LONG_VIDEO_CAPTURE = "Long video capture";
	private static final String VIDEO_OTHER_CAPABILITIES = "Other capabilities";
	private static final String VIDEO_FORMATS = "Formats";
	private static final String VIDEO_FORMATS_MPEG4 = "MPEG-4";
	private static final String VIDEO_FORMATS_3GPP = "3GPP";
	private static final String VIDEO_FORMATS_3GP2 = "3GP2";
	private static final String VIDEO_ENCODING = "Video encoding";
	private static final String VIDEO_ENCODING_MPEG4_PART_2 = "MPEG-4 Part 2";
	private static final String VIDEO_ENCODING_MPEG4_PART_10 = "H.264";
	private static final String VIDEO_ENCODING_H263 = "H.263";
	private static final String AUDIO_ENCODING = "Audio encoding";
	private static final String AUDIO_ENCODING_AAC = "AAC";
	private static final String AUDIO_ENCODING_AMR_NB = "AMR-NB";
	private static final String VIDEO_RESOLUTIONS = "Supported resolutions";
	private static final String VIDEO_MAXIMUM_BITRATE = "Maximum bitrate";
	private static final String VIDEO_MINIMUM_BITRATE = "Minimum bitrate";
	private static final String VIDEO_MAXIMUM_FRAME_RATE = "Maximum framerate";
	private static final String VIDEO_MINIMUM_FRAME_RATE = "Minimum framerate";
	private static final String AUDIO_MIN_FREQ = "Minimum frequency";
	private static final String AUDIO_MAX_FREQ = "Maximum frequency";
	private static final String AUDIO_MIN_CHANNELS = "Minimum channels";
	private static final String AUDIO_MAX_CHANNELS = "Maximum channels";
	private static final String AUDIO_MAXIMUM_BITRATE = "Maximum bitrate";
	private static final String AUDIO_MINIMUM_BITRATE = "Minimum bitrate";
	private static final String VIDEO_FILE_SIZE_LIMIT = "File size limit";

	private static final String BUILD_PROPERTIES_FILE = "/system/build.prop";
	private static final String VIDEO_SUPPORTED_FORMATS = "ro.media.enc.file.format";
	private static final String VIDEO_SUPPORTED_ENCODERS = "ro.media.enc.vid.codec";
	private static final String VIDEO_CODEC_CAPABILITES_PREFIX = "ro.media.enc.vid";
	private static final String VIDEO_ENCODER_H264 = "h264";
	private static final String VIDEO_ENCODER_M4V = "m4v";
	private static final String VIDEO_ENCODER_H263 = "h263";

	private static final String AUDIO_SUPPORTED_ENCODERS = "ro.media.enc.aud.codec";
	private static final String AUDIO_CODEC_CAPABILITES_PREFIX = "ro.media.enc.aud";
	private static final String AUDIO_ENCODER_AAC = "aac";
	private static final String AUDIO_ENCODER_AMR_NB = "amrnb";

	private static final String SUPPORTED = "Supported";

	private static final Object[] infoGetters = {
			/* Data Name */
			PARENT_NODE_NAME,
			/* Data direct Node retriving methods */
			"flatten",/* new in v1.1.0 */
			"getCameraLocation",/* new in v1.1.0 */
			"getCameraResolution",
			new Object[] { FLASH, "getFlashSupported", },
			/* Data children nodes */
			/* Image children attributes */
			new Object[] { IMAGE_PARENT_NODE, "getImageZoomSupported", "getImageFocusModes", "getImageRedEyeReduction",
					new Object[] { IMAGE_FORMAT, "getFormatSupportedFormats" }, "getResolutionSupported" },
			/* Video children attributes */
			new Object[] {
					VIDEO_PARENT_NODE,
					"getVideoFlashLightSupported",
					"getVideoSupportedWhiteBalance",
					"getVideoSupportedColorEffects",
					new Object[] { VIDEO_FORMATS, "getVideoFormatMP4Supported", "getVideoFormat3GPPSupported",
							"getVideoFormat3GP2Supported" }, "getVideoEncodersInfo", "getAudioEncodersInfo" } };

	private Camera cachedCamera = null;
	private Camera.Parameters[] camParams = null;
	private boolean readSysPropsFromFile = false;
	private Properties buildProps = null;
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	private static final String DESCRIPTION = "Collects data on available cameras and their capabilities";
	private static final String CAMERA_INFO_CLASSNAME = "android.hardware.Camera$CameraInfo";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.camera.PluginCommunicator# getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator#getTimeout ()
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
		// TODO Auto-generated method stub
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
	 * @see org.androidanalyzer.plugins.camera. PluginCommunicatorAbstract
	 * #getPluginClassName()
	 */
	@Override
	protected String getPluginClassName() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator# stopDataCollection()
	 */
	@Override
	protected void stopDataCollection() {
		this.stopSelf();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.camera.PluginCommunicator# getData()
	 */
	@Override
	protected Data getData() {
		ArrayList<Data> children = new ArrayList<Data>(4);
		Data parent = new Data();
		try {
			parent.setName(PARENT_NODE_NAME);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Camera parent node!", e);
			status = "Could not set Camera parent node!";
			return null;
		}
		
		int numOfCameras = getCameraCount();
		if (numOfCameras == -1) {
			cachedCamera = Camera.open();
			if (cachedCamera != null) {
				/* Get this properly when api is available */
				numOfCameras = 1;
				camParams = new Camera.Parameters[numOfCameras];
				for (int i = 0; i < numOfCameras; i++) {
					Data camera = null;
					camera = getCameraInfo(i);
					if (camera != null) {
						children.add(camera);
					}
				}
				if (children != null && children.size() > 0) {
					addToParent(parent, children);
				} else {
					try {
						parent.setStatus(Constants.NODE_STATUS_FAILED);
						parent.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
					} catch (Exception e) {
						Logger.ERROR(TAG, "Could not set values for parent Node!", e);
						status = "Could not set value for Camera parent node!";
					}
				}
			} else {
				try {
					parent.setStatus(Constants.NODE_STATUS_FAILED);
					parent.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set values for parent Node!", e);
					status = "Could not set value for Camera parent node!";
				}

			}

			/* Clear cached Camera object */
			if (cachedCamera != null)
				cachedCamera.release();
			cachedCamera = null;
			camParams = null;			
		} else {//we're running device with Android OS 2.3 or higher
			camParams = new Camera.Parameters[numOfCameras];
			for (int i = 0; i < numOfCameras; i++) {
				cachedCamera = openCamera(i);
				if (cachedCamera != null) {
					Data camera = null;
					camera = getCameraInfo(i);
					if (camera != null) {
						children.add(camera);
					}
					cachedCamera.release();
					cachedCamera = null;
				} else {
					try {
						parent.setStatus(Constants.NODE_STATUS_FAILED);
						parent.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
					} catch (Exception e) {
						Logger.ERROR(TAG, "Could not set values for Camera-"+i+" Node!", e);
						status = "Could not set value for Camera-"+i+" parent node!";
					}
				}
			}
			if (children != null && children.size() > 0) {
				addToParent(parent, children);
			} else {
				try {
					parent.setStatus(Constants.NODE_STATUS_FAILED);
					parent.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set values for parent Node!", e);
					status = "Could not set value for Camera parent node!";
				}
			}
			camParams = null;			
		}

		return parent;
	}
	
	/*new in v1.1.0*/
	private Camera openCamera(int cameraId) {
		Camera camera = null;
		try {
			Method open = Camera.class.getDeclaredMethod("open", new Class[] {int.class});
			if (open != null) {
				Object result = open.invoke(null, new Object[] {cameraId});
				if (result != null) {
					return (Camera)result;
				}
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Error opening camera with id: "+cameraId);
		}
		return camera;
	}
	
	/*new in v1.1.0*/
	private int getCameraCount() {
		int count = -1;//If method returns -1 than API level is < 9 and method for count of camera is not available;
		if (getAPIVersion() > 8) {
			try {
				Method countMethod = Camera.class.getDeclaredMethod("getNumberOfCameras", null);
				if (countMethod != null) {
					Object result = countMethod.invoke(null, new Object[] {});
					if (result != null) {
						try {
							count = (Integer)result;
							Logger.DEBUG(TAG, "Retrieved number of cameras: "+count);
						} catch (NumberFormatException nfe) {
							Logger.ERROR(TAG, "Error parsing int from method result");
						}
					}
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Cannot retrieve number of cameras on device");
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * @param cameraNumber
	 * @return
	 */
	private Data getCameraInfo(int cameraNumber) {
		if (infoGetters != null && infoGetters.length > 1) {
			return getInfoThroughGetters(infoGetters, cameraNumber);
		}
		return null;
	}

	/**
	 * @param infoGetters
	 * @param cameraNumber
	 * @return
	 */
	private Data getInfoThroughGetters(Object[] infoGetters, int cameraNumber) {
		Data node = null;
		if (infoGetters != null && infoGetters.length > 1) {
			node = new Data();
			if (infoGetters[0] instanceof String && infoGetters[0].equals(PARENT_NODE_NAME)) {
				try {
					node.setName(PARENT_NODE_NAME + "-" + cameraNumber);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set Camera node name !");
					return null;
				}
			} else if (infoGetters[0] instanceof String && ((String) infoGetters[0]).length() > 0) {
				try {
					node.setName((String) infoGetters[0]);
				} catch (Exception e) {
					Logger.ERROR(TAG, "Could not set node name :" + infoGetters[0]);
					return null;
				}
			}

			for (int i = 1; i < infoGetters.length; i++) {
				Object obj = infoGetters[i];
				Data tempData = null;
				if (obj instanceof String) {
					String funcName = (String) obj;
					try {

						Method method = this.getClass().getDeclaredMethod(funcName, int.class);
						Logger.DEBUG(TAG, "Found Method :" + funcName);
						if (method != null)
							try {
								Object result = method.invoke(this, cameraNumber);
								if (result instanceof Data) {
									tempData = (Data) result;
								}
							} catch (Exception e) {
								Logger.ERROR(TAG, "Error Invoking method : " + method);
								Logger.ERROR(TAG, "ERROR msg : ", e);
							}
					} catch (SecurityException e) {
						Logger.ERROR(TAG, "Could not execute method !");
					} catch (NoSuchMethodException e) {
						Logger.ERROR(TAG, "No such method : " + funcName);
					}
				} else if (obj instanceof Object[]) {
					Object[] subInfoGetters = (Object[]) obj;
					if (subInfoGetters.length > 1) {
						tempData = getInfoThroughGetters(subInfoGetters, cameraNumber);
					}
				} else {
					Logger.ERROR(TAG, "Incorect object in getInfoThroughGetters : " + obj);
				}
				if (tempData != null) {
					try {
						node.setValue(tempData);
					} catch (Exception e) {
						Logger.ERROR(TAG, "Could not set children node to parent node !");
					}
				} else {
					node = dataFailed(node.getName(), Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
					Logger.DEBUG(TAG, "Null tempNode in getInfoThroughGetters for obj: " + obj);
				}
			}
		}
		return node;
	}

	/**
	 * @param cameraNumber
	 * @return
	 */
	private Parameters getCameraParams(int cameraNumber) {
		Camera.Parameters params = camParams[cameraNumber];
		if (params == null) {
			params = cachedCamera.getParameters();
			camParams[cameraNumber] = params;
		}
		return params;
	}

	/** ***************************************************/
	/** Getter methods for camera parameters */
	/** ***************************************************/

	private Data getVideoEncodersInfo(int cameraNumber) {
		String formats = getSystemProperty(VIDEO_SUPPORTED_ENCODERS, false);
		String formatsFromFile = getSystemProperty(VIDEO_SUPPORTED_ENCODERS, true);
		Logger.DEBUG(TAG, "formats : " + formats);
		Logger.DEBUG(TAG, "formatsFromFile : " + formatsFromFile);

		StringTokenizer token = null;
		if (formats != null && formats.length() > 0 && formatsFromFile != null && formatsFromFile.length() > 0) {
			if (formats.length() >= formatsFromFile.length()) {
				token = new StringTokenizer(formats, ",");
			} else {
				token = new StringTokenizer(formatsFromFile, ",");
				readSysPropsFromFile = true;
			}
		} else if (formats != null && formats.length() > 0) {
			token = new StringTokenizer(formats, ",");
		} else if (formatsFromFile != null && formatsFromFile.length() > 0) {
			token = new StringTokenizer(formatsFromFile, ",");
			readSysPropsFromFile = true;
		} else {
			return null;
		}

		Data parent = new Data();
		try {
			parent.setName(VIDEO_ENCODING);
			while (token.hasMoreTokens()) {
				String format = token.nextToken();
				String name = null;
				if (format.equals(VIDEO_ENCODER_H263)) {
					name = VIDEO_ENCODING_H263;
				} else if (format.equals(VIDEO_ENCODER_H264)) {
					name = VIDEO_ENCODING_MPEG4_PART_10;
				} else if (format.equals(VIDEO_ENCODER_M4V)) {
					name = VIDEO_ENCODING_MPEG4_PART_2;
				}
				Data formData = null;
				if (name != null) {
					formData = new Data();
					formData.setName(name);
				}
				Data resolutions = getResolutions(cameraNumber, format);
				if (resolutions != null)
					formData.setValue(resolutions);
				Data minBitrate = getMinBitrate(cameraNumber, format);
				if (minBitrate != null)
					formData.setValue(minBitrate);
				Data maxBitrate = getMaxBitrate(cameraNumber, format);
				if (maxBitrate != null)
					formData.setValue(maxBitrate);
				Data minFramerate = getMinFramerate(cameraNumber, format);
				if (minFramerate != null)
					formData.setValue(minFramerate);
				Data maxFramerate = getMaxFramerate(cameraNumber, format);
				if (maxFramerate != null)
					formData.setValue(maxFramerate);

				if (formData != null)
					parent.setValue(formData);
			}
		} catch (Exception e) {
			parent = null;
			Logger.ERROR(TAG, "Could not create Video Encoding node!", e);
		}
		return parent;
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getEncFormatCapability(int cameraNumber, String format, String property, String name, String metric,
			boolean maxValue) {
		String framerates = getSystemProperty(property, readSysPropsFromFile);
		if (framerates != null) {
			StringTokenizer token = new StringTokenizer(framerates, ",");
			if (maxValue) {
				token.nextToken();
			}
			String minFramerate = token.nextToken();
			if (minFramerate != null && minFramerate.length() > 0) {
				Data data = new Data();
				try {
					data.setName(name);
					data.setValue(minFramerate);
					data.setValueType(Constants.NODE_VALUE_TYPE_INT);
					data.setValueMetric(metric);
					return data;
				} catch (Exception e) {
					Logger.DEBUG(TAG, "Could not create " + name + " node for format : " + format, e);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getMinFramerate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".fps",
				VIDEO_MINIMUM_FRAME_RATE, "fps", false);
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getMaxFramerate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".fps",
				VIDEO_MAXIMUM_FRAME_RATE, "fps", true);
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getMinBitrate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".bps",
				VIDEO_MINIMUM_BITRATE, "bps", false);
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getMaxBitrate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".bps",
				VIDEO_MAXIMUM_BITRATE, "bps", true);
	}

	/**
	 * @param format
	 * @return
	 */
	private Data getResolutions(int cameraNumber, String format) {
		Data res = new Data();
		try {
			res.setName(VIDEO_RESOLUTIONS);

			String ws = getSystemProperty(VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".width", readSysPropsFromFile);
			String hs = getSystemProperty(VIDEO_CODEC_CAPABILITES_PREFIX + "." + format + ".height", readSysPropsFromFile);
			Logger.DEBUG(TAG, "Supported resolutions widths/heights = " + ws + "/" + hs);
			StringTokenizer token = new StringTokenizer(ws, ",");
			if (token.countTokens() == 2) {
				String minW = token.nextToken();
				String maxW = token.nextToken();
				token = new StringTokenizer(hs, ",");
				String minH = token.nextToken();
				String maxH = token.nextToken();
				if (minW != null && maxW != null && minH != null && maxH != null) {
					Camera.Parameters params = getCameraParams(cameraNumber);

					List<Camera.Size> sizes = null;
					if (params != null) {
						if (getAPIVersion() >= 5) {
							String methodName = "getSupportedPreviewSizes";
							try {
								Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
								if (method != null) {
									sizes = (List<Size>) method.invoke(params, null);
								}
							} catch (SecurityException e) {
								Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
							} catch (NoSuchMethodException e) {
								Logger.DEBUG(TAG, "No such method : " + methodName);
							} catch (IllegalArgumentException e) {
							} catch (IllegalAccessException e) {
							} catch (InvocationTargetException e) {
							}
						}
					}
					StringBuffer resolutions = null;
					if (sizes != null && sizes.size() > 0) {
						boolean foundMax = false;
						for (Size size : sizes) {
							if (foundMax == false && maxW.equals(String.valueOf(size.width)) && maxH.equals(String.valueOf(size.height))) {
								foundMax = true;
							}
							if (foundMax == true) {
								if (resolutions == null) {
									resolutions = new StringBuffer();
									resolutions.append(size.width + "x" + size.height);
								} else {
									resolutions.append("," + size.width + "x" + size.height);
								}
							}
							if (minW.equals(String.valueOf(size.width)) && minH.equals(String.valueOf(size.height))) {
								break;
							}
						}
					} else {
						resolutions = new StringBuffer();
						resolutions.append(minW + "x" + minH + "," + maxW + "x" + maxH);
					}
					if (resolutions != null) {
						res.setValue(resolutions.toString());
						return res;
					}
				}
			}
			if (res.getValue() == null) {
				res.setStatus(Constants.NODE_STATUS_FAILED);
				res.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			}
		} catch (Exception e) {
			Logger.DEBUG(TAG, "Could not create supported resolutions node for format : " + format, e);
		}
		return null;
	}

	private Data getAudioEncodersInfo(int cameraNumber) {
		String formats = getSystemProperty(AUDIO_SUPPORTED_ENCODERS, false);
		String formatsFromFile = getSystemProperty(AUDIO_SUPPORTED_ENCODERS, true);
		Logger.DEBUG(TAG, "audio formats : " + formats);
		Logger.DEBUG(TAG, "audio formatsFromFile : " + formatsFromFile);

		StringTokenizer token = null;
		if (formats != null && formats.length() > 0 && formatsFromFile != null && formatsFromFile.length() > 0) {
			if (formats.length() >= formatsFromFile.length()) {
				token = new StringTokenizer(formats, ",");
			} else {
				token = new StringTokenizer(formatsFromFile, ",");
				readSysPropsFromFile = true;
			}
		} else if (formats != null && formats.length() > 0) {
			token = new StringTokenizer(formats, ",");
		} else if (formatsFromFile != null && formatsFromFile.length() > 0) {
			token = new StringTokenizer(formatsFromFile, ",");
			readSysPropsFromFile = true;
		} else {
			return null;
		}

		Data parent = new Data();
		try {
			parent.setName(AUDIO_ENCODING);
			while (token.hasMoreTokens()) {
				String format = token.nextToken();
				String name = null;
				if (format.equals(AUDIO_ENCODER_AMR_NB)) {
					name = AUDIO_ENCODING_AMR_NB;
				} else if (format.equals(AUDIO_ENCODER_AAC)) {
					name = AUDIO_ENCODER_AAC;
				}
				Data formData = null;
				if (name != null) {
					formData = new Data();
					formData.setName(name);
				}
				Data minBitrate = getMinAudioBitrate(cameraNumber, format);
				if (minBitrate != null)
					formData.setValue(minBitrate);
				Data maxBitrate = getMaxAudioBitrate(cameraNumber, format);
				if (maxBitrate != null)
					formData.setValue(maxBitrate);
				Data minFrequency = getMinAudioFrequency(cameraNumber, format);
				if (minFrequency != null)
					formData.setValue(minFrequency);
				Data maxFrequency = getMaxAudioFrequency(cameraNumber, format);
				if (maxFrequency != null)
					formData.setValue(maxFrequency);
				Data minChannels = getMinAudioChannels(cameraNumber, format);
				if (minChannels != null)
					formData.setValue(minChannels);
				Data maxChannels = getMaxAudioChannels(cameraNumber, format);
				if (maxChannels != null)
					formData.setValue(maxChannels);

				if (formData != null)
					parent.setValue(formData);
			}
		} catch (Exception e) {
			parent = null;
			Logger.DEBUG(TAG, "Could not create Audio Encoding Node !", e);
		}
		return parent;

	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMinAudioChannels(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".ch",
				AUDIO_MIN_CHANNELS, "channel", false);
	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMaxAudioChannels(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".ch",
				AUDIO_MAX_CHANNELS, "channel", true);
	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMaxAudioFrequency(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".hz", AUDIO_MAX_FREQ,
				"Hz", true);
	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMinAudioFrequency(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".hz", AUDIO_MIN_FREQ,
				"Hz", false);
	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMaxAudioBitrate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".bps",
				AUDIO_MAXIMUM_BITRATE, "bps", true);
	}

	/**
	 * @param cameraNumber
	 * @param format
	 * @return
	 */
	private Data getMinAudioBitrate(int cameraNumber, String format) {
		return getEncFormatCapability(cameraNumber, format, AUDIO_CODEC_CAPABILITES_PREFIX + "." + format + ".bps",
				AUDIO_MINIMUM_BITRATE, "bps", false);
	}

	private Data getVideoFormatMP4Supported(int cameraNumber) {
		return getVideoFormatSupported(VIDEO_FORMATS_MPEG4);
	}

	private Data getVideoFormat3GPPSupported(int cameraNumber) {
		return getVideoFormatSupported(VIDEO_FORMATS_3GPP);
	}

	private Data getVideoFormat3GP2Supported(int cameraNumber) {
		return getVideoFormatSupported(VIDEO_FORMATS_3GP2);
	}

	/**
	 * @param videoFormats3gpp
	 * @return
	 */
	private Data getVideoFormatSupported(String videoFormat) {
		String formats = getSystemProperty(VIDEO_SUPPORTED_FORMATS, readSysPropsFromFile);

		Data data = new Data();
		try {
			data.setName(videoFormat);
			if (formats != null && formats.length() > 0) {
				boolean supported = false;
				if (videoFormat.equals(VIDEO_FORMATS_3GPP)) {
					if (formats.contains("3gp")) {
						supported = true;
					}
				} else if (videoFormat.equals(VIDEO_FORMATS_MPEG4)) {
					if (formats.contains("mp4")) {
						supported = true;
					}
				} else if (videoFormat.equals(VIDEO_FORMATS_3GP2)) {
					if (formats.contains("3g2")) {
						supported = true;
					}
				}
				if (supported) {
					data.setValue(Constants.NODE_VALUE_YES);
				} else {
					data.setValue(Constants.NODE_VALUE_NO);
				}
				data.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);

			} else {
				data.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
				data.setStatus(Constants.NODE_STATUS_FAILED);
			}
			return data;
		} catch (Exception e) {
			Logger.DEBUG(TAG, "Could not create node for Video format : " + videoFormat);
		}
		return null;
	}

	/**
	 * @param videoSupportedFormats
	 * @return
	 */
	private String getSystemProperty(String propKey, boolean fromFile) {
		String value = null;
		if (fromFile) {
			if (buildProps == null) {
				buildProps = new Properties();
				try {
					buildProps.load(new FileInputStream(BUILD_PROPERTIES_FILE));
				} catch (IOException e) {
					Logger.DEBUG(TAG, "Could not read build.prop file !", e);
				}
			}
			value = buildProps.getProperty(propKey);
		} else {
			try {
				Class sysPropClass = Class.forName("android.os.SystemProperties");
				Method getMethod = sysPropClass.getMethod("get", String.class);
				value = (String) getMethod.invoke(sysPropClass.newInstance(), propKey);
			} catch (Exception e) {
				Logger.DEBUG(TAG, "Could not get systemProperty : " + propKey, e);
			}
		}
		Logger.DEBUG(TAG, "system prop  " + propKey + " = " + value);
		return value;
	}

	private Data getAvailableMode(int cameraNumber, String nodeName, String methodName, String fieldName) {
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		Boolean hasFeature = null;
		if (getAPIVersion() >= 5) {
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					List<String> modes = (List<String>) method.invoke(params, null);
					Field auto = Camera.Parameters.class.getDeclaredField(fieldName);
					if (modes != null && auto != null) {
						String fieldValue = (String) auto.get(params);
						if (modes.contains(fieldValue))
							hasFeature = Boolean.TRUE;
						else
							hasFeature = Boolean.FALSE;
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchFieldException e) {
				Logger.DEBUG(TAG, "No such field : " + fieldName);
			}
		}
		if (hasFeature != null) {
			data = new Data();
			try {
				data.setName(nodeName);
				if (hasFeature) {
					data.setValue(Constants.NODE_VALUE_YES);
				} else {
					data.setValue(Constants.NODE_VALUE_NO);
				}
				data.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node name !");
				data = null;
			}
		}
		if (data == null) {
			data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
		}
		Logger.DEBUG(TAG, "Node " + nodeName + " value : " + data.getValue());
		return data;
	}

	private Data getAllSupportedModes(int cameraNumber, String nodeName, String methodName) {
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		StringBuffer modeList = null;
		if (getAPIVersion() >= 5) {
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					Object temp = method.invoke(params, null);
					if (temp != null) {
						List<String> modes = (List<String>) temp;
						for (String mode : modes) {
							if (modeList == null) {
								modeList = new StringBuffer();
								modeList.append(mode);
							} else {
								modeList.append("," + mode);
							}
						}
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		if (modeList != null) {
			data = new Data();
			try {
				data.setName(nodeName);
				data.setValue(modeList.toString());
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node : " + nodeName);
				data = null;
			}
		}
		if (data == null ) {
			if ( getAPIVersion() >= 5 )
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			else
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
		}
		Logger.DEBUG(TAG, "Node " + nodeName + " value : " + data.getValue());
		return data;
	}

	private Data getFeatureAvailable(int cameraNumber, String nodeName, String featureCheckupFunc) {
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		Boolean hasFeature = null;
		Method method;
		try {
			if (featureCheckupFunc != null) {
				method = this.getClass().getDeclaredMethod(featureCheckupFunc, int.class);
				if (method != null) {
					Object temp = method.invoke(this, cameraNumber);
					if (temp != null) {
						hasFeature = (Boolean) temp;
					}
				}
			}
		} catch (SecurityException e) {
			Logger.DEBUG(TAG, "Security check failed obtaining method : " + featureCheckupFunc);
		} catch (NoSuchMethodException e) {
			Logger.DEBUG(TAG, "No such method : " + featureCheckupFunc);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}

		if (hasFeature != null) {
			data = new Data();
			try {
				data.setName(nodeName);
				if (hasFeature) {
					data.setValue(Constants.NODE_VALUE_YES);
				} else {
					data.setValue(Constants.NODE_VALUE_NO);
				}
				data.setValueType(Constants.NODE_VALUE_TYPE_BOOLEAN);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node name !");
				data = null;
			}
		}
		if (data == null ) {
			if ( getAPIVersion() >= 3)
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			else
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
		}
		Logger.DEBUG(TAG, "Node " + nodeName + " value : " + data.getValue());
		return data;
	}

	private Data getFlashSupported(int cameraNumber) {
		String nodeName = SUPPORTED;
		String methodName = "getFlashSupportedInfo";
		return getFeatureAvailable(cameraNumber, nodeName, methodName);
	}

	private Boolean getFlashSupportedInfo(int cameraNumber) {

		String methodName = "getSupportedFlashModes";
		Boolean hasFeature = null;
		if (getAPIVersion() >= 5) {
			Camera.Parameters params = getCameraParams(cameraNumber);
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					Object temp = method.invoke(params, null);
					List<String> modes = null;
					if (temp != null)
						modes = (List<String>) temp;
					if (modes != null && modes.size() > 0)
						hasFeature = Boolean.TRUE;
					else
						hasFeature = Boolean.FALSE;
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
				Logger.DEBUG(TAG, "IllegalArgumentException :" + e.getMessage());
			} catch (IllegalAccessException e) {
				Logger.DEBUG(TAG, "IllegalAccessException :" + e.getMessage());
			} catch (InvocationTargetException e) {
				Logger.DEBUG(TAG, "InvocationTargetException :" + e.getMessage());
			}
		}
		return hasFeature;
	}

	private Data getImageZoomSupported(int cameraNumber) {
		String nodeName = IMAGE_ZOOM;
		String methodName = "getImageZoomSupportedInfo";
		return getFeatureAvailable(cameraNumber, nodeName, methodName);
	}

	private Boolean getImageZoomSupportedInfo(int cameraNumber) {
		String methodName = "isZoomSupported";

		Camera.Parameters params = getCameraParams(cameraNumber);
		Boolean hasFeature = null;
		if (getAPIVersion() >= 5) {
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					Boolean zoomSupported = (Boolean) method.invoke(params, null);
					if (zoomSupported != null) {
						if (zoomSupported)
							hasFeature = Boolean.TRUE;
						else
							hasFeature = Boolean.FALSE;
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
				Logger.DEBUG(TAG, "IllegalArgumentException :" + e.getMessage());
			} catch (IllegalAccessException e) {
				Logger.DEBUG(TAG, "IllegalAccessException :" + e.getMessage());
			} catch (InvocationTargetException e) {
				Logger.DEBUG(TAG, "InvocationTargetException :" + e.getMessage());
			}
		}
		return hasFeature;
	}

	private Data getVideoSupportedWhiteBalance(int cameraNumber) {
		return getAllSupportedModes(cameraNumber, VIDEO_WHITE_BALANCE_BRIGHTNESS, "getSupportedWhiteBalance");
	}

	private Data getVideoSupportedColorEffects(int cameraNumber) {
		return getAllSupportedModes(cameraNumber, VIDEO_COLOR_TONE, "getSupportedColorEffects");
	}

	private Data getResolutionSupported(int cameraNumber) {
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		StringBuffer sizesStr = null;
		if (getAPIVersion() >= 5) {
			String methodName = "getSupportedPictureSizes";
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {

					List<Camera.Size> sizes = (List<Size>) method.invoke(params, null);

					if (sizes != null && sizes.size() > 0) {
						for (Size size : sizes) {
							if (sizesStr == null) {
								sizesStr = new StringBuffer();
								sizesStr.append(String.valueOf(size.width) + "x" + size.height);
							} else {
								sizesStr.append("," + size.width + "x" + size.height);
							}
						}
					} else {
						Logger.DEBUG(TAG, "No sizes available!");
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}

		if (sizesStr != null) {
			data = new Data();
			try {
				data.setName(IMAGE_RESOLUTIONS_SUPPORTED_RESOLUTIONS);
				data.setValue(sizesStr.toString());
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node name !");
				data = null;
			}
		}
		if (data == null) {
			if ( getAPIVersion() >= 5 )
				data = dataFailed(IMAGE_RESOLUTIONS_SUPPORTED_RESOLUTIONS, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			else
				data = dataFailed(IMAGE_RESOLUTIONS_SUPPORTED_RESOLUTIONS, Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
		}
		Logger.DEBUG(TAG, "Camera Supported resolutions : " + data.getValue());
		return data;
	}

	// private Data getResolutionMaximumSupported(int
	// cameraNumber) {
	// Camera.Parameters params =
	// getCameraParams(cameraNumber);
	// Data data = null;
	// Size size = null;
	// if (getAPIversion() >= 5) {
	// String methodName = "getSupportedPictureSizes";
	// try {
	// Method method =
	// Camera.Parameters.class.getDeclaredMethod(methodName);
	// if (method != null) {
	// List<Camera.Size> sizes = (List<Size>)
	// method.invoke(params, null);
	// if (sizes != null && sizes.size() > 0) {
	// size = sizes.get(0);
	// } else {
	// Logger.DEBUG(TAG, "No sizes available!");
	// }
	// }
	// } catch (SecurityException e) {
	// Logger.DEBUG(TAG,
	// "Security check failed obtaining method : " +
	// methodName);
	// } catch (NoSuchMethodException e) {
	// Logger.DEBUG(TAG, "No such method : " + methodName);
	// } catch (IllegalArgumentException e) {
	// } catch (IllegalAccessException e) {
	// } catch (InvocationTargetException e) {
	// }
	// }
	// if ((getAPIversion() > 0 && getAPIversion() < 5) ||
	// size == null) {
	// size = params.getPictureSize();
	// }
	// if (size != null) {
	// data = new Data();
	// try {
	// data.setName(IMAGE_RESOLUTIONS_MAXIMUM_RESOLUTION);
	// String value = String.valueOf(size.width) + "x" +
	// size.height;
	// data.setValue(value);
	// } catch (Exception e) {
	// Logger.ERROR(TAG, "Could not create node name !");
	// data = null;
	// }
	// }
	// if (data == null) {
	// data =
	// dataFailed(IMAGE_RESOLUTIONS_MAXIMUM_RESOLUTION);
	// }
	// if (data != null) {
	// Logger.DEBUG(TAG, "Camera Max resolution : " +
	// data.getValue());
	// }
	// return data;
	// }

	private Data getFormatSupportedFormats(int cameraNumber) {
		String nodeName = IMAGE_SUPPORTED_FORMATS;
		String methodName = "getSupportedPictureFormats";
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		StringBuffer formatsSupported = null;
		if (getAPIVersion() >= 5) {
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					List<Integer> formats = (List<Integer>) method.invoke(params, null);
					if (formats != null) {
						for (Integer format : formats) {
							String strFormat = getStrPictureFormat(format);
							if (strFormat != null) {
								if (formatsSupported == null) {
									formatsSupported = new StringBuffer();
									formatsSupported.append(strFormat);
								} else {
									formatsSupported.append("," + strFormat);
								}
							}
						}
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		if (formatsSupported != null && formatsSupported.length() > 0) {
			data = new Data();
			try {
				data.setName(nodeName);
				data.setValue(formatsSupported.toString());
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node : " + nodeName);
				data = null;
			}
		}
		if (data == null ) {
			if ( getAPIVersion() >= 5 )
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			else
				data = dataFailed(nodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_API);
		}
		Logger.DEBUG(TAG, "Node " + nodeName + " value : " + data.getValue());
		return data;
	}

	private Data getVideoFlashLightSupported(int cameraNumber) {
		return getAvailableMode(cameraNumber, VIDEO_FLASH_MOVIE_LIGHT, "getSupportedFlashModes", "FLASH_MODE_TORCH");
	}

	private Data getImageRedEyeReduction(int cameraNumber) {
		return getAvailableMode(cameraNumber, IMAGE_RED_EYE_REDUCTION, "getSupportedFlashModes", "FLASH_MODE_RED_EYE");
	}

	private Data getImageFocusModes(int cameraNumber) {
		return getAllSupportedModes(cameraNumber, IMAGE_FOCUS_MODES, "getSupportedFocusModes");
	}
	
	/**
	 * @since 1.1.0
	 */
	private Data flatten(int cameraNumber) {
		Data result = null;
		try {
			Camera.Parameters params = getCameraParams(cameraNumber);
			if (params != null) {
				String representation = params.flatten();
				if (representation != null) {
					result = new Data();
					result.setName(RAW_METADATA);
					result.setValue(representation);
				}
			}
		} catch (Exception e) {
			Logger.ERROR(TAG, "Error reading camera params for "+cameraNumber);
		}
		if (result == null) {
			result = dataFailed(RAW_METADATA, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
		}
		return result;
	}
	
	/**
	 * 
	 * @param cameraNumber
	 * @return
	 * @since 1.1.0
	 */
	private Data getCameraLocation(int cameraNumber) {
		Data data = null;
		if (getAPIVersion() > 8) {
			try {
				Class cameraInfo = Class.forName(CAMERA_INFO_CLASSNAME);
				if (cameraInfo != null) {
					Object instance = cameraInfo.newInstance();
					if (instance != null) {
						Method getCameraInfo = Camera.class.getDeclaredMethod("getCameraInfo", new Class[] {int.class, cameraInfo});
						if (getCameraInfo != null) {
							getCameraInfo.invoke(null, new Object[] {cameraNumber, instance});
							try {
								Field facing = cameraInfo.getDeclaredField(FIELD_CAMERAINFO_FACING);
								if (facing != null) {
									int value = facing.getInt(instance);
									data = new Data();
									data.setName(CAMERA_LOCATION);
									data.setValue(value == 0 ? CAMERA_LOCATION_BACK : (value == 1 ? CAMERA_LOCATION_FRONT : CAMERA_LOCATION_UNKNOWN));
								}
							} catch (Exception e) {
								Logger.ERROR(TAG, "Could not retrieve value from props");
							}
						}
					}
				}
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not load Camera.CameraInfo class");
			}
		}
		if (data == null) {
			data = dataFailed(CAMERA_LOCATION, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
		}
		Logger.DEBUG(TAG, "Camera location : " + data.getValue());
		return data;
	}

	private Data getCameraResolution(int cameraNumber) {
		Camera.Parameters params = getCameraParams(cameraNumber);
		Data data = null;
		double size = 0;
		if (getAPIVersion() >= 5) {
			String methodName = "getSupportedPictureSizes";
			try {
				Method method = Camera.Parameters.class.getDeclaredMethod(methodName);
				if (method != null) {
					List<Camera.Size> sizes = (List<Size>) method.invoke(params, null);
					if (sizes != null && sizes.size() > 0) {
						for (Size curSize : sizes) {
							if (size == 0) {
								size = curSize.height * curSize.width;
							} else if (size < (curSize.height * curSize.width)) {
								size = curSize.height * curSize.width;
							}
						}
					} else {
						Logger.DEBUG(TAG, "No sizes available!");
					}
				}
			} catch (SecurityException e) {
				Logger.DEBUG(TAG, "Security check failed obtaining method : " + methodName);
			} catch (NoSuchMethodException e) {
				Logger.DEBUG(TAG, "No such method : " + methodName);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		if ((getAPIVersion() > 0 && getAPIVersion() < 5) || size == 0) {
			Size sizeFromParams = params.getPictureSize();
			if (sizeFromParams != null) {
				size = sizeFromParams.width * sizeFromParams.height;
			}
		}
		if (size > 0) {
			data = new Data();
			try {
				data.setName(PARENT_NODE_NAME_RESOLUTION);
				size = size / 1000000;
				data.setValue(getFormattedDouble(size, "#0.00"));
				data.setValueMetric("MP");
				data.setValueType(Constants.NODE_VALUE_TYPE_DOUBLE);
			} catch (Exception e) {
				Logger.ERROR(TAG, "Could not create node name !");
				data = null;
			}
		}
		if (data == null) {
			data = dataFailed(PARENT_NODE_NAME_RESOLUTION, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
		}
		Logger.DEBUG(TAG, "Camera resolution : " + data.getValue());
		return data;
	}

	private String getFormattedDouble(double size, String format) {
		return new DecimalFormat(format).format(size);
	}

	/**
	 * @param parentNodeNameResolution
	 * @return
	 */
	private Data dataFailed(String parentNodeName) {
		return dataFailed(parentNodeName, Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
	}

	/**
	 * @param parentNodeNameResolution
	 * @return
	 */
	private Data dataFailed(String parentNodeName, String reason) {
		Data data = new Data();
		try {
			data.setName(parentNodeName);
			data.setStatus(Constants.NODE_STATUS_FAILED);
			data.setValue(reason);
			data.setValueType(Constants.NODE_VALUE_TYPE_STRING);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set failed node : " + parentNodeName);
			data = null;
		}
		return data;
	}

	/**
	 * Returns the String reperesentation of a int format value
	 * 
	 * @param integer
	 *          value of the format to be returned as String
	 * @return
	 */
	private String getStrPictureFormat(Integer format) {
		String formatStr = null;
		switch (format) {
		case -3:
			formatStr = "TRANSLUCENT";
			break;
		case -2:
			formatStr = "TRANSPARENT";
			break;
		case -1:
			formatStr = "OPAQUE";
			break;
		case 1:
			formatStr = "RGBA_8888";
			break;
		case 2:
			formatStr = "RGBX_8888";
			break;
		case 3:
			formatStr = "RGB_888";
			break;
		case 4:
			formatStr = "RGB_565";
			break;
		case 6:
			formatStr = "RGBA_5551";
			break;
		case 8:
			formatStr = "A_8";
			break;
		case 9:
			formatStr = "L_8";
			break;
		case 11:
			formatStr = "RGB_332";
			break;
		case 16:
			formatStr = "NV16";
			break;
		case 17:
			formatStr = "NV21";
			break;
		case 20:
			formatStr = "YUY2";
			break;
		case 256:
			formatStr = "JPEG";
			break;
		default:
			break;
		}
		return formatStr;
	}
}

package org.androidanalyzer.plugins.memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.androidanalyzer.Constants;
import org.androidanalyzer.core.Data;
import org.androidanalyzer.core.utils.Logger;
import org.androidanalyzer.plugins.AbstractPlugin;

/**
 * MemoryPlugin class used for gathering device memory connected data
 *
 */
public class MemoryPlugin extends AbstractPlugin {

	private static final String NAME = "Memory Plugin";
	private static final String PLUGIN_VERSION = "1.0.0";
	private static final String PLUGIN_VENDOR = "ProSyst Software GmbH";
	private static final String PARENT_NODE_NAME = "Memory";

	private static final String RAM = "RAM";
	private static final String INTERNAL_STORAGE = "Internal Storage";
	private static final String EXTERNAL_STORAGE_MEDIA_TYPE = "Media type";
	private static final String EXTERNAL_STORAGE_MAXIMUM_SIZE = "Maximum size";

	private static final String MEM_INFO_FILE = "/proc/meminfo";
	private static final String TAG = "Analyzer-MemoryPlugin";
	private static final String EXTERNAL_TYPE_MMC = "microSD";

	private static final String MICRO_SD_ROOT_DEVICE = "/dev/block/mmcblk0";

	private static final String MOUNT_COMMAND = "mount";
	private static final String DF_COMMAND = "df";

	private static final String MEMORY_METRIC = "K";

	private static final CharSequence FLASH_FILE_SYSTEM = "yaffs";
	private static final CharSequence UBI_FILE_SYSTEM = "ubifs";
	private static final String DESCRIPTION = "Collects data on memory capabilities, like ROM, RAM, removable storage, etc";
	private String status = Constants.METADATA_PLUGIN_STATUS_PASSED;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.memory.PluginCommunicator# getPluginName()
	 */
	@Override
	public String getPluginName() {
		return NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.PluginCommunicator# getPluginVersion()
	 */
	@Override
	public String getPluginVersion() {
		return PLUGIN_VERSION;
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
	 * @see org.androidanalyzer.plugins.memory.PluginCommunicator# getData()
	 */
	@Override
	protected Data getData() {
		Data parent = new Data();
		try {
			parent.setName(PARENT_NODE_NAME);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not set Memory parent node!", e);
			status = "Could not set Memory parent node!";
			return null;
		}

		ArrayList<Data> children = new ArrayList<Data>();

		try {
			Data ramD = new Data();
			ramD.setName(RAM);
			String ram = getRam();
			if (ram != null && ram.length() > 0) {
				ramD.setValue(ram);
				ramD.setStatus(Constants.NODE_STATUS_OK);
				ramD.setValueType(Constants.NODE_VALUE_TYPE_INT);
				ramD.setValueMetric(MEMORY_METRIC);
			} else {
				ramD.setStatus(Constants.NODE_STATUS_FAILED);
				ramD.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			}
			children.add(ramD);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not create RAM Node!", e);
			status = "Could not create RAM Node!";
		}

		try {
			Data intStorageD = new Data();
			intStorageD.setName(INTERNAL_STORAGE);
			String intStorage = getIntStorageSize();
			if (intStorage != null && intStorage.length() > 0) {
				intStorageD.setValue(intStorage);
				intStorageD.setStatus(Constants.NODE_STATUS_OK);
				intStorageD.setValueType(Constants.NODE_VALUE_TYPE_INT);
				intStorageD.setValueMetric(MEMORY_METRIC);
			} else {
				intStorageD.setStatus(Constants.NODE_STATUS_FAILED);
				intStorageD.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			}
			children.add(intStorageD);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not create Internal Storage Node!", e);
			status = "Could not create Internal Storage Node!";
		}

		try {
			Data extStorMediaType = new Data();
			extStorMediaType.setName(EXTERNAL_STORAGE_MEDIA_TYPE);
			String intStorage = getExtStorageType();
			if (extStorMediaType != null && intStorage != null && intStorage.length() > 0) {
				extStorMediaType.setValue(intStorage);
				extStorMediaType.setStatus(Constants.NODE_STATUS_OK);
			} else {
				extStorMediaType.setStatus(Constants.NODE_STATUS_FAILED);
				extStorMediaType.setValue(Constants.NODE_STATUS_FAILED_UNAVAILABLE_VALUE);
			}
			children.add(extStorMediaType);
		} catch (Exception e) {
			Logger.ERROR(TAG, "Could not create Ext Storage Type Node!", e);
			status = "Could not create Ext Storage Type Node!";
		}
		// Data extStorMaxSize = new Data();

		addToParent(parent, children);
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.androidanalyzer.plugins.memory. PluginCommunicatorAbstract
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
	 * @see org.androidanalyzer.plugins.AbstractPlugin#getPluginStatus()
	 */
	@Override
	protected String getPluginStatus() {
		return status;
	}

	/**
	 * @return
	 */
	private String getExtStorageType() {
		File mmcCard = new File(MICRO_SD_ROOT_DEVICE);
		if (mmcCard.exists()) {
			return EXTERNAL_TYPE_MMC;
		}
		return null;
	}

	/**
	 * @return
	 */
	private String getIntStorageSize() {
		String cmd = MOUNT_COMMAND;
		Runtime run = Runtime.getRuntime();
		Process pr = null;
		/* Parsing Mount Points */
		try {
			pr = run.exec(cmd);
		} catch (IOException e) {
			Logger.ERROR(TAG, "Could not execute command : " + cmd, e);
			return null;
		}
		try {
			if (pr != null) {
				pr.waitFor();
			}
		} catch (InterruptedException e) {
			Logger.ERROR(TAG, "Could not complete command : " + cmd, e);
			return null;
		}
		BufferedReader buf = null;

		String line = null;
		ArrayList<String> mountPts = new ArrayList<String>(3);
		try {
			buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while ((line = buf.readLine()) != null) {
				if (line.contains(FLASH_FILE_SYSTEM) || line.contains(UBI_FILE_SYSTEM)) {
					StringTokenizer token = new StringTokenizer(line, " ");
					while (token.hasMoreElements()) {
						String mntPoint = token.nextToken();
						if (mntPoint != null && mntPoint.length() > 0 && !mntPoint.startsWith("/dev/block") && mntPoint.startsWith("/")) {
							mountPts.add(mntPoint);
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			Logger.ERROR(TAG, "Could not parse mount points!", e);
		} finally {
			if (buf != null)
				try {
					buf.close();
				} catch (IOException e) {
				}
		}

		/* Parsing Mount Point sizes */
		cmd = DF_COMMAND;
		try {
			pr = run.exec(cmd);
		} catch (IOException e) {
			Logger.ERROR(TAG, "Could not execute command : " + cmd, e);
			return null;
		}
		try {
			if (pr != null) {
				pr.waitFor();
			}
		} catch (InterruptedException e) {
			Logger.ERROR(TAG, "Could not complete command : " + cmd, e);
			return null;
		}
		ArrayList<String> mountPtsSizes = new ArrayList<String>(mountPts.size());
		try {
			buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while ((line = buf.readLine()) != null) {
				for (String mntPnt : mountPts) {
					boolean found = false;
					if (line.contains(mntPnt)) {
						found = true;
						mountPts.remove(mntPnt);
						StringTokenizer token = new StringTokenizer(line, " ");
						token.nextToken();

						while (token.hasMoreElements()) {
							String size = token.nextToken();

							if (size != null && size.length() > 0) {
								if (size.endsWith("K")) {
									size = size.substring(0, size.length() - 1);
								}
								mountPtsSizes.add(size);
								break;
							}
						}
						if (found)
							break;
					}
				}
				if (mountPts.size() == 0) {
					break;
				}
			}
		} catch (IOException e) {
			Logger.ERROR(TAG, "Could not parse mount point sizes!", e);
		} finally {
			if (buf != null)
				try {
					buf.close();
				} catch (IOException e) {
				}
		}

		/* Summing mount point sizes */
		if (mountPtsSizes != null && mountPtsSizes.size() > 0) {
			Integer fullSize = new Integer(0);
			for (String size : mountPtsSizes) {
				fullSize += Integer.parseInt(size);
			}
			Logger.DEBUG(TAG, "Fullsize: " + fullSize + MEMORY_METRIC);
			return fullSize.toString();
		}
		return null;
	}

	/**
	 * @return
	 */
	private String getRam() {
		String ram = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(MEM_INFO_FILE));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.startsWith("MemTotal:")) {
					ram = str;
					break;
				}
			}
		} catch (IOException e) {
			Logger.ERROR(TAG, "Could not open " + MEM_INFO_FILE);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}

		if (ram != null && ram.length() > 0) {
			StringTokenizer token = new StringTokenizer(ram, ":");
			while (token.hasMoreElements()) {
				ram = (String) token.nextElement();
			}
			ram = ram.trim();
		}
		Integer ramValue = null;
		try {
			ramValue = Integer.parseInt(ram.substring(0, ram.length() - 3));
		} catch (Exception e) {
			Logger.ERROR(TAG, "Wrong RAM value!");
			return null;
		}

		if (ramValue > 0) {
			ramValue = ramValue;
			Logger.DEBUG(TAG, "RAM size: " + ramValue + MEMORY_METRIC);
			return ramValue.toString();
		}
		return null;
	}
}

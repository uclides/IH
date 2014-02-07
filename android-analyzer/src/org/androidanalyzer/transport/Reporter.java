package org.androidanalyzer.transport;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import org.androidanalyzer.core.Data;

/**
 * Abstract class representing sending data to the backend.
 * 
 */

public abstract class Reporter {
	//default backend server
//	private static final String HOST_DEFAULT = "http://212.95.166.45:8080/af/SubmitDeviceReport";
	private static final String HOST_DEFAULT = "http://AndroidFragmentation.com/SubmitDeviceReport";
	//current backend server
	private static String HOST = null;
	
	/** Key for the unique user identifier to be sent to the backend */
	public static final String KEY_USER_UID = "report.user.uid";

	/**
	 * Send data collected from available plugins to the server
	 * 
	 * @param data
	 *          Collected data
	 * @param host
	 *          Establish a connection to the specified host server
	 * @param extra
	 *          Extra report properties or metadata to be sent, <code>line KEY_USER_UID</code>
	 */
	abstract public Response send(Data data, URL host, Hashtable<String, String> extra) throws Exception;

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String mD5H(byte[] bytes) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(bytes);
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void setHost(String host) {
		HOST = host;
	}

	public static String getHost() {
		HOST = HOST != null ? HOST : HOST_DEFAULT;
		return HOST;
	}

    public abstract void send(Data root, URL url);

    public static class Response {
		public Hashtable<String, String> extra;
		public String reportID;
		public String responseStatus;
		
		public Response(String responseStatus, String reportID, Hashtable<String, String> extra) {
			Response.this.reportID = reportID;
			Response.this.responseStatus = responseStatus;
			Response.this.extra = extra;
		}
		
	}
	

}

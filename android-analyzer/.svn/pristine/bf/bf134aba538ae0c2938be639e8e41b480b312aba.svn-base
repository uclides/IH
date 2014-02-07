/**
 * 
 */
package org.androidanalyzer.core;

import java.util.ArrayList;

import org.androidanalyzer.Constants;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable Data object used in plugins to create data
 * tree of the acquired information
 * 
 * @author k.raev
 */
public class Data implements Parcelable {

  /* Bundle object for storing and retrieving values */
  private Bundle bundle;

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel out, int flags) {
    out.writeBundle(bundle);
  }

  public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
    public Data createFromParcel(Parcel in) {
      return new Data(in);
    }

    public Data[] newArray(int size) {
      return new Data[size];
    }
  };

  private Data(Parcel in) {
    bundle = in.readBundle();
    bundle.setClassLoader(getClass().getClassLoader());
  }

  public Data() {
    bundle = new Bundle();
  }

  /* Getters and Setters */
  /**
   * Sets the Node name
   * 
   * @param name
   *          - name of the Node
   */
  public void setName(String name) throws Exception {
    if (name != null) {
      bundle.putString(Constants.NODE_NAME, (String) name);
    } else {
      throw new Exception(Constants.VALUE_NULL);
    }
  }

  /**
   * Returns the Node name
   * 
   * @return the human readable name of the Node
   */
  public String getName() {
    return (String) bundle.get(Constants.NODE_NAME);
  }

  /**
   * Attaches value to the Node. Value types can be String
   * or org.androidanalyzer.core.Data, if other type is
   * given an exception is raised. When value is Data type
   * the method automatically generates ArrayList<Data> to
   * which the Data object is appended. This array list is
   * returned on getValue(). For String value dafault values
   * are set for
   * ValueType(Constants.NODE_VALUE_TYPE_STRING),
   * Status(Constants.NODE_STATUS_OK),InputSource(Constants.
   * NODE_INPUT_SOURCE_AUTOMATIC) and and
   * ConfirmationLevel(Constants
   * .NODE_CONFIRMATION_LEVEL_TEST_CASE_CONFIRMED). For Data
   * value the dafault values are
   * ValueType(Constants.NODE_VALUE_TYPE_DATA),
   * Status(Constants.NODE_STATUS_OK),InputSource(Constants.
   * NODE_INPUT_SOURCE_AUTOMATIC) and and
   * ConfirmationLevel(Constants
   * .NODE_CONFIRMATION_LEVEL_TEST_CASE_CONFIRMED).
   * 
   * @param value
   *          - value to be attached to the Node
   */
  public void setValue(Object value) throws Exception {
    if (value != null) {
      if (value instanceof String) {
        bundle.putString(Constants.NODE_VALUE, (String) value);
        setValueType(Constants.NODE_VALUE_TYPE_STRING);
        setStatus(Constants.NODE_STATUS_OK);
        setInputSource(Constants.NODE_INPUT_SOURCE_AUTOMATIC);
        setConfirmationLevel(Constants.NODE_CONFIRMATION_LEVEL_TEST_CASE_CONFIRMED);
      } else if (value instanceof Data) {
        if (((Data) value).getValue() != null) {
          Data data = (Data) value;
          setValueType(Constants.NODE_VALUE_TYPE_DATA);
          setStatus(Constants.NODE_STATUS_OK);
          setInputSource(Constants.NODE_INPUT_SOURCE_AUTOMATIC);
          setConfirmationLevel(Constants.NODE_CONFIRMATION_LEVEL_TEST_CASE_CONFIRMED);
          Object temp = bundle.get(Constants.NODE_VALUE);
          ArrayList<Parcelable> list = null;
          if (temp instanceof ArrayList<?>) {
            list = (ArrayList<Parcelable>) temp;
          }
          if (list == null) {
            list = new ArrayList<Parcelable>();
          }
          list.add(data);
          bundle.putParcelableArrayList(Constants.NODE_VALUE, list);
        } else {
          throw new Exception(Constants.NO_VALUE_IN_DATA_OBJECT);
        }
      } else {
        throw new Exception(Constants.VALUE_TYPE_INCORRECT);
      }
    } else {
      throw new Exception(Constants.VALUE_NULL);
    }
  }

  /**
   * Returns the value that the Node holds
   * 
   * @return - the Node value. Can be String or arraylist of
   *         one or more Data objects. If end Node and
   *         failed while obtaining the value, it'll return
   *         String with the failure reason(one of
   *         NODE_STATUS_FAILED... constants in
   *         org.anadroidanalyzer.Constants)
   */
  public Object getValue() {
    return bundle.get(Constants.NODE_VALUE);
  }

  /**
   * Sets the value type
   * 
   * @param valueType
   *          - the value type as defined in
   *          org.androidanalyzer.Constats class.
   */
  public void setValueType(String valueType) {
    if (valueType != null)
      bundle.putString(Constants.NODE_VALUE_TYPE, (String) valueType);
  }

  /**
   * Returns the value type
   * 
   * @return the value type
   */
  public String getValueType() {
    return bundle.getString(Constants.NODE_VALUE_TYPE);
  }

  /**
   * Sets metric type if there is one
   * 
   * @param valueMetric
   *          - the metric type
   */
  public void setValueMetric(String valueMetric) {
    if (valueMetric != null)
      bundle.putString(Constants.NODE_VALUE_METRIC, (String) valueMetric);
  }

  /**
   * Gets the metric if any or null if not specified
   * 
   * @return - the metric type or null
   */
  public String getValueMetric() {
    return bundle.getString(Constants.NODE_VALUE_METRIC);
  }

  /**
   * Sets comment for the Node
   * 
   * @param comment
   *          - the comment body
   */
  public void setComment(String comment) {
    if (comment != null)
      bundle.putString(Constants.NODE_COMMENT, (String) comment);
  }

  /**
   * Gets the comment for the Node or null if not set
   * 
   * @return - the comment for the node or null
   */
  public String getComment() {
    return (String) bundle.get(Constants.NODE_COMMENT);
  }

  /**
   * Returns the confirmation level of the information in
   * the Node
   * 
   * @return - the confirmation level
   */
  public String getConfirmationLevel() {
    return (String) bundle.get(Constants.NODE_CONFIRMATION_LEVEL);
  }

  /**
   * Sets the confirmation level of the information in the
   * Node
   * 
   * @param confLevel
   *          - the confirmation level
   */
  public void setConfirmationLevel(String confLevel) {
    if (confLevel != null)
      bundle.putString(Constants.NODE_CONFIRMATION_LEVEL, (String) confLevel);
  }

  /**
   * Returns the source of the Node's info
   * 
   * @return - the sorce of the Node info
   */
  public String getInputSource() {
    return (String) bundle.get(Constants.NODE_INPUT_SOURCE);
  }

  /**
   * Gets the source of the Node's info
   * 
   * @param inputSource
   *          - the input source of the infomation in the
   *          Node
   */
  public void setInputSource(String inputSource) {
    if (inputSource != null)
      bundle.putString(Constants.NODE_INPUT_SOURCE, (String) inputSource);
  }

  /**
   * Get method to use with raw key to get info from the
   * Node
   * 
   * @param key
   *          - key for the info to be obtained
   * @return - the value for the key or null
   */
  public Object get(String key) {
    return bundle.get(key);
  }

  /**
   * Set method to add key/value String pair to the Node
   * 
   * @param key
   *          - key for the info to be obtained
   * @param value
   *          - String value for the key
   */
  public void setString(String key, String value) {
    if (key != null && value != null)
      bundle.putString(key, value);
  }

  /**
   * Gets status of the value,if obtained or failed while
   * obtaining
   * 
   * @return - status of the value in the node(one
   *         NODE_STATUS_... constants defined in
   *         org.androidanalyzer.Constants
   */
  public String getStatus() {
    return bundle.getString(Constants.NODE_STATUS);
  }

  /**
   * Sets status of obtaining the value. One of
   * NODE_STATUS_... constant defined in
   * org.androidanalyzer.Constants
   * 
   * @param nodeStatus
   *          - status of the value obtained for the node
   */
  public void setStatus(String nodeStatus) throws Exception {
    if (nodeStatus != null) {
      bundle.putString(Constants.NODE_STATUS, (String) nodeStatus);
    } else {
      throw new Exception(Constants.VALUE_NULL);
    }
  }

}

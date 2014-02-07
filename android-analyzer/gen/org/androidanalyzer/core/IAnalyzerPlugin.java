/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Uclides\\Desktop\\android-analyzer\\src\\org\\androidanalyzer\\core\\IAnalyzerPlugin.aidl
 */
package org.androidanalyzer.core;
// 	Interface to be used with Analyzer Plugin

public interface IAnalyzerPlugin extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.androidanalyzer.core.IAnalyzerPlugin
{
private static final java.lang.String DESCRIPTOR = "org.androidanalyzer.core.IAnalyzerPlugin";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.androidanalyzer.core.IAnalyzerPlugin interface,
 * generating a proxy if needed.
 */
public static org.androidanalyzer.core.IAnalyzerPlugin asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.androidanalyzer.core.IAnalyzerPlugin))) {
return ((org.androidanalyzer.core.IAnalyzerPlugin)iin);
}
return new org.androidanalyzer.core.IAnalyzerPlugin.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getDescription:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getDescription();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_startAnalysis:
{
data.enforceInterface(DESCRIPTOR);
org.androidanalyzer.core.Data _result = this.startAnalysis();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_stopAnalysis:
{
data.enforceInterface(DESCRIPTOR);
this.stopAnalysis();
reply.writeNoException();
return true;
}
case TRANSACTION_getTimeout:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getTimeout();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_setDebugEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setDebugEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getClassName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getClassName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getVersion:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getVersion();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getVendor:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getVendor();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getStatus:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getStatus();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_isUIRequired:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isUIRequired();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.androidanalyzer.core.IAnalyzerPlugin
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getDescription() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDescription, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.androidanalyzer.core.Data startAnalysis() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.androidanalyzer.core.Data _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startAnalysis, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = org.androidanalyzer.core.Data.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void stopAnalysis() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopAnalysis, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public long getTimeout() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTimeout, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setDebugEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setDebugEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getClassName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getClassName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getVendor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVendor, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isUIRequired() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isUIRequired, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getDescription = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_startAnalysis = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_stopAnalysis = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getTimeout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setDebugEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getClassName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getVendor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isUIRequired = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
}
public java.lang.String getName() throws android.os.RemoteException;
public java.lang.String getDescription() throws android.os.RemoteException;
public org.androidanalyzer.core.Data startAnalysis() throws android.os.RemoteException;
public void stopAnalysis() throws android.os.RemoteException;
public long getTimeout() throws android.os.RemoteException;
public void setDebugEnabled(boolean enabled) throws android.os.RemoteException;
public java.lang.String getClassName() throws android.os.RemoteException;
public java.lang.String getVersion() throws android.os.RemoteException;
public java.lang.String getVendor() throws android.os.RemoteException;
public java.lang.String getStatus() throws android.os.RemoteException;
public boolean isUIRequired() throws android.os.RemoteException;
}

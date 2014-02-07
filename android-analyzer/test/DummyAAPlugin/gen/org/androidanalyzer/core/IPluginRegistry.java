/*___Generated_by_IDEA___*/

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Uclides\\Desktop\\android-analyzer\\test\\DummyAAPlugin\\src\\org\\androidanalyzer\\core\\IPluginRegistry.aidl
 */
package org.androidanalyzer.core;
//	Interface to be used by each Analyzer Plugin to register
//  itself into the Core registry

public interface IPluginRegistry extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.androidanalyzer.core.IPluginRegistry
{
private static final java.lang.String DESCRIPTOR = "org.androidanalyzer.core.IPluginRegistry";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.androidanalyzer.core.IPluginRegistry interface,
 * generating a proxy if needed.
 */
public static org.androidanalyzer.core.IPluginRegistry asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.androidanalyzer.core.IPluginRegistry))) {
return ((org.androidanalyzer.core.IPluginRegistry)iin);
}
return new org.androidanalyzer.core.IPluginRegistry.Stub.Proxy(obj);
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
case TRANSACTION_registerPlugin:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.registerPlugin(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.androidanalyzer.core.IPluginRegistry
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
@Override public void registerPlugin(java.lang.String plugin) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(plugin);
mRemote.transact(Stub.TRANSACTION_registerPlugin, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerPlugin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void registerPlugin(java.lang.String plugin) throws android.os.RemoteException;
}

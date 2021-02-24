/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/wayos/car/settings/IWayosCarConfigurationManager.aidl
 */
package wayos.car.settings;
// Declare any non-default types here with import statements

public interface IWayosCarConfigurationManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements wayos.car.settings.IWayosCarConfigurationManager
{
private static final java.lang.String DESCRIPTOR = "wayos.car.settings.IWayosCarConfigurationManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an wayos.car.settings.IWayosCarConfigurationManager interface,
 * generating a proxy if needed.
 */
public static wayos.car.settings.IWayosCarConfigurationManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof wayos.car.settings.IWayosCarConfigurationManager))) {
return ((wayos.car.settings.IWayosCarConfigurationManager)iin);
}
return new wayos.car.settings.IWayosCarConfigurationManager.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_getCarConfiguration:
{
data.enforceInterface(descriptor);
wayos.car.settings.CarConfigs _result = this.getCarConfiguration();
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
case TRANSACTION_updateCarConfig:
{
data.enforceInterface(descriptor);
wayos.car.settings.CarConfigs _arg0;
if ((0!=data.readInt())) {
_arg0 = wayos.car.settings.CarConfigs.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.updateCarConfig(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getSteerWheelType:
{
data.enforceInterface(descriptor);
int _result = this.getSteerWheelType();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements wayos.car.settings.IWayosCarConfigurationManager
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
/**
     * Get current car config.
     */
@Override public wayos.car.settings.CarConfigs getCarConfiguration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
wayos.car.settings.CarConfigs _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCarConfiguration, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = wayos.car.settings.CarConfigs.CREATOR.createFromParcel(_reply);
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
/**
     * Modify the CarConfig,only used in testMode.
     */
@Override public void updateCarConfig(wayos.car.settings.CarConfigs config) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((config!=null)) {
_data.writeInt(1);
config.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_updateCarConfig, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getSteerWheelType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSteerWheelType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getCarConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_updateCarConfig = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getSteerWheelType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
     * Get current car config.
     */
public wayos.car.settings.CarConfigs getCarConfiguration() throws android.os.RemoteException;
/**
     * Modify the CarConfig,only used in testMode.
     */
public void updateCarConfig(wayos.car.settings.CarConfigs config) throws android.os.RemoteException;
public int getSteerWheelType() throws android.os.RemoteException;
}

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/android/car/media/ICarMedia.aidl
 */
package android.car.media;
/**
 * Binder interface for {@link android.car.media.CarMediaManager}.
 * Check {@link android.car.media.CarMediaManager} APIs for expected behavior of each call.
 *
 * @hide
 */
public interface ICarMedia extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.car.media.ICarMedia
{
private static final java.lang.String DESCRIPTOR = "android.car.media.ICarMedia";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.car.media.ICarMedia interface,
 * generating a proxy if needed.
 */
public static android.car.media.ICarMedia asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.car.media.ICarMedia))) {
return ((android.car.media.ICarMedia)iin);
}
return new android.car.media.ICarMedia.Stub.Proxy(obj);
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
case TRANSACTION_getMediaSource:
{
data.enforceInterface(descriptor);
android.content.ComponentName _result = this.getMediaSource();
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
case TRANSACTION_setMediaSource:
{
data.enforceInterface(descriptor);
android.content.ComponentName _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.ComponentName.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setMediaSource(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_registerMediaSourceListener:
{
data.enforceInterface(descriptor);
android.car.media.ICarMediaSourceListener _arg0;
_arg0 = android.car.media.ICarMediaSourceListener.Stub.asInterface(data.readStrongBinder());
this.registerMediaSourceListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterMediaSourceListener:
{
data.enforceInterface(descriptor);
android.car.media.ICarMediaSourceListener _arg0;
_arg0 = android.car.media.ICarMediaSourceListener.Stub.asInterface(data.readStrongBinder());
this.unregisterMediaSourceListener(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements android.car.media.ICarMedia
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
@Override public android.content.ComponentName getMediaSource() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.content.ComponentName _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMediaSource, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.content.ComponentName.CREATOR.createFromParcel(_reply);
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
@Override public void setMediaSource(android.content.ComponentName mediaSource) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((mediaSource!=null)) {
_data.writeInt(1);
mediaSource.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setMediaSource, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerMediaSourceListener(android.car.media.ICarMediaSourceListener callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerMediaSourceListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterMediaSourceListener(android.car.media.ICarMediaSourceListener callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterMediaSourceListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getMediaSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setMediaSource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerMediaSourceListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterMediaSourceListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public android.content.ComponentName getMediaSource() throws android.os.RemoteException;
public void setMediaSource(android.content.ComponentName mediaSource) throws android.os.RemoteException;
public void registerMediaSourceListener(android.car.media.ICarMediaSourceListener callback) throws android.os.RemoteException;
public void unregisterMediaSourceListener(android.car.media.ICarMediaSourceListener callback) throws android.os.RemoteException;
}

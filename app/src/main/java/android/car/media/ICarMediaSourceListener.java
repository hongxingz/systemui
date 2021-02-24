/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/android/car/media/ICarMediaSourceListener.aidl
 */
package android.car.media;
/**
 * Binder interface to listen for media source changes
 *
 * @hide
 */
public interface ICarMediaSourceListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.car.media.ICarMediaSourceListener
{
private static final java.lang.String DESCRIPTOR = "android.car.media.ICarMediaSourceListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.car.media.ICarMediaSourceListener interface,
 * generating a proxy if needed.
 */
public static android.car.media.ICarMediaSourceListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.car.media.ICarMediaSourceListener))) {
return ((android.car.media.ICarMediaSourceListener)iin);
}
return new android.car.media.ICarMediaSourceListener.Stub.Proxy(obj);
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
case TRANSACTION_onMediaSourceChanged:
{
data.enforceInterface(descriptor);
android.content.ComponentName _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.ComponentName.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onMediaSourceChanged(_arg0);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements android.car.media.ICarMediaSourceListener
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
@Override public void onMediaSourceChanged(android.content.ComponentName newSource) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((newSource!=null)) {
_data.writeInt(1);
newSource.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onMediaSourceChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onMediaSourceChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onMediaSourceChanged(android.content.ComponentName newSource) throws android.os.RemoteException;
}

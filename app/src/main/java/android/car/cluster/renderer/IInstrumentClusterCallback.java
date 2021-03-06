/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/android/car/cluster/renderer/IInstrumentClusterCallback.aidl
 */
package android.car.cluster.renderer;
/**
 * This interface defines the communication channel between the cluster vendor implementation and
 * Car Service.
 *
 * @hide
 */
public interface IInstrumentClusterCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.car.cluster.renderer.IInstrumentClusterCallback
{
private static final java.lang.String DESCRIPTOR = "android.car.cluster.renderer.IInstrumentClusterCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.car.cluster.renderer.IInstrumentClusterCallback interface,
 * generating a proxy if needed.
 */
public static android.car.cluster.renderer.IInstrumentClusterCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.car.cluster.renderer.IInstrumentClusterCallback))) {
return ((android.car.cluster.renderer.IInstrumentClusterCallback)iin);
}
return new android.car.cluster.renderer.IInstrumentClusterCallback.Stub.Proxy(obj);
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
case TRANSACTION_setClusterActivityLaunchOptions:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.Bundle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.setClusterActivityLaunchOptions(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setClusterActivityState:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.Bundle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.setClusterActivityState(_arg0, _arg1);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements android.car.cluster.renderer.IInstrumentClusterCallback
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
     * Notify Car Service how to launch an activity for particular category.
     *
     * @param category cluster activity category,
     *        see {@link android.car.cluster.CarInstrumentClusterManager} for details.
     * @param activityOptions this bundle will be converted to {@link android.app.ActivityOptions}
     *        and used when starting an activity. It may contain information such as virtual display
     *        id or activity stack id where to start cluster activity.
     *
     * @hide
     */
@Override public void setClusterActivityLaunchOptions(java.lang.String category, android.os.Bundle activityOptions) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(category);
if ((activityOptions!=null)) {
_data.writeInt(1);
activityOptions.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setClusterActivityLaunchOptions, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Activities launched on virtual display will be in onPause state most of the time, so they
     * can't really know whether they visible on the screen or not. We need to propagate this
     * information along with unobscured bounds (and possible other info) from instrument cluster
     * vendor implementation to activity.
     *
     * @param category cluster activity category to which this state applies,
     *        see {@link android.car.cluster.CarInstrumentClusterManager} for details.
     * @param clusterActivityState is a {@link Bundle} object,
     *        see {@link android.car.cluster.ClusterActivityState} for how to construct the bundle.
     * @hide
     */
@Override public void setClusterActivityState(java.lang.String category, android.os.Bundle clusterActivityState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(category);
if ((clusterActivityState!=null)) {
_data.writeInt(1);
clusterActivityState.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setClusterActivityState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_setClusterActivityLaunchOptions = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setClusterActivityState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Notify Car Service how to launch an activity for particular category.
     *
     * @param category cluster activity category,
     *        see {@link android.car.cluster.CarInstrumentClusterManager} for details.
     * @param activityOptions this bundle will be converted to {@link android.app.ActivityOptions}
     *        and used when starting an activity. It may contain information such as virtual display
     *        id or activity stack id where to start cluster activity.
     *
     * @hide
     */
public void setClusterActivityLaunchOptions(java.lang.String category, android.os.Bundle activityOptions) throws android.os.RemoteException;
/**
     * Activities launched on virtual display will be in onPause state most of the time, so they
     * can't really know whether they visible on the screen or not. We need to propagate this
     * information along with unobscured bounds (and possible other info) from instrument cluster
     * vendor implementation to activity.
     *
     * @param category cluster activity category to which this state applies,
     *        see {@link android.car.cluster.CarInstrumentClusterManager} for details.
     * @param clusterActivityState is a {@link Bundle} object,
     *        see {@link android.car.cluster.ClusterActivityState} for how to construct the bundle.
     * @hide
     */
public void setClusterActivityState(java.lang.String category, android.os.Bundle clusterActivityState) throws android.os.RemoteException;
}

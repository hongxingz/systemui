/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/wayos/car/media/ICarAudioPolicyListener.aidl
 */
package wayos.car.media;
public interface ICarAudioPolicyListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements wayos.car.media.ICarAudioPolicyListener
{
private static final java.lang.String DESCRIPTOR = "wayos.car.media.ICarAudioPolicyListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an wayos.car.media.ICarAudioPolicyListener interface,
 * generating a proxy if needed.
 */
public static wayos.car.media.ICarAudioPolicyListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof wayos.car.media.ICarAudioPolicyListener))) {
return ((wayos.car.media.ICarAudioPolicyListener)iin);
}
return new wayos.car.media.ICarAudioPolicyListener.Stub.Proxy(obj);
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
case TRANSACTION_onAudioFocusGrant:
{
data.enforceInterface(descriptor);
wayos.car.media.CarAudioFocusInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = wayos.car.media.CarAudioFocusInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
boolean _result = this.onAudioFocusGrant(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onAudioFocusLoss:
{
data.enforceInterface(descriptor);
wayos.car.media.CarAudioFocusInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = wayos.car.media.CarAudioFocusInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _result = this.onAudioFocusLoss(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onAudioFocusRequest:
{
data.enforceInterface(descriptor);
wayos.car.media.CarAudioFocusInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = wayos.car.media.CarAudioFocusInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
boolean _result = this.onAudioFocusRequest(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onAudioFocusAbandon:
{
data.enforceInterface(descriptor);
wayos.car.media.CarAudioFocusInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = wayos.car.media.CarAudioFocusInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.onAudioFocusAbandon(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements wayos.car.media.ICarAudioPolicyListener
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
@Override public boolean onAudioFocusGrant(wayos.car.media.CarAudioFocusInfo afi, int requestResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((afi!=null)) {
_data.writeInt(1);
afi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(requestResult);
mRemote.transact(Stub.TRANSACTION_onAudioFocusGrant, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean onAudioFocusLoss(wayos.car.media.CarAudioFocusInfo afi, boolean wasNotified) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((afi!=null)) {
_data.writeInt(1);
afi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((wasNotified)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onAudioFocusLoss, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
        * Called whenever an application requests audio focus.
        * Only ever called if the {@link AudioPolicy} was built with
        * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
        * @param afi information about the focus request and the requester
        * @param requestResult deprecated after the addition of
        *     {@link AudioManager#setFocusRequestResult(AudioFocusInfo, int, AudioPolicy)}
        *     in Android P, always equal to {@link #AUDIOFOCUS_REQUEST_GRANTED}.
        */
@Override public boolean onAudioFocusRequest(wayos.car.media.CarAudioFocusInfo afi, int requestResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((afi!=null)) {
_data.writeInt(1);
afi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(requestResult);
mRemote.transact(Stub.TRANSACTION_onAudioFocusRequest, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
         * Called whenever an application abandons audio focus.
         * Only ever called if the {@link AudioPolicy} was built with
         * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
         * @param afi information about the focus request being abandoned and the original
         *     requester.
         */
@Override public boolean onAudioFocusAbandon(wayos.car.media.CarAudioFocusInfo afi) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((afi!=null)) {
_data.writeInt(1);
afi.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onAudioFocusAbandon, _data, _reply, 0);
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
static final int TRANSACTION_onAudioFocusGrant = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onAudioFocusLoss = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onAudioFocusRequest = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onAudioFocusAbandon = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public boolean onAudioFocusGrant(wayos.car.media.CarAudioFocusInfo afi, int requestResult) throws android.os.RemoteException;
public boolean onAudioFocusLoss(wayos.car.media.CarAudioFocusInfo afi, boolean wasNotified) throws android.os.RemoteException;
/**
        * Called whenever an application requests audio focus.
        * Only ever called if the {@link AudioPolicy} was built with
        * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
        * @param afi information about the focus request and the requester
        * @param requestResult deprecated after the addition of
        *     {@link AudioManager#setFocusRequestResult(AudioFocusInfo, int, AudioPolicy)}
        *     in Android P, always equal to {@link #AUDIOFOCUS_REQUEST_GRANTED}.
        */
public boolean onAudioFocusRequest(wayos.car.media.CarAudioFocusInfo afi, int requestResult) throws android.os.RemoteException;
/**
         * Called whenever an application abandons audio focus.
         * Only ever called if the {@link AudioPolicy} was built with
         * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
         * @param afi information about the focus request being abandoned and the original
         *     requester.
         */
public boolean onAudioFocusAbandon(wayos.car.media.CarAudioFocusInfo afi) throws android.os.RemoteException;
}

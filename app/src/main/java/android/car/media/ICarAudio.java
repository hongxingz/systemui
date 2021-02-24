/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/android/car/media/ICarAudio.aidl
 */
package android.car.media;
/**
 * Binder interface for {@link android.car.media.CarAudioManager}.
 * Check {@link android.car.media.CarAudioManager} APIs for expected behavior of each call.
 *
 * @hide
 */
public interface ICarAudio extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.car.media.ICarAudio
{
private static final java.lang.String DESCRIPTOR = "android.car.media.ICarAudio";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.car.media.ICarAudio interface,
 * generating a proxy if needed.
 */
public static android.car.media.ICarAudio asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.car.media.ICarAudio))) {
return ((android.car.media.ICarAudio)iin);
}
return new android.car.media.ICarAudio.Stub.Proxy(obj);
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
case TRANSACTION_setGroupVolume:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setGroupVolume(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_getGroupMaxVolume:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _result = this.getGroupMaxVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getGroupMinVolume:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _result = this.getGroupMinVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getGroupVolume:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _result = this.getGroupVolume(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setFadeTowardFront:
{
data.enforceInterface(descriptor);
float _arg0;
_arg0 = data.readFloat();
this.setFadeTowardFront(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setBalanceTowardRight:
{
data.enforceInterface(descriptor);
float _arg0;
_arg0 = data.readFloat();
this.setBalanceTowardRight(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getExternalSources:
{
data.enforceInterface(descriptor);
java.lang.String[] _result = this.getExternalSources();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_createAudioPatch:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
android.car.media.CarAudioPatchHandle _result = this.createAudioPatch(_arg0, _arg1, _arg2);
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
case TRANSACTION_releaseAudioPatch:
{
data.enforceInterface(descriptor);
android.car.media.CarAudioPatchHandle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.car.media.CarAudioPatchHandle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.releaseAudioPatch(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getVolumeGroupCount:
{
data.enforceInterface(descriptor);
int _result = this.getVolumeGroupCount();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getVolumeGroupIdForUsage:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _result = this.getVolumeGroupIdForUsage(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getUsagesForVolumeGroupId:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int[] _result = this.getUsagesForVolumeGroupId(_arg0);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_registerVolumeCallback:
{
data.enforceInterface(descriptor);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.registerVolumeCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterVolumeCallback:
{
data.enforceInterface(descriptor);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.unregisterVolumeCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setEqualizer:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
float[] _arg1;
_arg1 = data.createFloatArray();
this.setEqualizer(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getEqualizer:
{
data.enforceInterface(descriptor);
wayos.car.media.CarEqualerInfo _result = this.getEqualizer();
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
case TRANSACTION_getUserEqualizer:
{
data.enforceInterface(descriptor);
wayos.car.media.CarEqualerInfo _result = this.getUserEqualizer();
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
case TRANSACTION_getBalance:
{
data.enforceInterface(descriptor);
int[] _result = this.getBalance();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_setBalance:
{
data.enforceInterface(descriptor);
int[] _arg0;
_arg0 = data.createIntArray();
this.setBalance(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getTouchSoundSetting:
{
data.enforceInterface(descriptor);
boolean _result = this.getTouchSoundSetting();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setTouchSoundSetting:
{
data.enforceInterface(descriptor);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setTouchSoundSetting(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setBassMiddleTreble:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setBassMiddleTreble(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_getBassMiddleTreble:
{
data.enforceInterface(descriptor);
int[] _result = this.getBassMiddleTreble();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_registerAudioEffectCallback:
{
data.enforceInterface(descriptor);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.registerAudioEffectCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterAudioEffectCallback:
{
data.enforceInterface(descriptor);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.unregisterAudioEffectCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setAdditionalAudioFocusPolicy:
{
data.enforceInterface(descriptor);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.setAdditionalAudioFocusPolicy(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements android.car.media.ICarAudio
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
@Override public void setGroupVolume(int groupId, int index, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(groupId);
_data.writeInt(index);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_setGroupVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getGroupMaxVolume(int groupId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(groupId);
mRemote.transact(Stub.TRANSACTION_getGroupMaxVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getGroupMinVolume(int groupId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(groupId);
mRemote.transact(Stub.TRANSACTION_getGroupMinVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getGroupVolume(int groupId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(groupId);
mRemote.transact(Stub.TRANSACTION_getGroupVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setFadeTowardFront(float value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeFloat(value);
mRemote.transact(Stub.TRANSACTION_setFadeTowardFront, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setBalanceTowardRight(float value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeFloat(value);
mRemote.transact(Stub.TRANSACTION_setBalanceTowardRight, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String[] getExternalSources() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getExternalSources, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.car.media.CarAudioPatchHandle createAudioPatch(java.lang.String sourceAddress, int usage, int gainInMillibels) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.car.media.CarAudioPatchHandle _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(sourceAddress);
_data.writeInt(usage);
_data.writeInt(gainInMillibels);
mRemote.transact(Stub.TRANSACTION_createAudioPatch, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.car.media.CarAudioPatchHandle.CREATOR.createFromParcel(_reply);
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
@Override public void releaseAudioPatch(android.car.media.CarAudioPatchHandle patch) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((patch!=null)) {
_data.writeInt(1);
patch.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_releaseAudioPatch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getVolumeGroupCount() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVolumeGroupCount, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getVolumeGroupIdForUsage(int usage) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(usage);
mRemote.transact(Stub.TRANSACTION_getVolumeGroupIdForUsage, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getUsagesForVolumeGroupId(int groupId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(groupId);
mRemote.transact(Stub.TRANSACTION_getUsagesForVolumeGroupId, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * IBinder is ICarVolumeCallback but passed as IBinder due to aidl hidden.
     */
@Override public void registerVolumeCallback(android.os.IBinder binder) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(binder);
mRemote.transact(Stub.TRANSACTION_registerVolumeCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterVolumeCallback(android.os.IBinder binder) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(binder);
mRemote.transact(Stub.TRANSACTION_unregisterVolumeCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
     * @WAYOS add for audio effect begin.
     */
@Override public void setEqualizer(int eqType, float[] eqConfigs) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eqType);
_data.writeFloatArray(eqConfigs);
mRemote.transact(Stub.TRANSACTION_setEqualizer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public wayos.car.media.CarEqualerInfo getEqualizer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
wayos.car.media.CarEqualerInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEqualizer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = wayos.car.media.CarEqualerInfo.CREATOR.createFromParcel(_reply);
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
@Override public wayos.car.media.CarEqualerInfo getUserEqualizer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
wayos.car.media.CarEqualerInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUserEqualizer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = wayos.car.media.CarEqualerInfo.CREATOR.createFromParcel(_reply);
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
@Override public int[] getBalance() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBalance, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setBalance(int[] balance) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeIntArray(balance);
mRemote.transact(Stub.TRANSACTION_setBalance, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getTouchSoundSetting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getTouchSoundSetting, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setTouchSoundSetting(boolean isSound) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isSound)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setTouchSoundSetting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setBassMiddleTreble(int bass, int middle, int treble) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(bass);
_data.writeInt(middle);
_data.writeInt(treble);
mRemote.transact(Stub.TRANSACTION_setBassMiddleTreble, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int[] getBassMiddleTreble() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBassMiddleTreble, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void registerAudioEffectCallback(android.os.IBinder carAudioEffectCb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(carAudioEffectCb);
mRemote.transact(Stub.TRANSACTION_registerAudioEffectCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterAudioEffectCallback(android.os.IBinder carAudioEffectCb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(carAudioEffectCb);
mRemote.transact(Stub.TRANSACTION_unregisterAudioEffectCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
     * @WAYOS add for audio effect end.
     *//* @WAYOS add for Additional policy eg:carplay*/
@Override public void setAdditionalAudioFocusPolicy(android.os.IBinder carAudioPolicyListener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(carAudioPolicyListener);
mRemote.transact(Stub.TRANSACTION_setAdditionalAudioFocusPolicy, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_setGroupVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getGroupMaxVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getGroupMinVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getGroupVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setFadeTowardFront = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setBalanceTowardRight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getExternalSources = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_createAudioPatch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_releaseAudioPatch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getVolumeGroupCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getVolumeGroupIdForUsage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getUsagesForVolumeGroupId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_registerVolumeCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_unregisterVolumeCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setEqualizer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_getEqualizer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getUserEqualizer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getBalance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_setBalance = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getTouchSoundSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_setTouchSoundSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setBassMiddleTreble = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getBassMiddleTreble = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_registerAudioEffectCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_unregisterAudioEffectCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_setAdditionalAudioFocusPolicy = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
}
public void setGroupVolume(int groupId, int index, int flags) throws android.os.RemoteException;
public int getGroupMaxVolume(int groupId) throws android.os.RemoteException;
public int getGroupMinVolume(int groupId) throws android.os.RemoteException;
public int getGroupVolume(int groupId) throws android.os.RemoteException;
public void setFadeTowardFront(float value) throws android.os.RemoteException;
public void setBalanceTowardRight(float value) throws android.os.RemoteException;
public java.lang.String[] getExternalSources() throws android.os.RemoteException;
public android.car.media.CarAudioPatchHandle createAudioPatch(java.lang.String sourceAddress, int usage, int gainInMillibels) throws android.os.RemoteException;
public void releaseAudioPatch(android.car.media.CarAudioPatchHandle patch) throws android.os.RemoteException;
public int getVolumeGroupCount() throws android.os.RemoteException;
public int getVolumeGroupIdForUsage(int usage) throws android.os.RemoteException;
public int[] getUsagesForVolumeGroupId(int groupId) throws android.os.RemoteException;
/**
     * IBinder is ICarVolumeCallback but passed as IBinder due to aidl hidden.
     */
public void registerVolumeCallback(android.os.IBinder binder) throws android.os.RemoteException;
public void unregisterVolumeCallback(android.os.IBinder binder) throws android.os.RemoteException;
/*
     * @WAYOS add for audio effect begin.
     */
public void setEqualizer(int eqType, float[] eqConfigs) throws android.os.RemoteException;
public wayos.car.media.CarEqualerInfo getEqualizer() throws android.os.RemoteException;
public wayos.car.media.CarEqualerInfo getUserEqualizer() throws android.os.RemoteException;
public int[] getBalance() throws android.os.RemoteException;
public void setBalance(int[] balance) throws android.os.RemoteException;
public boolean getTouchSoundSetting() throws android.os.RemoteException;
public void setTouchSoundSetting(boolean isSound) throws android.os.RemoteException;
public void setBassMiddleTreble(int bass, int middle, int treble) throws android.os.RemoteException;
public int[] getBassMiddleTreble() throws android.os.RemoteException;
public void registerAudioEffectCallback(android.os.IBinder carAudioEffectCb) throws android.os.RemoteException;
public void unregisterAudioEffectCallback(android.os.IBinder carAudioEffectCb) throws android.os.RemoteException;
/*
     * @WAYOS add for audio effect end.
     *//* @WAYOS add for Additional policy eg:carplay*/
public void setAdditionalAudioFocusPolicy(android.os.IBinder carAudioPolicyListener) throws android.os.RemoteException;
}

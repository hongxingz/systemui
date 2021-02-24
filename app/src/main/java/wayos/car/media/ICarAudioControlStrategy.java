/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/wayos/car/media/ICarAudioControlStrategy.aidl
 */
package wayos.car.media;
/**
 * Binder interface for {@link wayos.car.media.CarAudioControlStrategyManager}.
 * Check {@link wayos.car.media.CarAudioControlStrategyManager} APIs for expected behavior of each call.
 *
 * @hide
 */
public interface ICarAudioControlStrategy extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements wayos.car.media.ICarAudioControlStrategy
{
private static final java.lang.String DESCRIPTOR = "wayos.car.media.ICarAudioControlStrategy";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an wayos.car.media.ICarAudioControlStrategy interface,
 * generating a proxy if needed.
 */
public static wayos.car.media.ICarAudioControlStrategy asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof wayos.car.media.ICarAudioControlStrategy))) {
return ((wayos.car.media.ICarAudioControlStrategy)iin);
}
return new wayos.car.media.ICarAudioControlStrategy.Stub.Proxy(obj);
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
case TRANSACTION_getSoundCompensationWithSpeed:
{
data.enforceInterface(descriptor);
int _result = this.getSoundCompensationWithSpeed();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setSoundCompensationWithSpeed:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
this.setSoundCompensationWithSpeed(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements wayos.car.media.ICarAudioControlStrategy
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
@Override public int getSoundCompensationWithSpeed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSoundCompensationWithSpeed, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setSoundCompensationWithSpeed(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setSoundCompensationWithSpeed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getSoundCompensationWithSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setSoundCompensationWithSpeed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public int getSoundCompensationWithSpeed() throws android.os.RemoteException;
public void setSoundCompensationWithSpeed(int mode) throws android.os.RemoteException;
}

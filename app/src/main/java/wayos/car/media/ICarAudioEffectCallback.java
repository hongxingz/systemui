/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: packages/services/Car/car-lib/src/wayos/car/media/ICarAudioEffectCallback.aidl
 */
package wayos.car.media;
// Declare any non-default types here with import statements
/**
* Binder interface to callback the volume key events.
*/
public interface ICarAudioEffectCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements wayos.car.media.ICarAudioEffectCallback
{
private static final java.lang.String DESCRIPTOR = "wayos.car.media.ICarAudioEffectCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an wayos.car.media.ICarAudioEffectCallback interface,
 * generating a proxy if needed.
 */
public static wayos.car.media.ICarAudioEffectCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof wayos.car.media.ICarAudioEffectCallback))) {
return ((wayos.car.media.ICarAudioEffectCallback)iin);
}
return new wayos.car.media.ICarAudioEffectCallback.Stub.Proxy(obj);
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
case TRANSACTION_onBalaceChange:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
this.onBalaceChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onEqualizerChange:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onEqualizerChange(_arg0, _arg1);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements wayos.car.media.ICarAudioEffectCallback
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
     * This is called whenever audio Balace is changed.
     */
@Override public void onBalaceChange(int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_onBalaceChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * This is called whenever the Equalizer state is changed.
     */
@Override public void onEqualizerChange(int type, int flags) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeInt(flags);
mRemote.transact(Stub.TRANSACTION_onEqualizerChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onBalaceChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onEqualizerChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * This is called whenever audio Balace is changed.
     */
public void onBalaceChange(int flags) throws android.os.RemoteException;
/**
     * This is called whenever the Equalizer state is changed.
     */
public void onEqualizerChange(int type, int flags) throws android.os.RemoteException;
}

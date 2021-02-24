package wayos.car.hardware.drivermonitor;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.annotation.IntDef;

import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyConfig;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import android.util.Log;

import android.util.ArraySet;
import android.hardware.automotive.vehicle.V2_0.VehicleProperty;

public final class CarDMSManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarDMSManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarDMSEventCallback> mCallbacks = new ArraySet<>();
    private CarPropertyEventListenerToBase mListenerToBase = null;


    public static final int ID_DMS_F_SYSTEM = 0x21401500;
    public static final int ID_DMS_ST_DTCFLAG = 0x21401501;
    public static final int ID_DMS_FACE_CAM_SWITCH_SET = 0x21401502;
    public static final int ID_DMS_FACE_CAM_SWITCH_STATUS = 0x21401503;
    public static final int ID_DMS_FACE_CAM_EYECLOSE_SWITCH_SET = 0x21401504;
    public static final int ID_DMS_FACE_CAM_EYECLOSE_SWITCH_STATUS = 0x21401505;
    public static final int ID_DMS_FACE_CAM_EYECLOSE_DETECT = 0x21401506;
    public static final int ID_DMS_FACE_CAM_YAWN_SWITCH_SET = 0x21401507;
    public static final int ID_DMS_FACE_CAM_YAWN_SWITCH_STATUS = 0x21401508;
    public static final int ID_DMS_FACE_CAM_YAWN_DETECT = 0x21401509;
    public static final int ID_DMS_FACE_CAM_CALL_SWITCH_SET = 0x2140150A;
    public static final int ID_DMS_FACE_CAM_CALL_SWITCH_STATUS = 0x2140150B;
    public static final int ID_DMS_FACE_CAM_CALL_DETECT = 0x2140150C;
    public static final int ID_DMS_FACE_CAM_SMOKING_SWITCH_SET = 0x2140150D;
    public static final int ID_DMS_FACE_CAM_SMOKING_SWITCH_STATUS = 0x2140150E;
    public static final int ID_DMS_FACE_CAM_SMOKING_DETECT = 0x2140150F;
    public static final int ID_DMS_FACE_CAM_NODALARM_SWITCH_SET = 0x21401510;
    public static final int ID_DMS_FACE_CAM_NODALARM_SWITCH_STATUS = 0x21401511;
    public static final int ID_DMS_FACE_CAM_NODALARM_DETECT = 0x21401512;
    public static final int ID_DMS_FACE_CAM_DISTRACTION_SWITCH_SET = 0x21401513;
    public static final int ID_DMS_FACE_CAM_DISTRACTION_SWITCH_STATUS = 0x21401514;
    public static final int ID_DMS_FACE_CAM_DISTRACTION_DETECT = 0x21401515;
    public static final int ID_DMS_FACE_CAM_DRINKING_SWITCH_SET = 0x21401516;
    public static final int ID_DMS_FACE_CAM_DRINKING_SWITCH_STATUS = 0x21401517;
    public static final int ID_DMS_FACE_CAM_DRINKING_DETECT = 0x21401518;
    public static final int ID_DMS_FACE_CAM_LONG_TIME_DRIVING_SWITCH_SET = 0x21401519;
    public static final int ID_DMS_FACE_CAM_LONG_TIME_DRIVING_SWITCH_STATUS = 0x2140151A;
    public static final int ID_DMS_FACE_CAM_LONG_TIME_DRIVING_DETECT = 0x2140151B;
    public static final int ID_DMS_FACE_CAM_FACE_ID_LOGIN = 0x2140151C;
    public static final int ID_DMS_FACE_CAM_FACE_DETECTION = 0x2140151D;
    public static final int ID_DMS_FACE_CAM_FACE_ID_RECORD = 0x2140151E;
    public static final int ID_DMS_FACE_CAM_FACE_ID_RECORD_RESP = 0x2140151F;
    public static final int ID_DMS_FACE_CAM_FACE_ID_CLOSE_RESP = 0x21401525;
    public static final int ID_DMS_FACE_CAM_FACE_ID_CONFIRM = 0x21401520;
    public static final int ID_DMS_FACE_CAM_FACE_ID_CANCEL = 0x21401521;
    public static final int ID_DMS_FACE_CAM_RETURN_IVI = 0x21401526;


    public static final int ID_DMS_INT_CAM_LIVE_MONITOR_SWITCH_SET = 0x21401527;
    public static final int ID_DMS_INT_CAM_LIVE_MONITOR_SWITCH_STATUS = 0x21401528;
    public static final int ID_DMS_INT_CAM_SELFIE_SWITCH_SET = 0x21401529;
    public static final int ID_DMS_INT_CAM_SELFIE_SWITCH_STATUS = 0x2140152A;
    public static final int ID_DMS_INT_CAM_SELFIE_RESP_DETECT = 0x2140152B;
    public static final int ID_DMS_INT_CAM_REAR_CHILD_SWITCH_SET = 0x2140152C;
    public static final int ID_DMS_INT_CAM_REAR_CHILD_SWITCH_STATUS = 0x2140152D;
    public static final int ID_DMS_INT_CAM_REAR_CHILD_DETECT = 0x2140152E;
    public static final int ID_DMS_INT_CAM_LIVE_SWITCH_SET = 0x2140152F;
    public static final int ID_DMS_INT_CAM_LIVE_SWITCH_STATUS = 0x21401530;
    public static final int ID_DMS_INT_CAM_LIVE_DETECT = 0x21401531;
    public static final int ID_DMS_INT_CAM_REMNANT_SWITCH_SET = 0x21401532;
    public static final int ID_DMS_INT_CAM_REMNANT_SWITCH_STATUS = 0x21401533;
    public static final int ID_DMS_INT_CAM_REMNANT_DETECT = 0x21401534;
    public static final int ID_DMS_INT_CAM_REAR_CHILD_MONITOR = 0x21401535;
    public static final int ID_DMS_INT_CAM_SELFIE_MODE_SWITCH_SET = 0x2140153B;
    public static final int ID_DMS_INT_CAM_SELFIE_MODE_SWITCH_STATUS = 0x2140153C;
    public static final int ID_DMS_INT_CAM_RESET = 0x21401536;
    public static final int ID_DMS_INT_CAM_RESET_STATUS = 0x21401537;
    public static final int ID_DMS_INT_DEFAULT_SET = 0x21401538;
    public static final int ID_DMS_INT_DEFAULT_SET_STATUS = 0x21401539;

    @IntDef({
    ID_DMS_F_SYSTEM,
    ID_DMS_ST_DTCFLAG,
    ID_DMS_FACE_CAM_SWITCH_SET,
    ID_DMS_FACE_CAM_SWITCH_STATUS,
    ID_DMS_FACE_CAM_EYECLOSE_SWITCH_SET,
    ID_DMS_FACE_CAM_EYECLOSE_SWITCH_STATUS,
    ID_DMS_FACE_CAM_EYECLOSE_DETECT,
    ID_DMS_FACE_CAM_YAWN_SWITCH_SET,
    ID_DMS_FACE_CAM_YAWN_SWITCH_STATUS,
    ID_DMS_FACE_CAM_YAWN_DETECT,
    ID_DMS_FACE_CAM_CALL_SWITCH_SET,
    ID_DMS_FACE_CAM_CALL_SWITCH_STATUS,
    ID_DMS_FACE_CAM_CALL_DETECT,
    ID_DMS_FACE_CAM_SMOKING_SWITCH_SET,
    ID_DMS_FACE_CAM_SMOKING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_SMOKING_DETECT,
    ID_DMS_FACE_CAM_NODALARM_SWITCH_SET,
    ID_DMS_FACE_CAM_NODALARM_SWITCH_STATUS,
    ID_DMS_FACE_CAM_NODALARM_DETECT,
    ID_DMS_FACE_CAM_DISTRACTION_SWITCH_SET,
    ID_DMS_FACE_CAM_DISTRACTION_SWITCH_STATUS,
    ID_DMS_FACE_CAM_DISTRACTION_DETECT,
    ID_DMS_FACE_CAM_DRINKING_SWITCH_SET,
    ID_DMS_FACE_CAM_DRINKING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_DRINKING_DETECT,
    ID_DMS_FACE_CAM_LONG_TIME_DRIVING_SWITCH_SET,
    ID_DMS_FACE_CAM_LONG_TIME_DRIVING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_LONG_TIME_DRIVING_DETECT,
    ID_DMS_FACE_CAM_FACE_ID_LOGIN,
    ID_DMS_FACE_CAM_FACE_DETECTION,
    ID_DMS_FACE_CAM_FACE_ID_RECORD,
    ID_DMS_FACE_CAM_FACE_ID_RECORD_RESP,
    ID_DMS_FACE_CAM_FACE_ID_CLOSE_RESP,
    ID_DMS_FACE_CAM_FACE_ID_CONFIRM,
    ID_DMS_FACE_CAM_FACE_ID_CANCEL,
    ID_DMS_FACE_CAM_RETURN_IVI,

    ID_DMS_INT_CAM_LIVE_MONITOR_SWITCH_SET,
    ID_DMS_INT_CAM_LIVE_MONITOR_SWITCH_STATUS,
    ID_DMS_INT_CAM_SELFIE_SWITCH_SET,
    ID_DMS_INT_CAM_SELFIE_SWITCH_STATUS,
    ID_DMS_INT_CAM_SELFIE_RESP_DETECT,
    ID_DMS_INT_CAM_REAR_CHILD_SWITCH_SET,
    ID_DMS_INT_CAM_REAR_CHILD_SWITCH_STATUS,
    ID_DMS_INT_CAM_REAR_CHILD_DETECT,
    ID_DMS_INT_CAM_LIVE_SWITCH_SET,
    ID_DMS_INT_CAM_LIVE_SWITCH_STATUS,
    ID_DMS_INT_CAM_LIVE_DETECT,
    ID_DMS_INT_CAM_REMNANT_SWITCH_SET,
    ID_DMS_INT_CAM_REMNANT_SWITCH_STATUS,
    ID_DMS_INT_CAM_REMNANT_DETECT,
    ID_DMS_INT_CAM_REAR_CHILD_MONITOR,
    ID_DMS_INT_CAM_SELFIE_MODE_SWITCH_SET,
    ID_DMS_INT_CAM_SELFIE_MODE_SWITCH_STATUS,
    ID_DMS_INT_CAM_RESET,
    ID_DMS_INT_CAM_RESET_STATUS,
    ID_DMS_INT_DEFAULT_SET,
    ID_DMS_INT_DEFAULT_SET_STATUS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId{}

    private final ArraySet<Integer> mDMSPropertyIds = new ArraySet<>(Arrays.asList(
    ID_DMS_F_SYSTEM,
    ID_DMS_ST_DTCFLAG,
    ID_DMS_FACE_CAM_SWITCH_STATUS,
    ID_DMS_FACE_CAM_EYECLOSE_SWITCH_STATUS,
    ID_DMS_FACE_CAM_EYECLOSE_DETECT,
    ID_DMS_FACE_CAM_YAWN_SWITCH_STATUS,
    ID_DMS_FACE_CAM_YAWN_DETECT,
    ID_DMS_FACE_CAM_CALL_SWITCH_STATUS,
    ID_DMS_FACE_CAM_CALL_DETECT,
    ID_DMS_FACE_CAM_SMOKING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_SMOKING_DETECT,
    ID_DMS_FACE_CAM_NODALARM_SWITCH_STATUS,
    ID_DMS_FACE_CAM_NODALARM_DETECT,
    ID_DMS_FACE_CAM_DISTRACTION_SWITCH_STATUS,
    ID_DMS_FACE_CAM_DISTRACTION_DETECT,
    ID_DMS_FACE_CAM_DRINKING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_DRINKING_DETECT,
    ID_DMS_FACE_CAM_LONG_TIME_DRIVING_SWITCH_STATUS,
    ID_DMS_FACE_CAM_LONG_TIME_DRIVING_DETECT,
    ID_DMS_FACE_CAM_FACE_DETECTION,
    ID_DMS_FACE_CAM_FACE_ID_RECORD_RESP,
    ID_DMS_FACE_CAM_FACE_ID_CLOSE_RESP,

    ID_DMS_INT_CAM_LIVE_MONITOR_SWITCH_STATUS,
    ID_DMS_INT_CAM_SELFIE_SWITCH_STATUS,
    ID_DMS_INT_CAM_SELFIE_RESP_DETECT,
    ID_DMS_INT_CAM_REAR_CHILD_SWITCH_STATUS,
    ID_DMS_INT_CAM_REAR_CHILD_DETECT,
    ID_DMS_INT_CAM_LIVE_SWITCH_STATUS,
    ID_DMS_INT_CAM_LIVE_DETECT,
    ID_DMS_INT_CAM_REMNANT_SWITCH_STATUS,
    ID_DMS_INT_CAM_REMNANT_DETECT,
    ID_DMS_INT_CAM_REAR_CHILD_MONITOR,
    ID_DMS_INT_CAM_SELFIE_MODE_SWITCH_STATUS,
    ID_DMS_INT_CAM_RESET_STATUS,
    ID_DMS_INT_DEFAULT_SET_STATUS));


    public interface CarDMSEventCallback{
        void onChangeEvent(CarPropertyValue value);
        void onErrorEvent(@PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements CarPropertyManager.CarPropertyEventListener{
        private final WeakReference<CarDMSManager> mManager;

        public CarPropertyEventListenerToBase(CarDMSManager manager){
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarDMSManager manager = mManager.get();
            if (manager != null){
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propId, int zone) {
            CarDMSManager manager = mManager.get();
            if (manager != null){
                manager.handleOnErrorEvent(propId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value){
        Collection<CarDMSEventCallback> callbacks;

        synchronized (this){
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()){
            for (CarDMSEventCallback l: callbacks){
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone){
        Collection<CarDMSEventCallback> callbacks;

        synchronized (this){
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()){
            for (CarDMSEventCallback l: callbacks){
                l.onErrorEvent(propertyId, zone);
            }
        }
    }

    public CarDMSManager(IBinder service, Context context, Handler handler){
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }


    /**
     * Implement wrappers for contained CarPropertyManager object
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarDMSEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarPropertyEventListenerToBase(this);
        }
        List<CarPropertyConfig> configs = getPropertyList();
        for (CarPropertyConfig c : configs) {
            // Register each individual propertyId
            mCarPropertyMgr.registerListener(mListenerToBase, c.getPropertyId(), 0);
        }
        mCallbacks.add(callback);
    }

    /**
     * Stop getting property updates for the given callback. If there are multiple registrations for
     * this listener, all listening will be stopped.
     * @param callback
     */
    public synchronized void unregisterCallback(CarDMSEventCallback callback)
            throws CarNotConnectedException {
        mCallbacks.remove(callback);
        List<CarPropertyConfig> configs = getPropertyList();
        for (CarPropertyConfig c : configs) {
            // Register each individual propertyId
            mCarPropertyMgr.unregisterListener(mListenerToBase, c.getPropertyId());
        }
        if (mCallbacks.isEmpty()) {
            mListenerToBase = null;
        }
    }

    /**
     * Get list of properties represented by Car Wireless Manager for this car.
     * @return List of CarPropertyConfig objects available via Car Wireless Manager.
     * @throws CarNotConnectedException if the connection to the car service has been lost.
     */
    public List<CarPropertyConfig> getPropertyList() throws CarNotConnectedException {
        return mCarPropertyMgr.getPropertyList(mDMSPropertyIds);
    }

    /**
     * Check whether a given property is available or disabled based on the cars current state.
     * @return true if the property is AVAILABLE, false otherwise
     */
    public boolean isPropertyAvailable(@PropertyId int propertyId, int area)
            throws CarNotConnectedException {
        return mCarPropertyMgr.isPropertyAvailable(propertyId, area);
    }

    public boolean isPropertyAvailable(@PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.isPropertyAvailable(propertyId, -1);
    }

    /**
     * Get value of boolean property
     * @param propertyId
     * @param area
     * @return value of requested boolean property
     * @throws CarNotConnectedException
     */
    public boolean getBooleanProperty(@PropertyId int propertyId, int area)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getBooleanProperty(propertyId, area);
    }

    /**
     * Get value of float property
     * @param propertyId
     * @param area
     * @return value of requested float property
     * @throws CarNotConnectedException
     */
    /*public float getFloatProperty(@PropertyId int propertyId, int area)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getFloatProperty(propertyId, area);
    }*/

    /**
     * Get value of integer property
     * @param propertyId
     * @param area
     * @return value of requested integer property
     * @throws CarNotConnectedException
     */
    public int getIntProperty(@PropertyId int propertyId, int area)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, area);
    }

    public int getIntProperty(@PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, -1);
    }

    /**
     * Set the value of a boolean property
     * @param propertyId
     * @param area
     * @param val
     * @throws CarNotConnectedException
     */
    public void setBooleanProperty(@PropertyId int propertyId, int area, boolean val)
            throws CarNotConnectedException {
        if (mDMSPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setBooleanProperty(propertyId, area, val);
        }
    }

    public void setBooleanProperty(@PropertyId int propertyId, boolean val)
            throws CarNotConnectedException {
        if (mDMSPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setBooleanProperty(propertyId, -1, val);
        }
    }

    /**
     * Set the value of a float property
     * @param propertyId
     * @param area
     * @param val
     * @throws CarNotConnectedException
     */
    /*public void setFloatProperty(@PropertyId int propertyId, int area, float val)
            throws CarNotConnectedException {
        if (mDMSPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setFloatProperty(propertyId, area, val);
        }
    }*/

    /**
     * Set the value of an integer property
     * @param propertyId
     * @param area
     * @param val
     * @throws CarNotConnectedException
     */
    public void setIntProperty(@PropertyId int propertyId, int area, int val)
            throws CarNotConnectedException {
        if (mDMSPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setIntProperty(propertyId, area, val);
        }
    }

    public void setIntProperty(@PropertyId int propertyId, int val)
            throws CarNotConnectedException {
        if (mDMSPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setIntProperty(propertyId, -1, val);
        }
    }

    @Override
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }
}

package wayos.car.hardware.cabin;

import android.annotation.IntDef;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyConfig;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArraySet;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CarCabinExtendManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarCabinExtendManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarCabinExtendManager.CarCabinExtendEventCallback> mCallbacks = new ArraySet<>();
    private CarCabinExtendManager.CarPropertyEventListenerToBase mListenerToBase = null;

    private static final int DEFAULT_AREA = 0;

    // public static final int ID_AUTO_CLOSE_SUNROOF_SET = 0x21401200;
    // public static final int ID_AUTO_CLOSE_SUNROOF_STATE = 0x21401201;
    // public static final int ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_SET = 0x21401202;
    // public static final int ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_STATE = 0x21401203;
    // public static final int ID_SUNROOF_OPEN_IN_VENT_MODE_MANUAL_REQ = 0x21401204;
    // public static final int ID_SUNROOF_MOVE_BY_PERCENT_REQ = 0x21401205;
    // public static final int ID_SUNROOF_ONE_CLICK_CLOSE_REQ = 0x21401206;
    // public static final int ID_SUNROOF_ONE_CLICK_OPEN_IN_VENT_MODE_REQ = 0x21401207;
    // public static final int ID_SUNROOF_MANUAL_CLOSE_REQ = 0x21401208;
    // public static final int ID_SUNROOF_MANUAL_OPEN_REQ = 0x21401209;
    // public static final int ID_SUNROOF_STATE = 0x2140120A;
    // public static final int ID_SUNROOF_HORIZONTAL_POSITION = 0x2140120B;


    // public static final int ID_SUNSHADE_MANUAL_OPEN_REQ = 0x21401230;
    // public static final int ID_SUNSHADE_MANUAL_CLOSE_REQ = 0x21401231;
    // public static final int ID_SUNSHADE_ONE_CLICK_CLOSE_REQ = 0x21401232;
    // public static final int ID_SUNSHADE_MOVE_BY_PERCENT_REQ = 0x21401233;
    // public static final int ID_SUNSHADE_STATE = 0x21401234;
    // public static final int ID_SUNSHADE_POSITION = 0x21401235;
    // public static final int ID_SUNSHADE_MOVING_STATE = 0x21401236;
    // public static final int ID_SUNSHADE_HORIZONTAL_POSITION = 0x21401237;
    /**
     * Seat fore/aft position
     * Sets the seat position forward (closer to steering wheel) and backwards.
     * Max value indicates closest to wheel, min value indicates most rearward
     * position.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     * @AreadId  VehicleArea:LEAT
     */
    public static final int ID_SEAT_FORE_AFT_POS = 0x15400B85;
    /**
     * Seat height position
     * Sets the seat height.
     * Max value indicates highest position.
     * Min value indicates lowest position.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     * @AreadId  VehicleArea:LEAT
     */
    public static final int ID_SEAT_HEIGHT_POS = 0x15400B8B;
    /**
     * Headrest angle position
     * Sets the angle of the headrest.
     * Max value indicates most upright angle.
     * Min value indicates shallowest headrest angle.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     * @AreadId  VehicleArea:LEAT
     */
    public static final int ID_SEAT_HEADREST_ANGLE_POS = 0x15400B97;
    /**
     * 4G module reset request
     * @change_mode ON_CHANGE
     * @access WRITE
     */
    public static final int ID_TBOX_MODULE_RESET_REQ = 0x21200600;
    /**
     * 4G module reset respond
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_TBOX_MODULE_RESET_RESP = 0x21400601;
    /**
     * RKE Trunk Unlock Command
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_PKE_TRUNE_LOCK_SWITCH = 0x21401625;
    /**
     * Tail door switch
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_DOOR_SWITCH_STS = 0x21401635;
    /**
     * Window Position
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     * @AreadId  VehicleArea:WINDOW
     */
    public static final int ID_WINDOW_POS = 0x13400BC0;
    /**
     * Door move
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     * @AreadId  VehicleArea:DOOR
     */
    public static final int ID_DOOR_MOVE = 0x16400B01;
    /**
     * front hood status.
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_FRONT_HOOD_STATE = 0x2620048B;
    /**
     * Acoustic vehicle sound type
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_AVAS_SOUND_TYPE_STATE = 0x21401311;
    /**
     * Acoustic vehicle alerting low speed mute state
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_AVAS_LOW_SPEED_MUTE_STATE = 0x2140130F;
    /**
     * Acoustic vehicle sound type set
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_AVAS_SOUND_TYPE_SET = 0x21401310;
    /**
     * Remote unlock mode
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_REMOTE_UNLOCK_MODE_STATE = 0x21401301;
    /**
     * RCTA Arc Warning Requirement
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_RCTA_ARC_WARNING_REQ = 0x21401604;
    /**
     * Rescue Mode Set
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_RESCUE_MODE_SET = 0x21401619;
    /**
     * Set auto folding mirror switch
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_AUTO_FOLD_MIRROR_SWITCH_SET = 0x21401302;
    /**
     * Auto folding mirror switch
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_AUTO_FOLD_MIRROR_SWITCH_STATE = 0x21401303;
    /**
     * Kick sensor switch set
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_KICK_SENSOR_SWITCH_SET = 0x2140130B;
    /**
     * Kick sensor switch state
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_KICK_SENSOR_SWITCH_STATE = 0x2140130C;
    /**
     * remainder range
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE = 0x21601403;
    /**
     * *******************************Comfort ID range [0x1300,0x133F] **************************************
     * 
     * Remote unlock mode set
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_REMOTE_UNLOCK_MODE_SET = 0x21401300;
    /**
     * Acoustic vehicle alerting low speed mute set
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_AVAS_LOW_SPEED_MUTE_SET = 0x2140130E;
    ////
    /**
     * Central Lock
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_CENTRAL_LOCK_REQ = 0x21401621;
    /**
     * Trunk(PLG) open 
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_TRUNK_OPEN_REQ = 0x2140130d;
    /**
     * BSR Warning Requirement
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:TURN 
     */
    public static final int ID_ESP_EARNING_REQ = 0x21401605;
    /**
     * BSR Warning Requirement
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_BSR_CHIME_TYPE = 0x21401605;
    /**
     * BSR System machine Status
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_BSR_SYSTEM_MACHINE_STS = 0x21401607;
    /**
     * BSR System Fail Status
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_BSR_SYSTEM_FAILED_STS = 0x21401610;
    /**
     * BSR System Fail Status
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_BSR_SYSTEM_FAILED_IND = 0x21401611;
    /**
     * BSR Sensor Blockage Indication
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_BSP_SENSOR_BLOCKAGE_IND = 0x21401608;
    /**
     * Set wiper to maintenance mode
     * @change_mode VehiclePropertyChangeMode.ON_CHANGE
     * @access VehiclePropertyAccess.READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_WIPER_MAINTENANCE_MODE_REQ = 0x21401306;
    /**
     * Wiper state from BCM
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_WIPER_STATE = 0x21401307;
    // /**
    //  * Set find car low beam remind time
    //  * @change_mode VehiclePropertyChangeMode.ON_CHANGE
    //  * @access VehiclePropertyAccess.READ_WRITE
    //  * @AreaId VehicleArea:GLOBAL
    //  */
    // public static final int ID_FIND_CAR_LBEAM_REMIND_TIME_SET = 0x13400bc0;
    // /**
    //  * Set find car low beam remind time
    //  * @change_mode VehiclePropertyChangeMode.ON_CHANGE
    //  * @access VehiclePropertyAccess.READ_WRITE
    //  * @AreaId VehicleArea:GLOBAL
    //  */
    // public static final int ID_FOLLOW_ME_HOME_TIME_SET = 0x21401104;
    /**
     * IVI_EasyAccessRequest
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_EASY_ACCESS_REQ = 0x21401623;

    /**
     * @hide
     */
    @IntDef({
            // ID_AUTO_CLOSE_SUNROOF_SET,
            // ID_AUTO_CLOSE_SUNROOF_STATE,
            // ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_SET,
            // ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_STATE,
            // ID_SUNROOF_OPEN_IN_VENT_MODE_MANUAL_REQ,
            // ID_SUNROOF_MOVE_BY_PERCENT_REQ,
            // ID_SUNROOF_ONE_CLICK_CLOSE_REQ,
            // ID_SUNROOF_ONE_CLICK_OPEN_IN_VENT_MODE_REQ,
            // ID_SUNROOF_MANUAL_CLOSE_REQ,
            // ID_SUNROOF_MANUAL_OPEN_REQ,
            // ID_SUNROOF_STATE,
            // ID_SUNROOF_HORIZONTAL_POSITION,
            // ID_SUNSHADE_MANUAL_OPEN_REQ,
            // ID_SUNSHADE_MANUAL_CLOSE_REQ,
            // ID_SUNSHADE_ONE_CLICK_CLOSE_REQ,
            // ID_SUNSHADE_MOVE_BY_PERCENT_REQ,
            // ID_SUNSHADE_STATE,
            // ID_SUNSHADE_POSITION,
            // ID_SUNSHADE_MOVING_STATE,
            // ID_SUNSHADE_HORIZONTAL_POSITION,
            ID_AVAS_SOUND_TYPE_SET,
            ID_REMOTE_UNLOCK_MODE_SET,
            ID_SEAT_FORE_AFT_POS,
            ID_SEAT_HEIGHT_POS,
            ID_SEAT_HEADREST_ANGLE_POS,
            ID_TBOX_MODULE_RESET_REQ,
            ID_TBOX_MODULE_RESET_RESP,
            ID_PKE_TRUNE_LOCK_SWITCH,
            ID_DOOR_SWITCH_STS,
            ID_WINDOW_POS,
            ID_DOOR_MOVE,
            ID_FRONT_HOOD_STATE,
            ID_AVAS_SOUND_TYPE_STATE,
            ID_AVAS_LOW_SPEED_MUTE_STATE,
            ID_REMOTE_UNLOCK_MODE_STATE,
            ID_RCTA_ARC_WARNING_REQ,
            ID_RESCUE_MODE_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_STATE,
            ID_KICK_SENSOR_SWITCH_SET,
            ID_KICK_SENSOR_SWITCH_STATE,
            ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE,
            ID_AVAS_LOW_SPEED_MUTE_SET,
            ID_TRUNK_OPEN_REQ,
            ID_ESP_EARNING_REQ,
            ID_BSR_CHIME_TYPE,
            ID_BSR_SYSTEM_MACHINE_STS,
            ID_BSR_SYSTEM_FAILED_STS,
            ID_BSR_SYSTEM_FAILED_IND,
            ID_BSP_SENSOR_BLOCKAGE_IND,
            ID_WIPER_MAINTENANCE_MODE_REQ,
            ID_WIPER_STATE,
            ID_EASY_ACCESS_REQ
            // ID_FIND_CAR_LBEAM_REMIND_TIME_SET,
            // ID_FOLLOW_ME_HOME_TIME_SET
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(new Integer[]{
            // ID_AUTO_CLOSE_SUNROOF_SET,
            // ID_AUTO_CLOSE_SUNROOF_STATE,
            // ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_SET,
            // ID_AUTO_CLOSE_SUNROOF_SPEED_THRESHOLD_STATE,
            // ID_SUNROOF_OPEN_IN_VENT_MODE_MANUAL_REQ,
            // ID_SUNROOF_MOVE_BY_PERCENT_REQ,
            // ID_SUNROOF_ONE_CLICK_CLOSE_REQ,
            // ID_SUNROOF_ONE_CLICK_OPEN_IN_VENT_MODE_REQ,
            // ID_SUNROOF_MANUAL_CLOSE_REQ,
            // ID_SUNROOF_MANUAL_OPEN_REQ,
            // ID_SUNROOF_STATE,
            // ID_SUNROOF_HORIZONTAL_POSITION,
            // ID_SUNSHADE_MANUAL_OPEN_REQ,
            // ID_SUNSHADE_MANUAL_CLOSE_REQ,
            // ID_SUNSHADE_ONE_CLICK_CLOSE_REQ,
            // ID_SUNSHADE_MOVE_BY_PERCENT_REQ,
            // ID_SUNSHADE_STATE,
            // ID_SUNSHADE_POSITION,
            // ID_SUNSHADE_MOVING_STATE,
            // ID_SUNSHADE_HORIZONTAL_POSITION,
            ID_AVAS_SOUND_TYPE_SET,
            ID_REMOTE_UNLOCK_MODE_SET,
            ID_SEAT_FORE_AFT_POS,
            ID_SEAT_HEIGHT_POS,
            ID_SEAT_HEADREST_ANGLE_POS,
            ID_TBOX_MODULE_RESET_REQ,
            ID_TBOX_MODULE_RESET_RESP,
            ID_PKE_TRUNE_LOCK_SWITCH,
            ID_DOOR_SWITCH_STS,
            ID_WINDOW_POS,
            ID_DOOR_MOVE,
            ID_FRONT_HOOD_STATE,
            ID_AVAS_SOUND_TYPE_STATE,
            ID_AVAS_LOW_SPEED_MUTE_STATE,
            ID_REMOTE_UNLOCK_MODE_STATE,
            ID_RCTA_ARC_WARNING_REQ,
            ID_RESCUE_MODE_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_STATE,
            ID_KICK_SENSOR_SWITCH_SET,
            ID_KICK_SENSOR_SWITCH_STATE,
            ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE,
            ID_AVAS_LOW_SPEED_MUTE_SET,
            ID_TRUNK_OPEN_REQ,
            ID_ESP_EARNING_REQ,
            ID_BSR_CHIME_TYPE,
            ID_BSR_SYSTEM_MACHINE_STS,
            ID_BSR_SYSTEM_FAILED_STS,
            ID_BSR_SYSTEM_FAILED_IND,
            ID_BSP_SENSOR_BLOCKAGE_IND,
            ID_WIPER_MAINTENANCE_MODE_REQ,
            ID_WIPER_STATE,
            ID_EASY_ACCESS_REQ
            // ID_FIND_CAR_LBEAM_REMIND_TIME_SET,
            // ID_FOLLOW_ME_HOME_TIME_SET
    }));

    /**
     * /**
     * Application registers {@link wayos.car.hardware.carbin.CarCabinExtendManager.CarCabinExtendEventCallback} object to receive updates and changes to
     * subscribed Car Battery properties.
     */
    public interface CarCabinExtendEventCallback {
        /**
         * Called when a property is updated
         *
         * @param value Property that has been updated.
         */
        void onChangeEvent(CarPropertyValue value);

        /**
         * Called when an error is detected with a property
         *
         * @param propertyId
         * @param zone
         */
        void onErrorEvent(@CarCabinExtendManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarCabinExtendManager> mManager;

        public CarPropertyEventListenerToBase(CarCabinExtendManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarCabinExtendManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarCabinExtendManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarCabinExtendManager.CarCabinExtendEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarCabinExtendManager.CarCabinExtendEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarCabinExtendManager.CarCabinExtendEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarCabinExtendManager.CarCabinExtendEventCallback l : callbacks) {
                l.onErrorEvent(propertyId, zone);
            }
        }
    }

    /**
     * Get an instance of the CarBatteryManager.
     * <p>
     * Should not be obtained directly by clients, use {@link Car#getCarManager(String)} instead.
     *
     * @param service
     * @param context
     * @param handler
     * @hide
     */
    public CarCabinExtendManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarCabinExtendManager.CarCabinExtendEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarCabinExtendManager.CarPropertyEventListenerToBase(this);
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
     *
     * @param callback
     */
    public synchronized void unregisterCallback(CarCabinExtendManager.CarCabinExtendEventCallback callback) {
        mCallbacks.remove(callback);
        try {
            List<CarPropertyConfig> configs = getPropertyList();
            for (CarPropertyConfig c : configs) {
                // Register each individual propertyId
                mCarPropertyMgr.unregisterListener(mListenerToBase, c.getPropertyId());
            }
        } catch (Exception e) {
            Log.e(TAG, "getPropertyList exception ", e);
        }
        if (mCallbacks.isEmpty()) {
            mCarPropertyMgr.unregisterListener(mListenerToBase);
            mListenerToBase = null;
        }
    }

    /**
     * Get list of properties represented by Car Battery Manager for this car.
     *
     * @return List of CarPropertyConfig objects available via Car Battery Manager.
     * @throws CarNotConnectedException if the connection to the car service has been lost.
     */
    private List<CarPropertyConfig> getPropertyList() throws CarNotConnectedException {
        return mCarPropertyMgr.getPropertyList(mPropertyIds);
    }

    /**
     * Get value of boolean property
     *
     * @param propertyId
     * @return value of requested boolean property
     * @throws CarNotConnectedException
     */
    public boolean getBooleanProperty(@CarCabinExtendManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getBooleanProperty(propertyId, DEFAULT_AREA);
    }

    /**
     * Get value of float property
     *
     * @param propertyId
     * @return value of requested float property
     * @throws CarNotConnectedException
     */
    public float getFloatProperty(@CarCabinExtendManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getFloatProperty(propertyId, DEFAULT_AREA);
    }

    /**
     * Get value of integer property
     *
     * @param propertyId
     * @return value of requested integer property
     * @throws CarNotConnectedException
     */
    public int getIntProperty(@CarCabinExtendManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, DEFAULT_AREA);
    }

    /**
     * Set the value of a boolean property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setBooleanProperty(@CarCabinExtendManager.PropertyId int propertyId, boolean val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setBooleanProperty(propertyId, DEFAULT_AREA, val);
        }
    }

    /**
     * Set the value of a float property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setFloatProperty(@CarCabinExtendManager.PropertyId int propertyId, float val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setFloatProperty(propertyId, DEFAULT_AREA, val);
        }
    }

    /**
     * Set the value of an integer property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setIntProperty(@CarCabinExtendManager.PropertyId int propertyId, int val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setIntProperty(propertyId, DEFAULT_AREA, val);
        }
    }

    /**
     * @hide
     */
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }
}

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

public class CarLightManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarLightManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarLightManager.CarLightEventCallback> mCallbacks = new ArraySet<>();
    private CarLightManager.CarPropertyEventListenerToBase mListenerToBase = null;

    private static final int DEFAULT_AREA = 0;
    /** 
     *Room light on request
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_ROOM_LIGHT_ON_REQ = 0x21401100;
    /**
     * Room light off request
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_ROOM_LIGHT_OFF_REQ = 0x21401101;
    /**
     * Room light on/off relevant to door set request
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_ROOM_LIGHT_RELEVANT_TO_DOOR_SET = 0x21401102;
    /**
     * Room light on/off relevant to door state
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_ROOM_LIGHT_RELEVANT_TO_DOOR_STATE = 0x21401103;
    /**
     * Set high beam follow me home time
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_FOLLOW_ME_HOME_TIME_SET = 0x21401104;
    /**
     * High beam follow me home time
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_FOLLOW_ME_HOME_TIME_STATE = 0x21401105;
    /**
     * Set find car low beam remind time
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_FIND_CAR_LBEAM_REMIND_TIME_SET = 0x21401106;
    /**
     * Find car low beam remind time
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_FIND_CAR_LBEAM_REMIND_TIME_STATE = 0x21401107;
    /**
     * Set high beam leave home time
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LEAVE_HOME_TIME_SET = 0x21401108;
    /**
     * High beam leave home time
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_LEAVE_HOME_TIME_STATE = 0x21401109;
    /**
     * Set high beam lights position
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_HIGH_BEAM_LIGHTS_POSITION_SET = 0x2140110A;
    /**
     * High beam lights position
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_HIGH_BEAM_LIGHTS_POSITION_STATE = 0x2140110B;
    /**
     * Set the courtesy lamp switch
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_COURTESY_LAMP_SWITCH_SET = 0x2140110C;
    /**
     * Courtesy lamp switch state
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_COURTESY_LAMP_SWITCH_STATE = 0x2140110D;
    /**
     * Set the ambient light switch
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_AMBIENT_LAMP_SWITCH_SET = 0x2140110E;
    /**
     * The ambient light switch state
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_AMBIENT_LAMP_SWITCH_STATE = 0x2140110F;
    /**
     * Set daytime running light
     * The setting that the user wants.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_DAY_TIME_RUNNING_LIGHT_SWITCH_SET = 0x21401110;
    /**
     * Daytime running light state
     * Return the current status of daytime running light
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_DAY_TIME_RUNNING_LIGHT_SWITCH_STATE = 0x21401111;
    /**
     * Set (IHBC) Intelligent high beam control switch
     * The setting that the user wants.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_INTELLIGENT_HIGH_BEAM_CONTROL_SET = 0x21401112;
    /**
     * (IHBC) Intelligent high beam control state
     * Return the current status of intelligent high beam control
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_INTELLIGENT_HIGH_BEAM_CONTROL_STATE = 0x21401113;
    /**
     * Set vehicle back light
     * The setting that the user wants.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_VEHICLE_BACK_LIGHT_ADJUST = 0x21401114;
    /**
     * LED Error Indication
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_LED_ERR_IND = 0x21401612;
    /**
     * Lamp language lock greeting button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_LOCK_ENA = 0x21401628;
    /**
     * Lamp language Short greeting button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_SHORT_GREET = 0x21401629;
    /**
     * Lamp language long greeting button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_LONG_GREET = 0x21401630;
    /**
     * Lamp language unlock button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_UNLOCK_ENA = 0x21401631;
    /**
     * Lamp language search car button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_SEARCH_CAR_ENA = 0x21401632;
    /**
     * Lamp language greeting button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_GREET_ENA = 0x21401633;
    /**
     * Lamp language charge button
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_LAMPSIG_CHARGE_ENA = 0x21401634;
    /**
     * State of the vehicles turn signals
     * @change_mode ON_CHANGE
     * @access READ
     * @AreadId  VehicleArea:LIGHT
     */
    public static final int ID_TURN_SIGNAL_STATE = 0x11400408;
    /**
     * High beam lights state
     * Return the current state of high beam lights.
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_HIGH_BEAM_LIGHTS_STATE = 0x11400E01;
    /**
     * emergrncy lamp state.
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_VEHICLE_EMERGRNCY_LAMP_STATE = 0x21201115;
    /**
     * emergrncy signal switch.
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_VEHICLE_EMERGRNCY_SIGNAL_SWITH = 0x21201116;
    /**
     * Parking brake state.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_PARKING_BRAKE_ON = 0x11200402;
    /**
     * High beam light switch
     * The setting that the user wants.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    public static final int ID_HIGH_BEAM_LIGHTS_SWITCH = 0x11400E11;
    /**
     * low beam lights state 
     * Return the current state of low beam lights.
     * @change_mode ON_CHANGE
     * @access READ
     */
    public static final int ID_LOW_BEAM_LIGHTS_STATE = 0x11400E14;
    /**
     * @hide
     */
    @IntDef({
            ID_ROOM_LIGHT_ON_REQ,
            ID_ROOM_LIGHT_OFF_REQ,
            ID_ROOM_LIGHT_RELEVANT_TO_DOOR_SET,
            ID_ROOM_LIGHT_RELEVANT_TO_DOOR_STATE,
            ID_FOLLOW_ME_HOME_TIME_SET,
            ID_FOLLOW_ME_HOME_TIME_STATE,
            ID_FIND_CAR_LBEAM_REMIND_TIME_SET,
            ID_FIND_CAR_LBEAM_REMIND_TIME_STATE,
            ID_LEAVE_HOME_TIME_SET,
            ID_LEAVE_HOME_TIME_STATE,
            ID_HIGH_BEAM_LIGHTS_POSITION_SET,
            ID_HIGH_BEAM_LIGHTS_POSITION_STATE,
            ID_COURTESY_LAMP_SWITCH_SET,
            ID_COURTESY_LAMP_SWITCH_STATE,
            ID_AMBIENT_LAMP_SWITCH_SET,
            ID_AMBIENT_LAMP_SWITCH_STATE,
            ID_DAY_TIME_RUNNING_LIGHT_SWITCH_SET,
            ID_DAY_TIME_RUNNING_LIGHT_SWITCH_STATE,
            ID_INTELLIGENT_HIGH_BEAM_CONTROL_SET,
            ID_INTELLIGENT_HIGH_BEAM_CONTROL_STATE,
            ID_VEHICLE_BACK_LIGHT_ADJUST,
            ID_LED_ERR_IND,
            ID_LAMPSIG_LOCK_ENA,
            ID_LAMPSIG_SHORT_GREET,
            ID_LAMPSIG_LONG_GREET,
            ID_LAMPSIG_UNLOCK_ENA,
            ID_LAMPSIG_SEARCH_CAR_ENA,
            ID_LAMPSIG_GREET_ENA,
            ID_LAMPSIG_CHARGE_ENA,
            ID_TURN_SIGNAL_STATE,
            ID_HIGH_BEAM_LIGHTS_STATE,
            ID_VEHICLE_EMERGRNCY_LAMP_STATE,
            ID_VEHICLE_EMERGRNCY_SIGNAL_SWITH,
            ID_PARKING_BRAKE_ON,
            ID_HIGH_BEAM_LIGHTS_SWITCH,
            ID_LOW_BEAM_LIGHTS_STATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(new Integer[]{
            ID_ROOM_LIGHT_ON_REQ,
            ID_ROOM_LIGHT_OFF_REQ,
            ID_ROOM_LIGHT_RELEVANT_TO_DOOR_SET,
            ID_ROOM_LIGHT_RELEVANT_TO_DOOR_STATE,
            ID_FOLLOW_ME_HOME_TIME_SET,
            ID_FOLLOW_ME_HOME_TIME_STATE,
            ID_FIND_CAR_LBEAM_REMIND_TIME_SET,
            ID_FIND_CAR_LBEAM_REMIND_TIME_STATE,
            ID_LEAVE_HOME_TIME_SET,
            ID_LEAVE_HOME_TIME_STATE,
            ID_HIGH_BEAM_LIGHTS_POSITION_SET,
            ID_HIGH_BEAM_LIGHTS_POSITION_STATE,
            ID_COURTESY_LAMP_SWITCH_SET,
            ID_COURTESY_LAMP_SWITCH_STATE,
            ID_AMBIENT_LAMP_SWITCH_SET,
            ID_AMBIENT_LAMP_SWITCH_STATE,
            ID_DAY_TIME_RUNNING_LIGHT_SWITCH_SET,
            ID_DAY_TIME_RUNNING_LIGHT_SWITCH_STATE,
            ID_INTELLIGENT_HIGH_BEAM_CONTROL_SET,
            ID_INTELLIGENT_HIGH_BEAM_CONTROL_STATE,
            ID_VEHICLE_BACK_LIGHT_ADJUST,
            ID_LED_ERR_IND,
            ID_LAMPSIG_LOCK_ENA,
            ID_LAMPSIG_SHORT_GREET,
            ID_LAMPSIG_LONG_GREET,
            ID_LAMPSIG_UNLOCK_ENA,
            ID_LAMPSIG_SEARCH_CAR_ENA,
            ID_LAMPSIG_GREET_ENA,
            ID_LAMPSIG_CHARGE_ENA,
            ID_TURN_SIGNAL_STATE,
            ID_HIGH_BEAM_LIGHTS_STATE,
            ID_VEHICLE_EMERGRNCY_LAMP_STATE,
            ID_VEHICLE_EMERGRNCY_SIGNAL_SWITH,
            ID_PARKING_BRAKE_ON,
            ID_HIGH_BEAM_LIGHTS_SWITCH,
            ID_LOW_BEAM_LIGHTS_STATE
    }));

    /**
     * /**
     * Application registers {@link wayos.car.hardware.cabin.CarLightManager.CarLightEventCallback} object to receive updates and changes to
     * subscribed Car Battery properties.
     */
    public interface CarLightEventCallback {
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
        void onErrorEvent(@CarLightManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarLightManager> mManager;

        public CarPropertyEventListenerToBase(CarLightManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarLightManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarLightManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarLightManager.CarLightEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarLightManager.CarLightEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarLightManager.CarLightEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarLightManager.CarLightEventCallback l : callbacks) {
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
    public CarLightManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarLightManager.CarLightEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarLightManager.CarPropertyEventListenerToBase(this);
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
    public synchronized void unregisterCallback(CarLightManager.CarLightEventCallback callback) {
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
    public boolean getBooleanProperty(@CarLightManager.PropertyId int propertyId)
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
    public float getFloatProperty(@CarLightManager.PropertyId int propertyId)
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
    public int getIntProperty(@CarLightManager.PropertyId int propertyId)
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
    public void setBooleanProperty(@CarLightManager.PropertyId int propertyId, boolean val)
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
    public void setFloatProperty(@CarLightManager.PropertyId int propertyId, float val)
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
    public void setIntProperty(@CarLightManager.PropertyId int propertyId, int val)
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

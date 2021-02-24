package wayos.car.hardware.energy;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.annotation.IntDef;
import android.util.ArraySet;
import android.util.Log;

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

public class CarBatteryManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarBatteryManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarBatteryManager.CarBatteryEventCallback> mCallbacks = new ArraySet<>();
    private CarBatteryManager.CarPropertyEventListenerToBase mListenerToBase = null;

    public static final int ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET_PROPERTY_VALUE_CANCEL = 0x2;

    /**
     * remainder range
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    public static final int ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE = 0x21601403;

    //public static final int ID_ENERGY_RECOVERY = 0x21401404;
    // public static final int ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET = 0x21501405;
    // public static final int ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_STATE = 0x21401406;

//    public static final int ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_SET = 0x21401407;

    private static final int DEFAULT_AREAR = 0;
    private  static final int ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SCALE = 10000;
    /**
     * Request to open e lock
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_ELOCK_OPEN_REQ = 0x21401350;
    /**
     * scheduled charge duration state
     * [0,1440] step=1
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_STATE = 0x21401408;
    /**
     * Set current energy recovery mode
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_BRAKING_ENERGY_RECOVERY_MODE_SET = 0x21401355;
    /**
     * Current energy recovery mode
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE = 0x21401356;
    /**
     * BATTERY PACK voltage.
     * [0,500]  step=0.1 offset=0
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_ENERGY_BATTERY_PACK_VOLTAGE_STATE = 0x21601401;
    /**
     * BATTERY PACK current.
     * [-700,700]  step=0.1 offset=-700
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_ENERGY_BATTERY_PACK_CURRENT_STATE = 0x21601400;
    /**
     * BATTERY PACK remaining capacity.
     * [0,100]  step=1 offset=0 unit='%'
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_ENERGY_BATTERY_PACK_SOC_STATE = 0x21401402;
    /**
     * Wireless charge .
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_WIRELESS_CHARGE_SWITCH_SET = 0x21401312;
    /**
     * Wireless charge working status.
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_WIRELESS_CHARGE_CURRENT_STATE = 0x21401314;
    /**
     * Charging Cover Req
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     * @AreaId VehicleArea:GLOBAL
     */
    private static final int ID_CHARGING_COVER_REQ = 0x21401613;
    
    /**
     * @hide
     */
    @IntDef({
            ID_ENERGY_BATTERY_PACK_CURRENT_STATE,
            ID_ENERGY_BATTERY_PACK_VOLTAGE_STATE,
            ID_ENERGY_BATTERY_PACK_SOC_STATE,
            ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE,
            //ID_ENERGY_RECOVERY,
            // ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET,
            // ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_STATE,
            ID_ELOCK_OPEN_REQ,
            ID_BRAKING_ENERGY_RECOVERY_MODE_SET,
            ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE,
            ID_WIRELESS_CHARGE_SWITCH_SET,
            ID_WIRELESS_CHARGE_CURRENT_STATE,
            ID_CHARGING_COVER_REQ,
            //ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_SET,
            ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_STATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(
            ID_ENERGY_BATTERY_PACK_CURRENT_STATE,
            ID_ENERGY_BATTERY_PACK_VOLTAGE_STATE,
            ID_ENERGY_BATTERY_PACK_SOC_STATE,
            ID_ENERGY_BATTERY_REMAINDER_RANGE_STATE,
            ID_ELOCK_OPEN_REQ,
            //ID_ENERGY_RECOVERY,
            //ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET,
            // ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_STATE,
            ID_BRAKING_ENERGY_RECOVERY_MODE_SET,
            ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE,
            ID_WIRELESS_CHARGE_SWITCH_SET,
            ID_WIRELESS_CHARGE_CURRENT_STATE,
            ID_CHARGING_COVER_REQ,
            //ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_SET
            ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_STATE
    ));

    private final ArraySet<Integer> mWritePropertyIds = new ArraySet<>(Arrays.asList(
            //ID_ENERGY_RECOVERY,
            // ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET
            //ID_ENERGY_BATTERY_SCHEDULED_CHARGE_DURATION_SET
    ));

    /**
     * /**
     * Application registers {@link android.car.hardware.energy.CarBatteryManager.CarBatteryEventCallback} object to receive updates and changes to
     * subscribed Car Battery properties.
     */
    public interface CarBatteryEventCallback {
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
        void onErrorEvent(@CarBatteryManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarBatteryManager> mManager;

        public CarPropertyEventListenerToBase(CarBatteryManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarBatteryManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarBatteryManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarBatteryManager.CarBatteryEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarBatteryManager.CarBatteryEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarBatteryManager.CarBatteryEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarBatteryManager.CarBatteryEventCallback l : callbacks) {
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
    public CarBatteryManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarBatteryManager.CarBatteryEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarBatteryManager.CarPropertyEventListenerToBase(this);
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
    public synchronized void unregisterCallback(CarBatteryManager.CarBatteryEventCallback callback) {
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
    public boolean getBooleanProperty(@CarBatteryManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getBooleanProperty(propertyId, DEFAULT_AREAR);
    }

    /**
     * Get value of float property
     *
     * @param propertyId
     * @return value of requested float property
     * @throws CarNotConnectedException
     */
    public float getFloatProperty(@CarBatteryManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getFloatProperty(propertyId, DEFAULT_AREAR);
    }

    /**
     * Get value of integer property
     *
     * @param propertyId
     * @return value of requested integer property
     * @throws CarNotConnectedException
     */
    public int getIntProperty(@CarBatteryManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, DEFAULT_AREAR);
    }

    /**
     * Get value of long property
     *
     * @param propertyId
     * @return value of requested integer property
     * @throws CarNotConnectedException
     */
    public long getLongProperty(@CarBatteryManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getLongProperty(propertyId, DEFAULT_AREAR);
    }

    /**
     * Set the value of a boolean property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setBooleanProperty(@CarBatteryManager.PropertyId int propertyId, boolean val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId) || mWritePropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setBooleanProperty(propertyId, DEFAULT_AREAR, val);
        }
    }

    /**
     * Set the value of a float property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setFloatProperty(@CarBatteryManager.PropertyId int propertyId, float val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId) || mWritePropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setFloatProperty(propertyId, DEFAULT_AREAR, val);
        }
    }

    /**
     * Set the value of an integer property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setIntProperty(@CarBatteryManager.PropertyId int propertyId, int val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId) || mWritePropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setIntProperty(propertyId, DEFAULT_AREAR, val);
        }
    }

    /**
     * Set the value of an integer property
     *
     * @param propertyId
     * @param val
     * @throws CarNotConnectedException
     */
    public void setLongProperty(@CarBatteryManager.PropertyId int propertyId, long val)
            throws CarNotConnectedException {
        if (mPropertyIds.contains(propertyId) || mWritePropertyIds.contains(propertyId)) {
            mCarPropertyMgr.setLongProperty(propertyId, DEFAULT_AREAR, val);
        } else {
            Log.d(TAG, "setLongProperty invalid PropertyId:" + propertyId);
        }
    }

    /**
     * @hide
     */
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }

    // /**
    //  * Set Scheduled Charge Time
    //  *
    //  * @param timeStamp
    //  * @param duration
    //  * @throws CarNotConnectedException
    //  */
    // public void setScheduledChargeTime(long timeStamp, int duration)
    //         throws CarNotConnectedException {
    //         mCarPropertyMgr.setLongProperty(ID_ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SET,
    //                 DEFAULT_AREAR, timeStamp*ENERGY_BATTERY_SCHEDULED_CHARGE_TIME_SCALE + duration);
    // }
}

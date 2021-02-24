package wayos.car.hardware.sensor;

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

public class CarAdasManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarAdasManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarAdasManager.CarAdasEventCallback> mCallbacks = new ArraySet<>();
    private CarAdasManager.CarPropertyEventListenerToBase mListenerToBase = null;
    private static final int DEFAULT_AREA = 0;


    public static final int ID_TSR_ACTIVE_SET = 0x21401340;
    public static final int ID_TSR_STATE = 0x21401341;
    public static final int ID_TSR_ERROR_STATE = 0x21401342;
    public static final int ID_FCW_ACTIVE_SET = 0x21401343;
    public static final int ID_FCW_ACTIVE_STATE = 0x21401344;
    public static final int ID_FCW_SENSITIVITY_SET = 0x21401345;
    public static final int ID_FCW_SENSITIVITY_STATE = 0x21401346;
    public static final int ID_LDW_ACTIVE_SET = 0x21401347;
    public static final int ID_LDW_ACTIVE_STATE = 0x21401348;
    public static final int ID_LDW_SENSITIVITY_SET = 0x21401349;
    public static final int ID_LDW_SENSITIVITY_STATE = 0x2140134A;
    public static final int ID_AEB_ACTIVE_SET = 0x2140134B;
    public static final int ID_AEB_STATE = 0x2140134C;
    public static final int ID_AEB_SWITCH_STATE = 0x2140134D;
    public static final int ID_PASSENGER_AIR_BAG_SET = 0x2140134E;
    public static final int ID_PASSENGER_AIR_BAG_STATE = 0x2140134F;
    public static final int ID_ELOCK_OPEN_REQ = 0x21401350;
    public static final int ID_EPS_MODE_SET = 0x21401351;
    public static final int ID_EPS_MODE_SET_FEEDBACK = 0x21401352;
    public static final int ID_EPS_CURRENT_MODE = 0x21401353;
    public static final int ID_EPS_FAULT_STATE = 0x21401354;
    public static final int ID_BRAKING_ENERGY_RECOVERY_MODE_SET = 0x21401355;
    public static final int ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE = 0x21401356;
    public static final int ID_DRIVING_MODE_SET = 0x21401357;
    public static final int ID_DRIVING_MODE_STATE = 0x21401358;
    public static final int ID_LKA_ACTIVE_SET = 0x21401359;
    public static final int ID_LKA_STATE = 0x2140135A;
    public static final int ID_LKA_UPDATE_STATE = 0x2140135B;
    public static final int ID_RCW_LIGHT_SET_REQ = 0x2140135C;
    public static final int ID_DOW_WARNING_SET = 0x2140135D;
    public static final int ID_DOW_WARNING_STATE = 0x2140135E;
    public static final int ID_ESP_SWITCH_SET = 0x2140135F;
    public static final int ID_ESP_SWITCH_STATE = 0x21401360;
    public static final int ID_HDC_SWITCH_SET = 0x21401361;
    public static final int ID_HDC_SWITCH_STATE = 0x21401362;
    public static final int ID_HDC_CURRENT_STATE = 0x21401363;
    public static final int ID_RADAR_SOUND_SWITCH_SET = 0x21401364;
    public static final int ID_RADAR_SOUND_SWITCH_STATE = 0x21401365;
    public static final int ID_BSD_WARNING_SET = 0x21401366;
    public static final int ID_BSD_WARNING_STATE = 0x21401367;
    /**
     * @hide
     */
    @IntDef({
            ID_TSR_ACTIVE_SET,
            ID_TSR_STATE,
            ID_TSR_ERROR_STATE,
            ID_FCW_ACTIVE_SET,
            ID_FCW_ACTIVE_STATE,
            ID_FCW_SENSITIVITY_SET,
            ID_FCW_SENSITIVITY_STATE,
            ID_LDW_ACTIVE_SET,
            ID_LDW_ACTIVE_STATE,
            ID_LDW_SENSITIVITY_SET,
            ID_LDW_SENSITIVITY_STATE,
            ID_AEB_ACTIVE_SET,
            ID_AEB_STATE,
            ID_AEB_SWITCH_STATE,
            ID_PASSENGER_AIR_BAG_SET,
            ID_PASSENGER_AIR_BAG_STATE,
            ID_ELOCK_OPEN_REQ,
            ID_EPS_MODE_SET,
            ID_EPS_MODE_SET_FEEDBACK,
            ID_EPS_CURRENT_MODE,
            ID_EPS_FAULT_STATE,
            ID_BRAKING_ENERGY_RECOVERY_MODE_SET,
            ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE,
            ID_DRIVING_MODE_SET,
            ID_DRIVING_MODE_STATE, 
            ID_LKA_ACTIVE_SET,
            ID_LKA_STATE,
            ID_LKA_UPDATE_STATE,
            ID_RCW_LIGHT_SET_REQ,
            ID_DOW_WARNING_SET,
            ID_DOW_WARNING_STATE,
            ID_ESP_SWITCH_SET,
            ID_ESP_SWITCH_STATE,
            ID_HDC_SWITCH_SET,
            ID_HDC_SWITCH_STATE,
            ID_HDC_CURRENT_STATE,
            ID_RADAR_SOUND_SWITCH_SET,
            ID_RADAR_SOUND_SWITCH_STATE,
            ID_BSD_WARNING_SET,
            ID_BSD_WARNING_STATE, 
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(new Integer[]{
            ID_TSR_ACTIVE_SET,
            ID_TSR_STATE,
            ID_TSR_ERROR_STATE,
            ID_FCW_ACTIVE_SET,
            ID_FCW_ACTIVE_STATE,
            ID_FCW_SENSITIVITY_SET,
            ID_FCW_SENSITIVITY_STATE,
            ID_LDW_ACTIVE_SET,
            ID_LDW_ACTIVE_STATE,
            ID_LDW_SENSITIVITY_SET,
            ID_LDW_SENSITIVITY_STATE,
            ID_AEB_ACTIVE_SET,
            ID_AEB_STATE,
            ID_AEB_SWITCH_STATE,
            ID_PASSENGER_AIR_BAG_SET,
            ID_PASSENGER_AIR_BAG_STATE,
            ID_EPS_MODE_SET,
            ID_EPS_MODE_SET_FEEDBACK,
            ID_EPS_CURRENT_MODE,
            ID_EPS_FAULT_STATE,
            ID_BRAKING_ENERGY_RECOVERY_MODE_SET,
            ID_BRAKING_ENERGY_RECOVERY_CURRENT_MODE,
            ID_DRIVING_MODE_SET,
            ID_DRIVING_MODE_STATE, 
            ID_LKA_ACTIVE_SET,
            ID_LKA_STATE,
            ID_LKA_UPDATE_STATE,
            ID_RCW_LIGHT_SET_REQ,
            ID_DOW_WARNING_SET,
            ID_DOW_WARNING_STATE,
            ID_ESP_SWITCH_SET,
            ID_ESP_SWITCH_STATE,
            ID_HDC_SWITCH_SET,
            ID_HDC_SWITCH_STATE,
            ID_HDC_CURRENT_STATE,
            ID_RADAR_SOUND_SWITCH_SET,
            ID_RADAR_SOUND_SWITCH_STATE,
            ID_BSD_WARNING_SET,
            ID_BSD_WARNING_STATE,
    }));

    /**
     * /**
     * Application registers {@link wayos.car.hardware.serson.CarAdasManager.CarAdasEventCallback} object to receive updates and changes to
     * subscribed Car Battery properties.
     */
    public interface CarAdasEventCallback {
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
        void onErrorEvent(@CarAdasManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarAdasManager> mManager;

        public CarPropertyEventListenerToBase(CarAdasManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarAdasManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarAdasManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarAdasManager.CarAdasEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarAdasManager.CarAdasEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarAdasManager.CarAdasEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarAdasManager.CarAdasEventCallback l : callbacks) {
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
    public CarAdasManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarAdasManager.CarAdasEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarAdasManager.CarPropertyEventListenerToBase(this);
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
    public synchronized void unregisterCallback(CarAdasManager.CarAdasEventCallback callback) {
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
    public boolean getBooleanProperty(@CarAdasManager.PropertyId int propertyId)
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
    public float getFloatProperty(@CarAdasManager.PropertyId int propertyId)
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
    public int getIntProperty(@CarAdasManager.PropertyId int propertyId)
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
    public void setBooleanProperty(@CarAdasManager.PropertyId int propertyId, boolean val)
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
    public void setFloatProperty(@CarAdasManager.PropertyId int propertyId, float val)
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
    public void setIntProperty(@CarAdasManager.PropertyId int propertyId, int val)
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

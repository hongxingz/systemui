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

public class CarComfortManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarComfortManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarComfortManager.CarComfortEventCallback> mCallbacks = new ArraySet<>();
    private CarComfortManager.CarPropertyEventListenerToBase mListenerToBase = null;
    private static final int DEFAULT_AREA = 0;

    public static final int ID_REMOTE_UNLOCK_MODE_SET = 0x21401300;
    public static final int ID_REMOTE_UNLOCK_MODE_STATE = 0x21401301;
    public static final int ID_AUTO_FOLD_MIRROR_SWITCH_SET = 0x21401302;
    public static final int ID_AUTO_FOLD_MIRROR_SWITCH_STATE = 0x21401303;
    public static final int ID_AUTO_REAR_WIPER_SWITCH_SET = 0x21401304;
    public static final int ID_AUTO_REAR_WIPER_SWITCH_STATE = 0x21401305;
    public static final int ID_WIPER_MAINTENANCE_MODE_REQ = 0x21401306;
    public static final int ID_WIPER_STATE = 0x21401307;
    public static final int ID_REAR_WIPER_WORKING_STATE = 0x21401308;
    public static final int ID_PARK_GEAR_UNLOCK_CAR_SWITCH_SET = 0x21401309;
    public static final int ID_PARK_GEAR_UNLOCK_CAR_SWITCH_STATE = 0x2140130A;
    public static final int ID_KICK_SENSOR_SWITCH_SET = 0x2140130B;
    public static final int ID_KICK_SENSOR_SWITCH_STATE = 0x2140130C;
    public static final int ID_TRUNK_OPEN_REQ = 0x2140130D;
    public static final int ID_AVAS_LOW_SPEED_MUTE_SET = 0x2140130E;
    public static final int ID_AVAS_LOW_SPEED_MUTE_STATE = 0x2140130F;
    public static final int ID_AVAS_SOUND_TYPE_SET = 0x21401310;
    public static final int ID_AVAS_SOUND_TYPE_STATE = 0x21401311;
    public static final int ID_WIRELESS_CHARGE_SWITCH_SET = 0x21401312;
    public static final int ID_WIRELESS_CHARGE_SWITCH_STATE = 0x21401313;
    public static final int ID_WIRELESS_CHARGE_CURRENT_STATE = 0x21401314;

    /**
     * @hide
     */
    @IntDef({
            ID_REMOTE_UNLOCK_MODE_SET,
            ID_REMOTE_UNLOCK_MODE_STATE,
            ID_AUTO_FOLD_MIRROR_SWITCH_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_STATE,
            ID_AUTO_REAR_WIPER_SWITCH_SET,
            ID_AUTO_REAR_WIPER_SWITCH_STATE,
            ID_WIPER_MAINTENANCE_MODE_REQ,
            ID_WIPER_STATE,
            ID_REAR_WIPER_WORKING_STATE,
            ID_PARK_GEAR_UNLOCK_CAR_SWITCH_SET,
            ID_PARK_GEAR_UNLOCK_CAR_SWITCH_STATE,
            ID_KICK_SENSOR_SWITCH_SET,
            ID_KICK_SENSOR_SWITCH_STATE,
            ID_TRUNK_OPEN_REQ,
            ID_AVAS_LOW_SPEED_MUTE_SET,
            ID_AVAS_LOW_SPEED_MUTE_STATE,
            ID_AVAS_SOUND_TYPE_SET,
            ID_AVAS_SOUND_TYPE_STATE,
            ID_WIRELESS_CHARGE_SWITCH_SET,
            ID_WIRELESS_CHARGE_SWITCH_STATE,
            ID_WIRELESS_CHARGE_CURRENT_STATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(new Integer[]{
            ID_REMOTE_UNLOCK_MODE_SET,
            ID_REMOTE_UNLOCK_MODE_STATE,
            ID_AUTO_FOLD_MIRROR_SWITCH_SET,
            ID_AUTO_FOLD_MIRROR_SWITCH_STATE,
            ID_AUTO_REAR_WIPER_SWITCH_SET,
            ID_AUTO_REAR_WIPER_SWITCH_STATE,
            ID_WIPER_MAINTENANCE_MODE_REQ,
            ID_WIPER_STATE,
            ID_REAR_WIPER_WORKING_STATE,
            ID_PARK_GEAR_UNLOCK_CAR_SWITCH_SET,
            ID_PARK_GEAR_UNLOCK_CAR_SWITCH_STATE,
            ID_KICK_SENSOR_SWITCH_SET,
            ID_KICK_SENSOR_SWITCH_STATE,
            ID_TRUNK_OPEN_REQ,
            ID_AVAS_LOW_SPEED_MUTE_SET,
            ID_AVAS_LOW_SPEED_MUTE_STATE,
            ID_AVAS_SOUND_TYPE_SET,
            ID_AVAS_SOUND_TYPE_STATE,
            ID_WIRELESS_CHARGE_SWITCH_SET,
            ID_WIRELESS_CHARGE_SWITCH_STATE,
            ID_WIRELESS_CHARGE_CURRENT_STATE
    }));

    /**
     * /**
     * Application registers {@link wayos.car.hardware.cabin.CarComfortManager.CarComfortEventCallback} object to receive updates and changes to
     * subscribed Car Battery properties.
     */
    public interface CarComfortEventCallback {
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
        void onErrorEvent(@CarComfortManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarComfortManager> mManager;

        public CarPropertyEventListenerToBase(CarComfortManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarComfortManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarComfortManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarComfortManager.CarComfortEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarComfortManager.CarComfortEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarComfortManager.CarComfortEventCallback> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        if (!callbacks.isEmpty()) {
            for (CarComfortManager.CarComfortEventCallback l : callbacks) {
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
    public CarComfortManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarComfortManager.CarComfortEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarComfortManager.CarPropertyEventListenerToBase(this);
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
    public synchronized void unregisterCallback(CarComfortManager.CarComfortEventCallback callback) {
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
    public boolean getBooleanProperty(@CarComfortManager.PropertyId int propertyId)
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
    public float getFloatProperty(@CarComfortManager.PropertyId int propertyId)
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
    public int getIntProperty(@CarComfortManager.PropertyId int propertyId)
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
    public void setBooleanProperty(@CarComfortManager.PropertyId int propertyId, boolean val)
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
    public void setFloatProperty(@CarComfortManager.PropertyId int propertyId, float val)
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
    public void setIntProperty(@CarComfortManager.PropertyId int propertyId, int val)
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

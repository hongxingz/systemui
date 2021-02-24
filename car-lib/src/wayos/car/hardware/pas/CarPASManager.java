package wayos.car.hardware.pas;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.annotation.IntDef;
import android.util.ArraySet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyConfig;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.hardware.automotive.vehicle.V2_0.VehicleProperty;

public class CarPASManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarPASManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarPASEventCallback> mCallbacks = new ArraySet<>();
    private CarPASManager.CarPropertyEventListenerToBase mListenerToBase = null;
    //private ICarAVMManager mService;

    public static final int ID_PAS_APA_MODE_SET = 0x21400440;
    public static final int ID_PAS_REMOTE_PARK_STATUS = 0x21400441;
    public static final int ID_PAS_MEMORY_PARK_STATUS = 0x21400443;
    public static final int ID_PAS_PARK_OUT_DRECTION_SET = 0x21400445;
    public static final int ID_PAS_PARK_OUT_DRECTION_STATUS = 0x21400446;
    public static final int ID_PAS_ABNORMAL_REQUESE = 0x21400447;
    public static final int ID_PAS_ACTIVE_STATUS = 0x21400448;
    public static final int ID_PAS_DRV_REQ_STATE = 0x2140044B;
    public static final int ID_PAS_RPA_PDC_DISTANCE_FSL = 0x2140046A;
    public static final int ID_PAS_RPA_PDC_DISTANCE_FSR = 0x2140046B;
    public static final int ID_PAS_RPA_PDC_DISTANCE_RSL = 0x2140046C;
    public static final int ID_PAS_RPA_PDC_DISTANCE_RSR = 0x2140046D;

    public static final int ID_PAS_APA_QUIT_REASON = 0x21400449;
    public static final int ID_PAS_APA_RECOVER_INTERRUPT_INDICATION = 0x2140044A;
    public static final int ID_PAS_APA_REQUEST = 0x2140044B;
    public static final int ID_PAS_RPA_PARK_PATH_SET = 0x2140044C;
    public static final int ID_PAS_RPA_PARK_PATH_DEL_REQ_SET = 0x2140044D;
    public static final int ID_PAS_RPA_PARK_LEARN_CONTROL_SET = 0x2140044E;
    public static final int ID_PAS_RPA_SIDE_RADAR_SOUND_SET = 0x2140045F;
    public static final int ID_PAS_RPA_SIDE_RADAR_SOUND_STATUS = 0x21400460;
    public static final int ID_PAS_RPA_SPEED_PERFORMANCE_PRIORITY_SET = 0x2140045D;
    public static final int ID_PAS_RPA_SPEED_PERFORMANCE_PRIORITY_STATUS = 0x2140045E;
    public static final int ID_PAS_RPA_CUSTOM_PARALLEL_PARK_STATUS = 0x21400462;
    public static final int ID_PAS_RPA_CUSTOM_VERTICAL_PARK_STATUS = 0x21400463;
    public static final int ID_PAS_RPA_CUSTOM_PARK_TYPE_CONFIRM  = 0x21400464;
    public static final int ID_PAS_RPA_ALARM_LOACTION = 0x21400469;
    public static final int ID_PAS_APP_INF = 0x21400470;
    public static final int ID_PAS_RPA_PARK_LOACTION_SELECTION_SET = 0x21400479;

    public static final int ID_PAS_APA_RADAR_SOUND_STATUS= 0x2140047A;
    public static final int ID_PAS_APA_WORKING_STATUS= 0x2140047B;
    public static final int ID_PAS_APA_PARKSLOT_STATE= 0x2140047C;
    public static final int ID_PAS_APA_SELECTED_SIDE_STATE= 0x2140047D;
    public static final int ID_PAS_APA_DRVING_REQ_STATE= 0x2140047E;
    public static final int ID_PAS_APA_PROCESS_STATUS= 0x2140047F;
    public static final int ID_PAS_BUZZER_ALARM_MODE_STATE= 0x21400480;
    public static final int ID_PAS_PDC_MODE= 0x21400481;
    public static final int ID_PAS_PDC_DISTANCE_FL= 0x21400482;
    public static final int ID_PAS_PDC_DISTANCE_FML= 0x21400483;
    public static final int ID_PAS_PDC_DISTANCE_FMR= 0x21400484;
    public static final int ID_PAS_PDC_DISTANCE_FR= 0x21400485;
    public static final int ID_PAS_PDC_DISTANCE_RL= 0x21400486;
    public static final int ID_PAS_PDC_DISTANCE_RML= 0x21400487;
    public static final int ID_PAS_PDC_DISTANCE_RMR= 0x21400488;
    public static final int ID_PAS_PDC_DISTANCE_RR= 0x21400489;

    @IntDef({
    ID_PAS_APA_MODE_SET,
    ID_PAS_REMOTE_PARK_STATUS,
    ID_PAS_MEMORY_PARK_STATUS,
    ID_PAS_PARK_OUT_DRECTION_SET,
    ID_PAS_PARK_OUT_DRECTION_STATUS,
    ID_PAS_ABNORMAL_REQUESE,
    ID_PAS_ACTIVE_STATUS,
    ID_PAS_DRV_REQ_STATE,
    ID_PAS_RPA_PDC_DISTANCE_FSL,
    ID_PAS_RPA_PDC_DISTANCE_FSR,
    ID_PAS_RPA_PDC_DISTANCE_RSL,
    ID_PAS_RPA_PDC_DISTANCE_RSR,

    ID_PAS_APA_QUIT_REASON,
    ID_PAS_APA_RECOVER_INTERRUPT_INDICATION,
    ID_PAS_APA_REQUEST,
    ID_PAS_RPA_PARK_PATH_SET,
    ID_PAS_RPA_PARK_PATH_DEL_REQ_SET,
    ID_PAS_RPA_PARK_LEARN_CONTROL_SET,
    ID_PAS_RPA_SIDE_RADAR_SOUND_SET,
    ID_PAS_RPA_SIDE_RADAR_SOUND_STATUS,
    ID_PAS_RPA_SPEED_PERFORMANCE_PRIORITY_SET,
    ID_PAS_RPA_SPEED_PERFORMANCE_PRIORITY_STATUS,
    ID_PAS_RPA_CUSTOM_PARALLEL_PARK_STATUS,
    ID_PAS_RPA_CUSTOM_VERTICAL_PARK_STATUS,
    ID_PAS_RPA_CUSTOM_PARK_TYPE_CONFIRM ,
    ID_PAS_RPA_ALARM_LOACTION,
    ID_PAS_APP_INF,
    ID_PAS_RPA_PARK_LOACTION_SELECTION_SET,
    ID_PAS_APA_RADAR_SOUND_STATUS,
    ID_PAS_APA_WORKING_STATUS,
    ID_PAS_APA_PARKSLOT_STATE,
    ID_PAS_APA_SELECTED_SIDE_STATE,
    ID_PAS_APA_DRVING_REQ_STATE,
    ID_PAS_APA_PROCESS_STATUS,
    ID_PAS_BUZZER_ALARM_MODE_STATE,
    ID_PAS_PDC_MODE,
    ID_PAS_PDC_DISTANCE_FL,
    ID_PAS_PDC_DISTANCE_FML,
    ID_PAS_PDC_DISTANCE_FMR,
    ID_PAS_PDC_DISTANCE_FR,
    ID_PAS_PDC_DISTANCE_RL,
    ID_PAS_PDC_DISTANCE_RML,
    ID_PAS_PDC_DISTANCE_RMR,
    ID_PAS_PDC_DISTANCE_RR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    private final ArraySet<Integer> mPASPropertyId = new ArraySet<>(Arrays.asList(
    ID_PAS_REMOTE_PARK_STATUS,
    ID_PAS_MEMORY_PARK_STATUS,
    ID_PAS_PARK_OUT_DRECTION_STATUS,
    ID_PAS_ABNORMAL_REQUESE,
    ID_PAS_ACTIVE_STATUS,
    ID_PAS_DRV_REQ_STATE,
    ID_PAS_RPA_PDC_DISTANCE_FSL,
    ID_PAS_RPA_PDC_DISTANCE_FSR,
    ID_PAS_RPA_PDC_DISTANCE_RSL,
    ID_PAS_RPA_PDC_DISTANCE_RSR,
    
    ID_PAS_APA_QUIT_REASON,
    ID_PAS_APA_RECOVER_INTERRUPT_INDICATION,
    ID_PAS_APA_REQUEST,
    ID_PAS_RPA_SIDE_RADAR_SOUND_STATUS,
    ID_PAS_RPA_SPEED_PERFORMANCE_PRIORITY_STATUS,
    ID_PAS_RPA_CUSTOM_PARALLEL_PARK_STATUS,
    ID_PAS_RPA_CUSTOM_VERTICAL_PARK_STATUS,
    ID_PAS_RPA_ALARM_LOACTION,
    ID_PAS_APP_INF,
    ID_PAS_APA_RADAR_SOUND_STATUS,
    ID_PAS_APA_WORKING_STATUS,
    ID_PAS_APA_PARKSLOT_STATE,
    ID_PAS_APA_SELECTED_SIDE_STATE,
    ID_PAS_APA_DRVING_REQ_STATE,
    ID_PAS_APA_PROCESS_STATUS,
    ID_PAS_BUZZER_ALARM_MODE_STATE,
    ID_PAS_PDC_MODE,
    ID_PAS_PDC_DISTANCE_FL,
    ID_PAS_PDC_DISTANCE_FML,
    ID_PAS_PDC_DISTANCE_FMR,
    ID_PAS_PDC_DISTANCE_FR,
    ID_PAS_PDC_DISTANCE_RL,
    ID_PAS_PDC_DISTANCE_RML,
    ID_PAS_PDC_DISTANCE_RMR,
    ID_PAS_PDC_DISTANCE_RR
    ));

    public interface CarPASEventCallback {
        void onChangeEvent(CarPropertyValue value);

        void onErrorEvent(@CarPASManager.PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarPASManager> mManager;

        public CarPropertyEventListenerToBase(CarPASManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarPASManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propId, int zone) {
            CarPASManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarPASEventCallback> callbacks;

        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()) {
            for (CarPASEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarPASEventCallback> callbacks;

        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()) {
            for (CarPASEventCallback l : callbacks) {
                l.onErrorEvent(propertyId, zone);
            }
        }
    }

    public CarPASManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     *
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarPASEventCallback callback)
            throws CarNotConnectedException {
        if (mCallbacks.isEmpty()) {
            mListenerToBase = new CarPASManager.CarPropertyEventListenerToBase(this);
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
    public synchronized void unregisterCallback(CarPASEventCallback callback)
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
     *
     * @return List of CarPropertyConfig objects available via Car Wireless Manager.
     * @throws CarNotConnectedException if the connection to the car service has been lost.
     */
    public List<CarPropertyConfig> getPropertyList() throws CarNotConnectedException {
        return mCarPropertyMgr.getPropertyList(mPASPropertyId);
    }

    public boolean isPropertyAvailable(@CarPASManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.isPropertyAvailable(propertyId, 0);
    }

    public int getIntProperty(@CarPASManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, 0);
    }

    public Boolean getBooleanProperty(@CarPASManager.PropertyId int propertyId)
            throws CarNotConnectedException {
        return mCarPropertyMgr.getBooleanProperty(propertyId, 0);
    }

    /**
     * Set the value of an integer property
     *
     * @param propertyId
     * @param area
     * @param val
     * @throws CarNotConnectedException
     */
    public void setIntProperty(@CarPASManager.PropertyId int propertyId, int area, int val)
            throws CarNotConnectedException {
        if (mPASPropertyId.contains(propertyId)) {
            mCarPropertyMgr.setIntProperty(propertyId, area, val);
        }
    }


    @Override
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }
}

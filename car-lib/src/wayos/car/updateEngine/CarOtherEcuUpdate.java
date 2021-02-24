package wayos.car.updateEngine;

import android.annotation.Nullable;
import android.car.annotation.ValueTypeDef;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.CarPropertyConfig;
import android.car.hardware.property.CarPropertyManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.util.ArraySet;
import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import android.util.Log;

public class CarOtherEcuUpdate implements CarManagerBase{
    private static final String TAG = "CarOtherEcuUpdate";
    private static final boolean DBG = false;
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<UpdateLisenter> mCallbacks = new ArraySet<>();
    private CarPropertyEventListenerToBase mListenerToBase = null;

    public static final int TYPE_ECU_MCU = 1;
    public static final int TYPE_ECU_CLUSTRE = 2;

    private static final int ID_ECU_UPDATE_STS = 557848576;
    private static final int ID_ECU_UPDATE_FILE_MAX_BLOCK = 557848577;
    private static final int ID_ECU_UPDATE_PROGRESS = 557848578;
    private static final int ID_ECU_UPDATE_FILE_PATH = 554702851;
    @IntDef({
        ID_ECU_UPDATE_FILE_PATH,
        ID_ECU_UPDATE_STS,
        ID_ECU_UPDATE_FILE_MAX_BLOCK,
        ID_ECU_UPDATE_PROGRESS,
        ID_ECU_UPDATE_FILE_PATH,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {}
    private final ArraySet<Integer> mPropertyIds = new ArraySet<>(Arrays.asList(new Integer [] {
        ID_ECU_UPDATE_STS,
        ID_ECU_UPDATE_FILE_MAX_BLOCK,
        ID_ECU_UPDATE_PROGRESS,
        // ID_ECU_UPDATE_FILE_PATH,
    }));

    /** @hide */
    public CarOtherEcuUpdate(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, null, DBG, TAG);
    }

    public boolean mcuUpdate(String path) throws CarNotConnectedException {
        update(path,TYPE_ECU_MCU);
        return true;
    }

    public boolean clusterUpdate(String path)throws CarNotConnectedException {
        update(path,TYPE_ECU_CLUSTRE);
        return true;
    }

    public boolean update(String path,int type) throws CarNotConnectedException {
        String str = "type:"+type+"|"+"path:"+path;
        mCarPropertyMgr.setProperty(String.class,ID_ECU_UPDATE_FILE_PATH,0,str);
        return true;
    }

    public interface UpdateLisenter {
        void onProcess (int process);
        void onUpdateResult (int result);
    }

    private static class CarPropertyEventListenerToBase implements
            CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarOtherEcuUpdate> mManager;

        public CarPropertyEventListenerToBase(CarOtherEcuUpdate manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarOtherEcuUpdate manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propertyId, int zone) {
            CarOtherEcuUpdate manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propertyId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<UpdateLisenter> callbacks;
        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }
        switch (value.getPropertyId()) {
            case ID_ECU_UPDATE_STS:
                if (!callbacks.isEmpty()) {
                    for (UpdateLisenter l: callbacks) {
                        // switch (value.getPropertyId() & VehiclePropertyType.MASK) {
                        //     case VehiclePropertyType.INT32:
                                     l.onUpdateResult((int) value.getValue());
                                // break;
                    }
                }
                break;
            case ID_ECU_UPDATE_PROGRESS:
                if (!callbacks.isEmpty()) {
                    for (UpdateLisenter l: callbacks) {
                        l.onProcess((int) value.getValue());
                      
                    }
                }
                break;
            case ID_ECU_UPDATE_FILE_MAX_BLOCK:
                
                break;
            default:
                break;
        }
       
    }

    private void handleOnErrorEvent(int propertyId, int zone) {

    }

     /**
     * Implement wrappers for contained CarPropertyManager object
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(UpdateLisenter callback)
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
    public synchronized void unregisterCallback(UpdateLisenter callback) {
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
     * Get list of properties represented by Car Hvac Manager for this car.
     * @return List of CarPropertyConfig objects available via Car Hvac Manager.
     * @throws CarNotConnectedException if the connection to the car service has been lost.
     */
    public List<CarPropertyConfig> getPropertyList() throws CarNotConnectedException {
        return mCarPropertyMgr.getPropertyList(mPropertyIds);
    }

    /** @hide */
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }
}
package wayos.car.hardware.avm;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
//import android.os.RemoteException;
import android.annotation.IntDef;
import android.util.ArraySet;
//import android.util.Log;

//import com.wayos.car.CarLibLog;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.car.hardware.CarPropertyConfig;

import android.car.hardware.property.CarPropertyManager;
import android.car.hardware.CarPropertyValue;
import android.hardware.automotive.vehicle.V2_0.VehicleProperty;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CarAVMManager implements CarManagerBase {
    private final static boolean DBG = false;
    private final static String TAG = "CarAVMManager";
    private final CarPropertyManager mCarPropertyMgr;
    private final ArraySet<CarAVMEventCallback> mCallbacks = new ArraySet<>();
    private CarPropertyEventListenerToBase mListenerToBase = null;

    // public static final int ID_AVM_REQUEST = 0x21401705;
    // public static final int ID_AVM_ERROR_STATE = 0x21401700;
    // public static final int ID_AVM_NODE_STATE = 0x21401702;
    // public static final int ID_AVM_TOUCH_AREA = 0x21401708;
    // public static final int ID_AVM_OPEN = 0x2140170B;
    public static final int ID_AVM_ERROR_STATUS = 0x21401700;
    public static final int ID_AVM_TYPE_STATUS = 0x21401701;
    public static final int ID_AVM_APA_MODE_VIEW_LAYOUT_STATUS = 0x21401702;
    public static final int ID_AVM_VIEW_DISPLAY_ORIENTATION_STATUS = 0x21401703;
    public static final int ID_AVM_VIEW_REQUEST_STATUS = 0x21401704;
    public static final int ID_AVM_REQUEST_SET = 0x21401705;
    public static final int ID_AVM_TOUCH_COOR_EVET_SET = 0x21411706;
    public static final int ID_AVM_INTERFACE_OPEN_REQUEST_SET = 0x21401707;

    @IntDef({
        ID_AVM_ERROR_STATUS,
        ID_AVM_TYPE_STATUS,
        ID_AVM_APA_MODE_VIEW_LAYOUT_STATUS,
        ID_AVM_VIEW_DISPLAY_ORIENTATION_STATUS,
        ID_AVM_VIEW_REQUEST_STATUS,
        ID_AVM_REQUEST_SET,
        ID_AVM_TOUCH_COOR_EVET_SET,
        ID_AVM_INTERFACE_OPEN_REQUEST_SET,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PropertyId {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IVITOAVM {
        int No_Request = 0x00;
        int Front_View = 0x01;
        int Animation_View = 0x02;
        int APA_View = 0x03;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AVM_TYPE{
        int TYPE_ADAYO = 0;
        int TYPE_OTHER = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AVM_APA_ModeLayoutStatus {
        int StatusNoRequest = 0;
        int StatusLargeSingleViewAndSmallBowlView = 1;
        int StatusSmallSingleViewAndLargeBowlView = 2;
        int StatusSingleView = 3;
        int StatusSingleCompositeView  = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VIewDisplayOrientationStatus{
        int StatusNoRequest = 0;
        int Status2DAllAnd2DFront = 0x01;
        int Status2DAllAnd2DCombine = 0x02;
        int Status2DAllAnd2DRight = 0x03;
        int Status2DAllAnd2DRear  = 0x04;
        int Status180Front  = 0x05;
        int Status180Rear  = 0x06;
        int Status2DAllAnd3DFront  = 0x07;
        int Status2DAllAnd3DLeft  = 0x08;
        int Status2DAllAnd3DRight  = 0x09;
        int Status2DAllAnd3DRear  = 0x0a;
        int Status2DAllAnd2DFrontNear  = 0x0b;
        int Status2DAllAnd2DRearNear = 0x0c;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AVMStatus{
        int StatusNoRequest = 0;
        int StatusReverseRequest = 0x01;
        int StatusTurnSignelRequest = 0x02;
        int StatusIVIRequestResponse = 0x03;
        int StatusRadarAlarmRequest  = 0x04;
    }



    private final ArraySet<Integer> mAVMPropertyIds = new ArraySet<>(Arrays.asList(
        ID_AVM_ERROR_STATUS,
        ID_AVM_TYPE_STATUS,
        ID_AVM_APA_MODE_VIEW_LAYOUT_STATUS,
        ID_AVM_VIEW_DISPLAY_ORIENTATION_STATUS,
        ID_AVM_VIEW_REQUEST_STATUS
        ));

    public interface CarAVMEventCallback {
        void onChangeEvent(CarPropertyValue value);

        void onErrorEvent(@PropertyId int propertyId, int zone);
    }

    private static class CarPropertyEventListenerToBase implements CarPropertyManager.CarPropertyEventListener {
        private final WeakReference<CarAVMManager> mManager;

        public CarPropertyEventListenerToBase(CarAVMManager manager) {
            mManager = new WeakReference<>(manager);
        }

        @Override
        public void onChangeEvent(CarPropertyValue value) {
            CarAVMManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnChangeEvent(value);
            }
        }

        @Override
        public void onErrorEvent(int propId, int zone) {
            CarAVMManager manager = mManager.get();
            if (manager != null) {
                manager.handleOnErrorEvent(propId, zone);
            }
        }
    }

    private void handleOnChangeEvent(CarPropertyValue value) {
        Collection<CarAVMEventCallback> callbacks;

        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()) {
            for (CarAVMEventCallback l : callbacks) {
                l.onChangeEvent(value);
            }
        }
    }

    private void handleOnErrorEvent(int propertyId, int zone) {
        Collection<CarAVMEventCallback> callbacks;

        synchronized (this) {
            callbacks = new ArraySet<>(mCallbacks);
        }

        if (!callbacks.isEmpty()) {
            for (CarAVMEventCallback l : callbacks) {
                l.onErrorEvent(propertyId, zone);
            }
        }
    }

    public CarAVMManager(IBinder service, Context context, Handler handler) {
        mCarPropertyMgr = new CarPropertyManager(service, handler, DBG, TAG);
    }

    /**
     * Implement wrappers for contained CarPropertyManager object
     * 
     * @param callback
     * @throws CarNotConnectedException
     */
    public synchronized void registerCallback(CarAVMEventCallback callback) throws CarNotConnectedException {
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
     * Stop getting property updates for the given callback. If there are multiple
     * registrations for this listener, all listening will be stopped.
     * 
     * @param callback
     */
    public synchronized void unregisterCallback(CarAVMEventCallback callback) throws CarNotConnectedException {
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


    private List<CarPropertyConfig> getPropertyList() throws CarNotConnectedException {
        return mCarPropertyMgr.getPropertyList(mAVMPropertyIds);
    }

    public boolean isPropertyAvailable(@PropertyId int propertyId) throws CarNotConnectedException {
        return mCarPropertyMgr.isPropertyAvailable(propertyId, -1);
    }

    public int getIntProperty(@PropertyId int propertyId) throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(propertyId, -1);
    }


    public void avmTouchInfo(boolean isTouchDown, int x, int y) throws CarNotConnectedException {
        int touchDown = isTouchDown ? 1 : 0;
        // mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_TOUCH_X_COOR_SET, 0, new Integer[] {x});
        // mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_TOUCH_Y_COOR_SET, 0,new Integer[]  {y});
        mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_TOUCH_COOR_EVET_SET, 0, new Integer[] {x,y,touchDown});
    }

    public void avmOpenClose(boolean isOpen) throws CarNotConnectedException {
        Integer[] value = new Integer[] { isOpen ?  1 :2};
        mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_INTERFACE_OPEN_REQUEST_SET, 0, value);
    }

    public void avmOpenClose(boolean isOpen, @AVMStatus int type) throws CarNotConnectedException {
        Integer[] value = new Integer[] { isOpen ? 1 : 0, type };
        mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_INTERFACE_OPEN_REQUEST_SET, 0, value);
        if (isOpen){
            setAVMStatus(type);
        }
    }

    public int getAVMType() throws CarNotConnectedException {
       return  mCarPropertyMgr.getIntProperty(ID_AVM_TYPE_STATUS,0);
    }

    public int getAPAModeLayoutStatus () throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(ID_AVM_APA_MODE_VIEW_LAYOUT_STATUS,0);
    }
    public int getAVMViewDisplayOrientationStatus () throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(ID_AVM_VIEW_DISPLAY_ORIENTATION_STATUS,0);
    }
    public int getAVMViewStatus () throws CarNotConnectedException {
        return mCarPropertyMgr.getIntProperty(ID_AVM_VIEW_REQUEST_STATUS,0);
    }
    public void setAVMStatus (@AVMStatus int status) throws CarNotConnectedException {
        mCarPropertyMgr.setProperty(Integer[].class, ID_AVM_REQUEST_SET, 0,new Integer[]  {status});
    }
    
    @Override
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }
}

package wayos.car.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.annotation.IntDef;
import android.content.Context;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.car.CarLibLog;


public class CarAudioControlStrategyManager implements CarManagerBase {
    
    public static final int AUDIO_COMPENSATION_MODE_CLOSE= 0;
    public static final int AUDIO_COMPENSATION_MODE_LOW = 1;
    public static final int AUDIO_COMPENSATION_MODE_MEDIUM = 2;
    public static final int AUDIO_COMPENSATION_MODE_HIGH = 3;

    @IntDef({
        AUDIO_COMPENSATION_MODE_CLOSE,
        AUDIO_COMPENSATION_MODE_LOW,
        AUDIO_COMPENSATION_MODE_MEDIUM,
        AUDIO_COMPENSATION_MODE_HIGH
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioCompensationMode {}

        /**
     * Key to persist audio compensation with speed mode  in {@link Settings.Global}
     *
     */
    public static final String AUDIO_COMPENSATION_WITH_SPEED_MODE = "wayos.car.AUDIO_COMPENSATION_WITH_SPEED_MODE";

    /**
     * default  audio compensation with speed mode 
     */
    public static final int DEFAULT_AUDIO_COMPENSATION_MODE = AUDIO_COMPENSATION_MODE_MEDIUM;


    private final ICarAudioControlStrategy mService;

    /** @hide */
    public CarAudioControlStrategyManager(IBinder service, Context context, Handler handler) {
        mService = ICarAudioControlStrategy.Stub.asInterface(service);
    }

    public int getSoundCompensationWithSpeed()
        throws CarNotConnectedException {
        try {
             return mService.getSoundCompensationWithSpeed();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getSoundCompensationWithSpeed failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    public void setSoundCompensationWithSpeed(@AudioCompensationMode int mode) 
        throws CarNotConnectedException {
        try {
            mService.setSoundCompensationWithSpeed(mode);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "setSoundCompensationWithSpeed failed", e);
            throw new CarNotConnectedException(e);
        }
    }

        /** @hide */
    @Override
    public void onCarDisconnected() {
    }
}
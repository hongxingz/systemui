package wayos.car.settings;

import android.os.IBinder;
import android.os.RemoteException;
import android.annotation.IntDef;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;

import wayos.car.settings.IWayosCarConfigurationManager;
import wayos.car.settings.CarConfigs;

import static wayos.car.settings.CarConfigs.ITEM_CARMODE;
import static wayos.car.settings.CarConfigs.ITEM_CONGAUTODRVAST;

/**
 * Manager that exposes car configuration values that are stored on the system.
 */
public class WayosCarConfigurationManager implements CarManagerBase {
    private static final String TAG = "WayosCarConfigurationManager";

    public @interface SteerWheelType {
        int Low = 0;
        int High = 1;
        int Selection = 2;
    }

    public @interface AutoDrvAstType {
        int ABSENT = 0;
        int PRESENT = 1;
    }

    public @interface CarMode {
        int DIRECT_CUSTOMER = 0;
        int RETAIL_STANDARD = 1;
    }

    private final IWayosCarConfigurationManager mConfigurationService;

    /** @hide */
    public WayosCarConfigurationManager(IBinder service) {
        mConfigurationService = IWayosCarConfigurationManager.Stub.asInterface(service);
    }

    /**
     * Returns a configuration for car.
     *
     * @return A {@link CarConfigs} that contains the configuration values.
     * @throws CarNotConnectedException If the configuration cannot be retrieved.
     */
    public CarConfigs getCarConfiguration() throws CarNotConnectedException {
        try {
            return mConfigurationService.getCarConfiguration();
        } catch (RemoteException e) {
            Log.e(TAG, "Could not get CarConfig", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Updatea configuration for car,only used in testmode.
     *
     * @return A {@link CarConfigs} that contains the configuration values.
     * @throws CarNotConnectedException If the configuration cannot be retrieved.
     */
    public void updateConfig(CarConfigs config) throws CarNotConnectedException {
        try {
            mConfigurationService.updateCarConfig(config);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not updateCarConfig", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * steer wheel type high low selection
     * 
     * @return
     */
    public int getSteerWheelType() throws CarNotConnectedException {
        try {
            return mConfigurationService.getSteerWheelType();
        } catch (RemoteException e) {
            throw new CarNotConnectedException(e);
        }
    }

    /** @hide */
    @Override
    public void onCarDisconnected() {
        // Nothing to release.
    }
}
// ICarConfigurationManager.aidl
package wayos.car.settings;

import wayos.car.settings.CarConfigs;
// Declare any non-default types here with import statements

interface IWayosCarConfigurationManager {
    /**
     * Get current car config.
     */
    CarConfigs getCarConfiguration();

    /**
     * Modify the CarConfig,only used in testMode.
     */
    void updateCarConfig(in CarConfigs config);

    /**
      * Steer wheel type
      */
    int getSteerWheelType();
}

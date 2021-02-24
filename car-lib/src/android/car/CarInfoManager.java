/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.car;

import android.annotation.Nullable;
import android.car.annotation.ValueTypeDef;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.os.Bundle;
import android.os.IBinder;


/**
 * Utility to retrieve various static information from car. Each data are grouped as {@link Bundle}
 * and relevant data can be checked from {@link Bundle} using pre-specified keys.
 */
public final class CarInfoManager implements CarManagerBase{

    private static final boolean DBG = false;
    private static final String TAG = "CarInfoManager";
    private final CarPropertyManager mCarPropertyMgr;
    /**
     * Key for manufacturer of the car. Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_KEY_MANUFACTURER = 0x11100101;
    /**
     * Key for model name of the car. This information may not necessarily allow distinguishing
     * different car models as the same name may be used for different cars depending on
     * manufacturers. Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_KEY_MODEL = 0x11100102;
    /**
     * Key for model year of the car in AC. Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_KEY_MODEL_YEAR = 0x11400103;
    /**
     * Key for unique identifier for the car. This is not VIN, and id is persistent until user
     * resets it. Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = String.class)
    public static final String BASIC_INFO_KEY_VEHICLE_ID = "android.car.vehicle-id";

    /**
     * Key for product configuration info.
     * @FutureFeature Cannot drop due to usage in non-flag protected place.
     * @hide
     */
    @ValueTypeDef(type = String.class)
    public static final String INFO_KEY_PRODUCT_CONFIGURATION = "android.car.product-config";

    /* TODO bug: 32059999
    //@ValueTypeDef(type = Integer.class)
    //public static final String KEY_DRIVER_POSITION = "driver-position";

    //@ValueTypeDef(type = int[].class)
    //public static final String KEY_SEAT_CONFIGURATION = "seat-configuration";

    //@ValueTypeDef(type = Integer.class)
    //public static final String KEY_WINDOW_CONFIGURATION = "window-configuration";

    //MT, AT, CVT, ...
    //@ValueTypeDef(type = Integer.class)
    //public static final String KEY_TRANSMISSION_TYPE = "transmission-type";

    // add: transmission gear available selection, gear available steps
    //          drive wheel: FWD, RWD, AWD, 4WD */
    /**
     * Key for Fuel Capacity in milliliters.  Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_FUEL_CAPACITY = 0x11600104;
    /**
     * Key for Fuel Types.  This is an array of fuel types the vehicle supports.
     * Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_FUEL_TYPES = 0x11410105;
    /**
     * Key for EV Battery Capacity in WH.  Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BASIC_INFO_EV_BATTERY_CAPACITY = 0x11600106;
    /**
     * Key for EV Connector Types.  This is an array of connector types the vehicle supports.
     * Passed in basic info Bundle.
     * @hide
     */
    @ValueTypeDef(type = Integer[].class)
    public static final int BASIC_INFO_EV_CONNECTOR_TYPES = 0x11410107;

    /**
     * Radar Fault Statu
     * @change_mode CONTINUOUS
     * @access READ
     */
    @ValueTypeDef(type = Integer.class)
    public static final int EADER_FAULT_STS = 0x21401609;
    /**
     * Speed of the vehicle
     * @change_mode CONTINUOUS
     * @access READ
     */
    @ValueTypeDef(type = Integer.class)
    public static final int PERF_VEHICLE_SPEED = 0x11600207;
    /**
     * Current odometer value of the vehicle
     * @change_mode CONTINUOUS
     * @access READ
     */
    @ValueTypeDef(type = Integer.class)
    public static final int PERF_ODOMETER = 0x11600204;
    /**
     * Currently selected gear
     * @change_mode CONTINUOUS
     * @access READ
     */
    @ValueTypeDef(type = Integer.class)
    public static final int GEAR_SELECTION = 0x11400400;
    /**
     * Reports wheel ticks  
     * @change_mode CONTINUOUS
     * @access READ
     * @AreadId  VehicleArea:WHEEL
     */
    @ValueTypeDef(type = Integer.class)
    public static final int WHEEL_TICK = 0x11510306;
    /**
     * Tire pressure
     * @change_mode CONTINUOUS
     * @access READ
     * @AreadId  VehicleArea:WHEEL
     */
    @ValueTypeDef(type = Integer.class)
    public static final int TIRE_PRESSURE = 0x17600309;
    /**
     * Set daytime running light
     * The setting that the user wants.
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    @ValueTypeDef(type = Integer.class)
    public static final int DAY_TIME_RUNNING_LIGHT_SWITCH_SET = 0x21401110;

    /**
     * Indicate the switch state of the AEB
     * @change_mode ON_CHANGE
     * @access READ
     */
    @ValueTypeDef(type = Integer.class)
    public static final int PASSENGER_AIR_BAG_STATE = 0x2140134F;

    /**
     * 0x0:No request
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    @ValueTypeDef(type = Integer.class)
    public static final int DRIVE_MODE_SET = 0x21401614;

    /**
     * Set current driving mode
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    @ValueTypeDef(type = Integer.class)
    public static final int DRIVING_MODE_SET = 0x21401357;

    /**
     * Request of  brake pedal modes setting from IVI to IB
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BRAKE_PEDAL_MODE_REQ = 0x21401616;

    /**
     * Feedback of the pedal mode setting request from IB to IVI
     * @change_mode ON_CHANGE
     * @access READ_WRITE
     */
    @ValueTypeDef(type = Integer.class)
    public static final int BRAKE_PEDAL_MODE_STS = 0x21401615;

    /**
     * @return Manufacturer of the car.  Null if not available.
     */
    @Nullable
    public String getManufacturer() throws CarNotConnectedException {
        CarPropertyValue<String> carProp = mCarPropertyMgr.getProperty(String.class,
                BASIC_INFO_KEY_MANUFACTURER, 0);
        return carProp != null ? carProp.getValue() : null;
    }

    /**
     * @return Model name of the car, null if not available.  This information
     * may not necessarily allow distinguishing different car models as the same
     * name may be used for different cars depending on manufacturers.
     */
    @Nullable
    public String getModel() throws CarNotConnectedException {
        CarPropertyValue<String> carProp = mCarPropertyMgr.getProperty(
                String.class, BASIC_INFO_KEY_MODEL, 0);
        return carProp != null ? carProp.getValue() : null;
    }

    /**
     * @return Model year of the car in AC.  Null if not available.
     */
    @Nullable
    public String getModelYear() throws CarNotConnectedException {
        CarPropertyValue<String> carProp = mCarPropertyMgr.getProperty(String.class,
                BASIC_INFO_KEY_MODEL_YEAR, 0);
        return carProp != null ? carProp.getValue() : null;
    }

    /**
     * @return Unique identifier for the car. This is not VIN, and vehicle id is
     * persistent until user resets it. This ID is guaranteed to be always
     * available.
     * TODO: BASIC_INFO_KEY_VEHICLE_ID property?
     */
    public String getVehicleId() throws CarNotConnectedException {
        return "";
    }

    /**
     * @return Fuel capacity of the car in milliliters.  0 if car doesn't run on
     *         fuel.
     */
    public float getFuelCapacity() throws CarNotConnectedException {
        CarPropertyValue<Float> carProp = mCarPropertyMgr.getProperty(Float.class,
                BASIC_INFO_FUEL_CAPACITY, 0);
        return carProp != null ? carProp.getValue() : 0f;
    }

    /**
     * @return Array of FUEL_TYPEs available in the car.  Empty array if no fuel
     *         types available.
     */
    public @FuelType.Enum int[] getFuelTypes() throws CarNotConnectedException {
        return mCarPropertyMgr.getIntArrayProperty(BASIC_INFO_FUEL_TYPES, 0);
    }

    /**
     * @return Battery capacity of the car in WH.  0 if car doesn't run on
     *         battery.
     */
    public float getEvBatteryCapacity() throws CarNotConnectedException {
        CarPropertyValue<Float> carProp = mCarPropertyMgr.getProperty(Float.class,
                BASIC_INFO_EV_BATTERY_CAPACITY, 0);
        return carProp != null ? carProp.getValue() : 0f;
    }

    /**
     * @return Array of EV_CONNECTOR_TYPEs available in the car.  Empty array if
     *         no connector types available.
     */
    public @EvConnectorType.Enum int[] getEvConnectorTypes() throws CarNotConnectedException {
        return mCarPropertyMgr.getIntArrayProperty(BASIC_INFO_EV_CONNECTOR_TYPES, 0);
    }

    /** @hide */
    CarInfoManager(IBinder service) {
        mCarPropertyMgr = new CarPropertyManager(service, null, DBG, TAG);
    }

    /** @hide */
    public void onCarDisconnected() {
        mCarPropertyMgr.onCarDisconnected();
    }

}

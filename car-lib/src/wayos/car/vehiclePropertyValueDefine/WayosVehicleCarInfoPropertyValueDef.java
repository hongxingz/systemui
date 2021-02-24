package wayos.car.vehiclePropertyValueDefine;
import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public final class WayosVehicleCarInfoPropertyValueDef {

    /**
     * Radar Fault Statu
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     */
    public static final int EADER_FAULT_STS = 0x21401609;

    /**
     * Indicate the switch state of the AEB
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ
     */
    public static final int PASSENGER_AIR_BAG_STATE = 0x2140134F;

    /**
     * 0x0:No request
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     */
    public static final int DRIVE_MODE_SET = 0x21401614;

    /**
     * Set current driving mode
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     */
    public static final int DRIVING_MODE_SET = 0x21401357;

    /**
     * Request of  brake pedal modes setting from IVI to IB
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     */
    public static final int BRAKE_PEDAL_MODE_REQ = 0x21401616;

    /**
     * Feedback of the pedal mode setting request from IB to IVI
     * @change_mode VehiclePropertyChangeMode:ON_CHANGE
     * @access VehiclePropertyAccess:READ_WRITE
     */
    public static final int BRAKE_PEDAL_MODE_STS = 0x21401615;



    /**
     *Radar Fault Status
     *property:EADER_FAULT_STS
     *0 "Fail_Not_Present" 1 "BSR-L_Fault" 2 "BSR-R_Fault" 3 "Lft_Rgt_BSR_Fault";
     */
    @IntDef({
            RadarFaultStatus.FAIL_NOT_PRESENT,
            RadarFaultStatus.BSR_L_FAULT,
            RadarFaultStatus.BSR_R_FAULT,
            RadarFaultStatus.LFT_RGT_BSR_FAULT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RadarFaultStatus {
        int FAIL_NOT_PRESENT = 0x0;
        int BSR_L_FAULT = 0x1;
        int BSR_R_FAULT = 0x2;
        int LFT_RGT_BSR_FAULT = 0x3;
    }

    /**
     *Passenger Airbag Status
     *property:PASSENGER_AIR_BAG_STATE
     *0 "On" 1 "Off";
     */
    @IntDef({
            PassengerAirbagtatus.ON,
            PassengerAirbagtatus.OFF,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PassengerAirbagtatus {
        int ON = 0x0;
        int OFF = 0x1;
    }

    /**
     *Driving mode (power mode)setting (from IVI)
     *Drive mode have five sub items,it is power mode.
     *property:DRIVE_MODE_SET
     *1 "ECO" 2 "Normal" 3 "Sport" 4 "PEDAL";
     */
    @IntDef({
            PowerModeSet.NORMAL,
            PowerModeSet.ECO,
            PowerModeSet.SPORT,
            PowerModeSet.PEDAL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerModeSet {
        int NORMAL = 0x1;
        int ECO = 0x2;
        int SPORT = 0x3;
        int PEDAL = 0x4;
    }
    

    /**
     *Request of brake pedal modes setting from IVI to IB
     *Drive mode have five sub items,it is brake pedal mode.
     *property:BRAKE_PEDAL_MODE_REQ
     *0 "No request" 1 "lighter" 2 "Standard" 3 "Sports";
     */
    @IntDef({
            BrakePedalModeSet.NO_REQUEST,
            BrakePedalModeSet.LIGHTER,
            BrakePedalModeSet.STANDARD,
            BrakePedalModeSet.SPORTS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BrakePedalModeSet {
        int NO_REQUEST = 0x0;
        int LIGHTER = 0x1;
        int STANDARD = 0x2;
        int SPORTS = 0x3;
    }

    /**
     *Feedback of the pedal mode setting request from IB to IVI
     *property:BRAKE_PEDAL_MODE_STS
     *0 "Init" 1 "lighter" 2 "Standard" 3 "Sports";
     */
    @IntDef({
            BrakePedalMode.INIT,
            BrakePedalMode.LIGHTER,
            BrakePedalMode.STANDARD,
            BrakePedalMode.SPORTS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BrakePedalMode {
        int INIT = 0x0;
        int LIGHTER = 0x1;
        int STANDARD = 0x2;
        int SPORTS = 0x3;
    }

    /**
     *Gear position
     *property:GEAR_SELECTION
     *0 "init" 1 "P" 2 "R" 3 "N" 4 "D"; 
     */
    @IntDef({
            GearPosition.INIT,
            GearPosition.P,
            GearPosition.R,
            GearPosition.N,
            GearPosition.D
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface GearPosition {
        int INIT = 0x0;
        int P = 0x1;
        int R = 0x2;
        int N = 0x3;
        int D = 0x4;
    }

    /**
     *tire pressure status:　4 tires
     *property:TIRE_PRESSURE
     *0 "Normalpressure" 1 "Lowpressure" 2 "Highpressure" 3 "Rapidleak"; 
     */
    @IntDef({
            TirePressureSts.INORMAL_PRESSURENIT,
            TirePressureSts.LOW_PRESSURE,
            TirePressureSts.HIGH_PRESSURE,
            TirePressureSts.RAPID_LEAK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TirePressureSts {
        int INORMAL_PRESSURENIT = 0x0;
        int LOW_PRESSURE = 0x1;
        int HIGH_PRESSURE = 0x2;
        int RAPID_LEAK = 0x3;
    }

}

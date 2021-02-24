package wayos.car.vehiclePropertyValueDefine;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public final class WayosVehicleHavcValueDef{
    /**
     * air conditioner 
     * 0 "Off" 1 "ON";
     */
    @IntDef({
        HvacPowerOn.ON,
        HvacPowerOn.OFF
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacPowerOn {
        int OFF = 0;
        int ON = 1;
    }
    /**
     * CCM driver mode set
     * 1 "Eco mode" 2 "Normal mode";
     */
    @IntDef({
        CcmDriverModeSet.ECO_MODE,
        CcmDriverModeSet.NORMAL_MODE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CcmDriverModeSet {
        int ECO_MODE = 1;
        int NORMAL_MODE = 2;
    }
    /**
     * CCM driver mode respond signal
     * 1 "Eco mode" 2 "Normal mode"
     */
    @IntDef({
        CcmDriverModeResp.ECO_MODE,
        CcmDriverModeResp.NORMAL_MODE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CcmDriverModeResp {
        int ECO_MODE = 1;
        int NORMAL_MODE = 2;
    }
    /**
     * CCM cycle status
     * 0 "REC" 1 "FRE"
     */
    @IntDef({
        HvacRecircOn.REC,
        HvacRecircOn.FRE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacRecircOn {
        int REC = 0;
        int FRE = 1;
    }
    /**
     * On/off defrost for designated window
     * 0 "OFF" 1 "ON"
     */
    @IntDef({
        HvacDefroster.OFF,
        HvacDefroster.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacDefroster {
        int OFF = 0;
        int ON = 1;
    }
    /**
     * Fan direction
     * 0 "Face" 1 "Face+Foot" 2 "Foot" 3 "Foot+window" 4 "window"
     */
    @IntDef({
        HvacFanDirection.FACE,
        HvacFanDirection.FACE_FOOT,
        HvacFanDirection.FOOT,
        HvacFanDirection.FOOT_WINDOW,
        HvacFanDirection.WINDOW
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacFanDirection {
        int FACE = 0;
        int FACE_FOOT = 1;
        int FOOT = 2;
        int FOOT_WINDOW = 3;
        int WINDOW = 4;
    }
    /**
     * On/off automatic mode
     * 0 "Auto Mode OFF" 1 "Auto Mode ON"
     */
    @IntDef({
        HvacAutoOn.OFF,
        HvacAutoOn.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacAutoOn {
        int OFF = 0;
        int ON = 1;
    }
    /**
     * On/off AC for designated areaId
     * 0 "Auto Mode OFF" 1 "Auto Mode ON"
     */
    @IntDef({
        HvacAcOn.OFF,
        HvacAcOn.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacAcOn {
        int OFF = 0;
        int ON = 1;
    }
    /**
     * Fan speed level setting 
     * 0 "Level 1" 1 "Level 2" 2 "Level 3" 3 "Level 4" 4 "Level 5" 5 "Level 6" 6 "Level 7" 7 "Fan_Off"
     */
    @IntDef({
        HvacFanSpeed.LEVEL_1,
        HvacFanSpeed.LEVEL_2,
        HvacFanSpeed.LEVEL_3,
        HvacFanSpeed.LEVEL_4,
        HvacFanSpeed.LEVEL_5,
        HvacFanSpeed.LEVEL_6,
        HvacFanSpeed.LEVEL_7,
        HvacFanSpeed.FAN_OFF
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacFanSpeed {
        int LEVEL_1 = 0;
        int LEVEL_2 = 1;
        int LEVEL_3 = 2;
        int LEVEL_4 = 3;
        int LEVEL_5 = 4;
        int LEVEL_6 = 5;
        int LEVEL_7 = 6;
        int FAN_OFF = 7;
    }
    /**
     * Enable temperature coupling between areas.
     * 0 "Singel mode" 1 "Dual mode"
     */
    @IntDef({
        HvacDualOn.SINGEL_MODE,
        HvacDualOn.DUAL_MODE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacDualOn {
        int SINGEL_MODE = 0;
        int DUAL_MODE = 1;
    }
    /**
     * Enable temperature coupling between areas.
     * 0 "OFF" 1 "Level1Low" 2 "Level2Mid" 3 "auto" 7 "Error"
     * note: if we recevie value == 7 from scm, represent scm has occurred an error.
     */
    @IntDef({
        HvacSeatTemperature.OFF,
        HvacSeatTemperature.LEVEL1_LOW,
        HvacSeatTemperature.LEVEL2_MID,
        HvacSeatTemperature.AUTO,
        HvacSeatTemperature.ERROR,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HvacSeatTemperature {
        int OFF = 0;
        int LEVEL1_LOW = 1;
        int LEVEL2_MID = 2;
        int AUTO = 3;
        int ERROR = 7;
    }
}
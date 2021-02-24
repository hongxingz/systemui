package wayos.car.vehiclePropertyValueDefine;
import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public final class WayosVehicleLightPropertyValueDef {

    
    /**
     *LED Error Indication
     *0 "Fail_Not_Present" 1 "Lft_LED_Error" 2 "Rgt_LED_Error" 3 "Lft_Rgt_Led_Error";
     */
    @IntDef({
            LEDErrorInd.FAIL_NOT_PRESENT,
            LEDErrorInd.LFT_LED_ERROR,
            LEDErrorInd.RGT_LED_ERROR,
            LEDErrorInd.LFT_RGT_LED_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LEDErrorInd {
        int FAIL_NOT_PRESENT = 0x0;
        int LFT_LED_ERROR = 0x1;
        int RGT_LED_ERROR = 0x2;
        int LFT_RGT_LED_ERROR = 0x3;
    }

    /**
     *Lamp language car search function enable
     *0 "NotValid" 1 "Ena" 2 "Dis"
     */
    @IntDef({
            LampSigSearchCarEna.NOT_VALID,
            LampSigSearchCarEna.ENA,
            LampSigSearchCarEna.DIS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigSearchCarEna {
        int NOT_VALID = 0x0;
        int ENA = 0x1;
        int DIS = 0x2;
    }

    /**
     *lamp language greeting function enable;
     *0 "NotValid" 1 "Ena" 2 "Dis"
     */
    @IntDef({
            LampSigGreenEna.NOT_VALID,
            LampSigGreenEna.ENA,
            LampSigGreenEna.DIS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigGreenEna {
        int NOT_VALID = 0x0;
        int ENA = 0x1;
        int DIS = 0x2;
    }

    /**
     *Light language lock response function enable
     *0 "NotValid" 1 "Ena" 2 "Dis"
     */
    @IntDef({
            LampSigLockEna.NOT_VALID,
            LampSigLockEna.ENA,
            LampSigLockEna.DIS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigLockEna {
        int NOT_VALID = 0x0;
        int ENA = 0x1;
        int DIS = 0x2;
    }

    /**
     *Light language unlock response function enable;
     *0 "NotValid" 1 "Ena" 2 "Dis"
     */
    @IntDef({
            LampSigUnLockEna.NOT_VALID,
            LampSigUnLockEna.ENA,
            LampSigUnLockEna.DIS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigUnLockEna {
        int NOT_VALID = 0x0;
        int ENA = 0x1;
        int DIS = 0x2;
    }

    /**
     *Lamp charging indication function enable
     *0 "NotValid" 1 "Ena" 2 "Dis"
     */
    @IntDef({
            LampSigChargeEna.NOT_VALID,
            LampSigChargeEna.ENA,
            LampSigChargeEna.DIS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigChargeEna {
        int NOT_VALID = 0x0;
        int ENA = 0x1;
        int DIS = 0x2;
    }

    /**
     *Lamp language Short greeting button
     *0 "NotValid" 1 "Reqired" 2 "NotReqired";
     */
    @IntDef({
            LampSigShortGreet.NOT_VALID,
            LampSigShortGreet.REQIRED,
            LampSigShortGreet.NOT_REQIRED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigShortGreet {
        int NOT_VALID = 0x0;
        int REQIRED = 0x1;
        int NOT_REQIRED = 0x2;
    }

    /**
     *Light continuous greeting button
     *property:LAMPSIG_LONG_GREET
     *0 "NotValid" 1 "Pushed" 2 "Rleased";
     */
    @IntDef({
            LampSigLongGreet.NOT_VALID,
            LampSigLongGreet.PUSHED,
            LampSigLongGreet.RLEASED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LampSigLongGreet {
        int NOT_VALID = 0x0;
        int PUSHED = 0x1;
        int RLEASED = 0x2;
    }

    /**
     *Room lamp full on request
     *0 "No request" 1 "Light On Request";
     */
    @IntDef({
            RoomLightOnReq.NO_REQUEST,
            RoomLightOnReq.LIGHT_ON_REQ
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoomLightOnReq {
        int NO_REQUEST = 0x0;
        int LIGHT_ON_REQ = 0x1;
    }

    /**
     *Room lamp full close request
     *0 "No request" 1 "Light Off Request";
     */
    @IntDef({
            RoomLightOffReq.NO_REQUEST,
            RoomLightOffReq.LIGHT_OFF_REQ
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoomLightOffReq {
        int NO_REQUEST = 0x0;
        int LIGHT_OFF_REQ = 0x1;
    }

    /**
     *BCM emergrncy Signal Switch
     *0 "Closed" 1 "Open";
     */
    @IntDef({
            VehicleEmergrncySignalSwitch.CLOSED,
            VehicleEmergrncySignalSwitch.OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface VehicleEmergrncySignalSwitch {
        int CLOSED = 0x0;
        int OPEN = 0x1;
    }

    /**
     *Atmosphere lamp setting status;
     *0 "Closed" 1 "Open";
     */
    @IntDef({
            AmbientLampSwitch.CLOSED,
            AmbientLampSwitch.OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AmbientLampSwitch {
        int CLOSED = 0x0;
        int OPEN = 0x1;
    }

    /**
     *Day light;
     *0 "off" 1 "on";
     */
    @IntDef({
            DayTimeRunningLightSwitch.OFF,
            DayTimeRunningLightSwitch.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DayTimeRunningLightSwitch {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *Left turn signal status;
     *0 "off" 1 "on";
     */
    @IntDef({
            LeftTurnSignal.OFF,
            LeftTurnSignal.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LeftTurnSignal {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     right turn signal status;
     *0 "off" 1 "on";
     */
    @IntDef({
            RightTurnSignal.OFF,
            RightTurnSignal.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RightTurnSignal {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *Double flash status;
     *0 "off" 1 "on";VEHICLE_EMERGRNCY_LAMP_STATE
     */
    @IntDef({
            DoubleFlashSignal.OFF,
            DoubleFlashSignal.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DoubleFlashSignal {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *High beam status;
     *0 "off" 1 "on";
     */
    @IntDef({
            HighBeamLights.OFF,
            HighBeamLights.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HighBeamLights {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *Low beam status;
     *0 "off" 1 "on";
     */
    @IntDef({
            LowBeamLights.OFF,
            LowBeamLights.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LowBeamLights {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *Brake light status;
     *0 "off" 1 "on";
     */
    @IntDef({
            ParkingBrakeOn.OFF,
            ParkingBrakeOn.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ParkingBrakeOn {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *High beam switch input;
     *0 "Inactive" 1 "Active"
     */
    @IntDef({
            HightBeamLightsSwitch.INACTIVE,
            HightBeamLightsSwitch.ACTIVE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HightBeamLightsSwitch {
        int INACTIVE = 0x0;
        int ACTIVE = 0x1;
    }
    /**
     *(IHBC) Intelligent high beam control switch
     *0 "off" 1 "on";
     */
    @IntDef({
            IntelligentHighBeamControl.OFF,
            IntelligentHighBeamControl.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntelligentHighBeamControl {
        int OFF = 0x0;
        int ON = 0x1;
    }


    /**
     *Active welcome function setting: Unlocking near vehicle;
     *0 "Open" 1 "Closed" 2 "No request"
     */
    @IntDef({
            CourtesyLampSwitvh.OPEN,
            CourtesyLampSwitvh.CLOSED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CourtesyLampSwitvh {
        int OPEN = 0x0;
        int CLOSED = 0x1;
    }

    /**
     *Headlamp height get and set
     *0 "Position1" 1 "Position2" 2 "Position3" 3 "Position4"
     */
    @IntDef({
            HighBeamLightPositionSTS.POSITION_1,
            HighBeamLightPositionSTS.POSITION_2,
            HighBeamLightPositionSTS.POSITION_3,
            HighBeamLightPositionSTS.POSITION_4
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface HighBeamLightPositionSTS {
        int POSITION_1 = 0x0;
        int POSITION_2 = 0x1;
        int POSITION_3 = 0x2;
        int POSITION_4 = 0x3;
    }

    /**
     *Headlamp leave home delay threshold;
     *0 "00s" 1 "15s" 2 "30s" 3 "45s" 4 "60s" 5 "75s" 6 "90s" 7 "No request";
     */
    @IntDef({
            FollowMeHomeTime.HOME_ZERO_S,
            FollowMeHomeTime.HOME_FIFTEEN_S,
            FollowMeHomeTime.HOME_THIRTY_S,
            FollowMeHomeTime.HOME_FORTY_FIVE_S,
            FollowMeHomeTime.HOME_SIXTY_S,
            FollowMeHomeTime.HOME_SEVENTY_FIVE_S,
            FollowMeHomeTime.HOME_NINETY_S,
            FollowMeHomeTime.HOME_NO_REQUEST
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FollowMeHomeTime {
        int HOME_ZERO_S = 0x0;
        int HOME_FIFTEEN_S = 0x1;
        int HOME_THIRTY_S = 0x2;
        int HOME_FORTY_FIVE_S = 0x3;
        int HOME_SIXTY_S = 0x4;
        int HOME_SEVENTY_FIVE_S = 0x5;
        int HOME_NINETY_S = 0x6;
        int HOME_NO_REQUEST = 0x7;
    }

    /**
     *follow me home light - time setting;
     *property:LEAVE_HOME_TIME_SET
     *0 "00s" 1 "15s" 2 "30s" 3 "45s" 4 "60s" 5 "75s" 6 "90s" 7 "No request";
     */
    @IntDef({
            LeaveHomeTime.HOME_ZERO_S,
            LeaveHomeTime.HOME_FIFTEEN_S,
            LeaveHomeTime.HOME_THIRTY_S,
            LeaveHomeTime.HOME_FORTY_FIVE_S,
            LeaveHomeTime.HOME_SIXTY_S,
            LeaveHomeTime.HOME_SEVENTY_FIVE_S,
            LeaveHomeTime.HOME_NINETY_S,
            LeaveHomeTime.HOME_NO_REQUEST
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface LeaveHomeTime {
        int HOME_ZERO_S = 0x0;
        int HOME_FIFTEEN_S = 0x1;
        int HOME_THIRTY_S = 0x2;
        int HOME_FORTY_FIVE_S = 0x3;
        int HOME_SIXTY_S = 0x4;
        int HOME_SEVENTY_FIVE_S = 0x5;
        int HOME_NINETY_S = 0x6;
        int HOME_NO_REQUEST = 0x7;
    }

    /**
     *Top linght associated door set and get
     *0 "Door NoRelated" 1 "Door Related"
     */
    @IntDef({
            RoomLightRelevantToDoor.DOOR_NO_RELATED,
            RoomLightRelevantToDoor.DOOR_RELATED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RoomLightRelevantToDoor {
        int DOOR_NO_RELATED = 0x0;
        int DOOR_RELATED = 0x1;
    }

    /**
     *Car search reminder - low beam time setting
     *property:FIND_CAR_LBEAM_REMIND_TIME_STATE
     *rsp:0 "15" 1 "30" 2 "45" 3 "60" 4 "close"
     *req:0 "15s" 1 "30s" 2 "45s" 3 "60s" 7 "No request"
     */
    @IntDef({
            FindCarLbeamRemindTime.FIFTEEN_S,
            FindCarLbeamRemindTime.THIRTY_S,
            FindCarLbeamRemindTime.FORTY_FIVE_S,
            FindCarLbeamRemindTime.SIXTY_S,
            FindCarLbeamRemindTime.CLOSED,
            FindCarLbeamRemindTime.NO_REQUEST
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FindCarLbeamRemindTime {
        int FIFTEEN_S = 0x0;
        int THIRTY_S = 0x1;
        int FORTY_FIVE_S = 0x2;
        int SIXTY_S = 0x3;
        int CLOSED = 0x4;
        int NO_REQUEST = 0x7;
    }

}
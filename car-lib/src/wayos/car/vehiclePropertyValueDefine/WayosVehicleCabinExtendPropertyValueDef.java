package wayos.car.vehiclePropertyValueDefine;
import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public final class WayosVehicleCabinExtendPropertyValueDef {

    /**
     *Set auto folding mirror switch set and get;
     *0 "Open" 1 "Closed" 2 "No request"
     */
    @IntDef({
            AutoFoldMirrorSwitch.OPEN,
            AutoFoldMirrorSwitch.CLOSED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoFoldMirrorSwitch {
        int OPEN = 0x1;
        int CLOSED = 0x2;
    }

    /**
     *Kick sensor switch set and get;
     *0 "Open" 1 "Closed" 2 "No request"
     */
    @IntDef({
            KickSensorSwitch.OPEN,
            KickSensorSwitch.CLOSED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface KickSensorSwitch {
        int OPEN = 0x1;
        int CLOSED = 0x2;
    }

    /**
     *RCTA Arc Warning Requirement
     *1 "LBSR_Warning" 2 "RBSR_Warning" 3 "LBSR_RBSR_Warning"
     */
    @IntDef({
            RCTAArcWarning.LBSR_WARNING,
            RCTAArcWarning.RBSR_WARNING,
            RCTAArcWarning.LBSR_RBSR_WARNING
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RCTAArcWarning {
        int LBSR_WARNING = 0x1;
        int RBSR_WARNING = 0x2;
        int LBSR_RBSR_WARNING = 0x3;
    }

    /**
     *Remote unlock mode  set and get
     *0 "Fourdoor" 1 "Drivedoor"
     */
    @IntDef({
            RemoteUnlockMode.FOUR_DOOR,
            RemoteUnlockMode.DRIVE_DOOR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RemoteUnlockMode {
        int FOUR_DOOR = 0x0;
        int DRIVE_DOOR = 0x1;
    }

    /**
     *Working state of wiper
     *0 "OFF" 1 "Low" 2 "High" 3 "Error" 4 "INTER" 5 "AUTO"
     */
    @IntDef({
            WiperSts.OFF,
            WiperSts.LOW,
            WiperSts.HIGHT,
            WiperSts.ERROR,
            WiperSts.INTER,
            WiperSts.AUTO
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WiperSts {
        int OFF = 0x0;
        int LOW = 0x1;
        int HIGHT = 0x2;
        int ERROR = 0x3;
        int INTER = 0x4;
        int AUTO = 0x5;
    }

    /**
     *Acoustic vehicle sound type  set and get
     *0 "Type 1" 1 "Type 2" 2 "Type 3"
     */
    @IntDef({
            AVASSoundType.TYPE_1,
            AVASSoundType.TYPE_2,
            AVASSoundType.TYPE_3
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AVASSoundType {
        int TYPE_1 = 0x0;
        int TYPE_2 = 0x1;
        int TYPE_3 = 0x2;
    }

    /**
     *Window position status
     *0 "Invalid" 1 "Full Close" 2 "Middle Position" 3 "Full Open";
     */
    @IntDef({
            WindowPosition.INVALID,
            WindowPosition.FULL_CLOAE,
            WindowPosition.MIDDLE_POSITION,
            WindowPosition.FULL_OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowPosition {
        int INVALID = 0x0;
        int FULL_CLOAE = 0x1;
        int MIDDLE_POSITION = 0x2;
        int FULL_OPEN = 0x3;
    }

    /**
     *4G module reset request;
     *0 "off" 1 "On";
     */
    @IntDef({
            TBoxModuleResetReq.OFF,
            TBoxModuleResetReq.ON
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TBoxModuleResetReq {
        int OFF = 0x0;
        int ON = 0x1;
    }

    /**
     *Tail door switch；
     *0 "close" 1 "Open";
     */
    @IntDef({
            DoorSwitch.CLOSE,
            DoorSwitch.OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DoorSwitch {
        int CLOSE = 0x0;
        int OPEN = 0x1;
    }

    /**
     *Door move;
     *0 "close" 1 "Open";
     */
    @IntDef({
            DoorMove.CLOSE,
            DoorMove.OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DoorMove {
        int CLOSE = 0x0;
        int OPEN = 0x1;
    }

    /**
     *front hood status;
     *0 "off" 1 "On";
     */
    @IntDef({
            FrontHoodState.CLOSE,
            FrontHoodState.OPEN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FrontHoodState {
        int CLOSE = 0x0;
        int OPEN = 0x11;
    }

    /**
     *Acoustic vehicle alerting low speed mute set and get;
     *0 "Inactive" 1 "Active"
     */
    @IntDef({
            AvasLowSpeedMute.INACTIVE,
            AvasLowSpeedMute.ACTIVE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AvasLowSpeedMute {
        int INACTIVE = 0x0;
        int ACTIVE = 0x1;
    }

    /**
     *EPB rescue mode;
     *0 "RescueModeOFF" 1 "RescueModeON" 2 "Norequest";
     */
    @IntDef({
            RescueMode.RESCUE_MODE_OFF,
            RescueMode.RESCUE_MODE_ON,
            RescueMode.NO_REQUEST
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RescueMode {
        int RESCUE_MODE_OFF = 0x0;
        int RESCUE_MODE_ON = 0x1;
        int NO_REQUEST = 0x3;
    }

    /**
     *4G module reset respond
     *0 "Initial" 1 "Command received" 2 "Resetting" 3 "Reset success" 4 "Reset failure"
     */
    @IntDef({
            TBoxModuleResetResp.INITIAL,
            TBoxModuleResetResp.COMMAND_RECEIVED,
            TBoxModuleResetResp.RESETTING,
            TBoxModuleResetResp.RESET_SUCCESS,
            TBoxModuleResetResp.RESET_FAILURE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TBoxModuleResetResp {
        int INITIAL = 0x0;
        int COMMAND_RECEIVED = 0x1;
        int RESETTING = 0x2;
        int RESET_SUCCESS = 0x3;
        int RESET_FAILURE = 0x4;
    }

    /**
     *Comfortable setting of boarding and alighting seats
     *0 "Default Valu" 1 "Disable" 2 "Enable" 3 "Invalid"
     */
    @IntDef({
            EasyAccessReq.DEFAULT_VALUE,
            EasyAccessReq.DISABLE,
            EasyAccessReq.ENABLE,
            EasyAccessReq.INVALID
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface EasyAccessReq {
        int DEFAULT_VALUE = 0x0;
        int DISABLE = 0x1;
        int ENABLE = 0x2;
        int INVALID = 0x3;
    }

    /**
     *RKE Trunk Unlock Command
     *0 "Norequest" 1 "LongPressRequest" 2 "ShortPressRequest" 3 "Reserved"
     */
    @IntDef({
            PKETrunkUnlockSwitch.NO_REQUEST,
            PKETrunkUnlockSwitch.LONG_PRESS,
            PKETrunkUnlockSwitch.SHORT_PRESS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PKETrunkUnlockSwitch {
        int NO_REQUEST = 0x0;
        int LONG_PRESS = 0x1;
        int SHORT_PRESS = 0x2;
    }

    /**
     * Central Lock
     * 0 "UnLock Request" 1 "Lock Request"
     */
    @IntDef({
        CentralLockReq.UNLOCK,
        CentralLockReq.LOCK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CentralLockReq {
        int UNLOCK = 0;
        int LOCK = 1;
    }
    /**
     * Trunk(PLG) open 
     * 0 "Open" 1 "Closed"
     * Note: when we set property, use 0 or 1;
     *      when we recvie property change, recvie data such as "xx%".
     */
    @IntDef({
        TrunkOpenReq.OPEN,
        TrunkOpenReq.COLSE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TrunkOpenReq {
        int OPEN = 0;
        int COLSE = 1;
    }
    /**
     * BSR Warning Requirement
     * 0 "No Warning" 1 "Warning Present"
     */
    @IntDef({
        EspEarningReq.NO_WARNING,
        EspEarningReq.WARNING_PRESENT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface EspEarningReq {
        int NO_WARNING = 0;
        int WARNING_PRESENT = 1;
    }
    /**
     * BSR Chime Type
     * 0 "Chm_OFF" 1 "Type1" 2 "Type2" 3 "Reserve"
     */
    @IntDef({
        BsrChimeType.CHM_OFF,
        BsrChimeType.TYPE1,
        BsrChimeType.TYPE2,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BsrChimeType {
        int CHM_OFF = 0;
        int TYPE1 = 1;
        int TYPE2 = 2;
    }
    /**
     * BSR System machine Status
     * 0 "System OFF" 1 "OFF_Awake" 2 "No_Warning" 3 "BSD_Warning" 4 "DOW_Warning" 5 "RCTA_Warning" 6 "RCTA_DOW_Warning" 7 "Failure"
     */
    @IntDef({
        BsrSystemMachineSts.SYSTEM_OFF,
        BsrSystemMachineSts.OFF_AWAKE,
        BsrSystemMachineSts.NO_WARNING,
        BsrSystemMachineSts.BSD_WARNING,
        BsrSystemMachineSts.DOW_WARNING,
        BsrSystemMachineSts.RCTA_WARNING,
        BsrSystemMachineSts.RCTA_DOW_WARNING,
        BsrSystemMachineSts.FAILURE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BsrSystemMachineSts {
        int SYSTEM_OFF = 0;
        int OFF_AWAKE = 1;
        int NO_WARNING = 2;
        int BSD_WARNING = 3;
        int DOW_WARNING = 4;
        int RCTA_WARNING = 5;
        int RCTA_DOW_WARNING = 6;
        int FAILURE = 7;
    }
    /**
     * BSR System Fail Status
     * 0 "Not_Present" 1 "Generic_Fail" 2 "Current_Fail";
     */
    @IntDef({
        BsrSystemFailedSts.NOT_PRESENT,
        BsrSystemFailedSts.GENERIC_FAIL,
        BsrSystemFailedSts.CURRENT_FAIL,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BsrSystemFailedSts {
        int NOT_PRESENT = 0;
        int GENERIC_FAIL = 1;
        int CURRENT_FAIL = 2;
    }
    /**
     * BSR System Fail Status
     * 0 "Fail_Not_Present" 1 "Permanent_Error";
     */
    @IntDef({
        BsrSystemFailedInd.FAIL_NOT_PRESENT,
        BsrSystemFailedInd.PERMANENT_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BsrSystemFailedInd {
        int FAIL_NOT_PRESENT = 0;
        int PERMANENT_ERROR = 1;
    }
    /**
     *  BSR Sensor Blockage Indication
     * 0 "Fail_Not_Present" 1 "LBSR_Blockage" 2 "RBSR_Blockage" 3 "LBSR_RBSR_Blockage"
     */
    @IntDef({
        BspSensorBlockageInd.FAIL_NOT_PRESENT,
        BspSensorBlockageInd.LBSR_BLOCKAGE,
        BspSensorBlockageInd.RBSR_BLOCKAGE,
        BspSensorBlockageInd.LBSR_RBSR_BLOCKAGE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BspSensorBlockageInd {
        int FAIL_NOT_PRESENT = 0;
        int LBSR_BLOCKAGE = 1;
        int RBSR_BLOCKAGE = 2;
        int LBSR_RBSR_BLOCKAGE = 3;
    }
    /**
     *  Set wiper to maintenance mode
     * 0 "Inactive" 1 "Active" 2 "No request"
     */
    @IntDef({
        WiperMaintenanceModeReq.INACTIVE,
        WiperMaintenanceModeReq.ACTIVE,
        WiperMaintenanceModeReq.NO_REQUEST,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WiperMaintenanceModeReq {
        int INACTIVE = 0;
        int ACTIVE = 1;
        int NO_REQUEST = 2;
    }
    // /**
    //  *  Wiper state from BCM
    //  * 0 "OFF" 1 "Low" 2 "High" 3 "Error" 4 "INTER" 5 "AUTO"
    //  */
    // @IntDef({
    //     WiperState.OFF,
    //     WiperState.LOW,
    //     WiperState.HIGH,
    //     WiperState.ERROR,
    //     WiperState.INTER,
    //     WiperState.AUTO
    // })
    // @Retention(RetentionPolicy.SOURCE)
    // public @interface WiperState {
    //     int OFF = 0;
    //     int LOW = 1;
    //     int HIGH = 2;
    //     int ERROR = 3;
    //     int INTER = 4;
    //     int AUTO = 5;
    // }

}
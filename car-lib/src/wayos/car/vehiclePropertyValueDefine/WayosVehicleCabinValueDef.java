package wayos.car.vehiclePropertyValueDefine;

import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class WayosVehicleCabinValueDef {

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
    /**
     *  Wiper state from BCM
     * 0 "OFF" 1 "Low" 2 "High" 3 "Error" 4 "INTER" 5 "AUTO"
     */
    @IntDef({
        WiperState.OFF,
        WiperState.LOW,
        WiperState.HIGH,
        WiperState.ERROR,
        WiperState.INTER,
        WiperState.AUTO
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WiperState {
        int OFF = 0;
        int LOW = 1;
        int HIGH = 2;
        int ERROR = 3;
        int INTER = 4;
        int AUTO = 5;
    }
    /**
     *  Set find car low beam remind time
     * 0 "15s" 1 "30s" 2 "45s" 3 "60s" 7 "No request"
     */
    @IntDef({
        FindCarLbeamRemindTimeSet.SECOND_15,
        FindCarLbeamRemindTimeSet.SECOND_20,
        FindCarLbeamRemindTimeSet.SECOND_45,
        FindCarLbeamRemindTimeSet.SECOND_60,
        FindCarLbeamRemindTimeSet.NO_REQUEST,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FindCarLbeamRemindTimeSet {
        int SECOND_15 = 0;
        int SECOND_20 = 1;
        int SECOND_45 = 2;
        int SECOND_60 = 3;
        int NO_REQUEST = 7;
    }
    /**
     *  Set high beam follow me home time
     * 0 "00s" 1 "15s" 2 "30s" 3 "45s" 4 "60s" 5 "75s" 6 "90s" 7 "No request"
     */
    @IntDef({
        FollowMeHomeTimeSet.SECOND_0,
        FollowMeHomeTimeSet.SECOND_15,
        FollowMeHomeTimeSet.SECOND_30,
        FollowMeHomeTimeSet.SECOND_45,
        FollowMeHomeTimeSet.SECOND_60,
        FollowMeHomeTimeSet.SECOND_75,
        FollowMeHomeTimeSet.SECOND_90,
        FollowMeHomeTimeSet.NO_REQUEST
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FollowMeHomeTimeSet {
        int SECOND_0 = 0;
        int SECOND_15 = 1;
        int SECOND_30 = 2;
        int SECOND_45 = 3;
        int SECOND_60 = 4;
        int SECOND_75 = 5;
        int SECOND_90 = 6;
        int NO_REQUEST = 7;
    }
}
package wayos.car.vehiclePropertyValueDefine;

import android.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class WayosVehicleBatteryValueDef {

    /**
     * Request to open e lock 
     * 1 "Lock" 2 "Unlock"
     */
    @IntDef({
        ElockOpenReq.LOCK,
        ElockOpenReq.UNLOCK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ElockOpenReq {
        int LOCK = 1;
        int UNLOCK = 2;
    }
    /**
     * energy recovery mode
     * 0 "init" 1 "High" 2 "Mid" 3 "Low" 4"POWER"
     * Note: when we recvie value==0, represent that VCU is initing.
     *  We don`t need to set value==0.if we set 0 ,respresent "no request".    
     */
    @IntDef({
        BrakingEnergyRecoveryMode.INIT,
        BrakingEnergyRecoveryMode.HIGH,
        BrakingEnergyRecoveryMode.MID,
        BrakingEnergyRecoveryMode.LOW,
        BrakingEnergyRecoveryMode.POWER,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface BrakingEnergyRecoveryMode {
        int INIT = 0;
        int HIGH = 1;
        int MID = 2;
        int LOW = 3;
        int POWER = 4; /** Mandatory recycling */
    }
    /**
     *  Wireless charge (READ_WRITE)
     * 0 "inactive" 1 "off" 2 "on"
     */
    @IntDef({
        WirelessChargeSwithSet.INACTIVE,
        WirelessChargeSwithSet.OFF,
        WirelessChargeSwithSet.ON,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WirelessChargeSwithSet {
        int INACTIVE = 0;
        int OFF = 1;
        int ON = 2;
    }
    /**
     *  Wireless charge working status.
     * 0 "OFF" 1 "Standby" 2 "Charging" 3 "FOD" 4 "Voltage Protected" 5 "Overpower Protected" 
     * 6 "transmitting coil disable" 7 "Over Temperature Protected" 8 "Charging Completed" 15 "Invalid";
     */
    @IntDef({
        WirelessChargeCurrentState.OFF,
        WirelessChargeCurrentState.STANDBY,
        WirelessChargeCurrentState.CHARGING,
        WirelessChargeCurrentState.FOD,
        WirelessChargeCurrentState.VOLTAGE_PROTECTED,
        WirelessChargeCurrentState.OVERPOWER_PROTECTED,
        WirelessChargeCurrentState.TRANSMITTING_COIL_DISABLE,
        WirelessChargeCurrentState.OVER_TEMPERATURE_PROTECTED,
        WirelessChargeCurrentState.CHARGING_COMPLETED,
        WirelessChargeCurrentState.INVALID
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface WirelessChargeCurrentState {
        int OFF = 0;
        int STANDBY = 1;
        int CHARGING = 2;
        int FOD = 3;
        int VOLTAGE_PROTECTED = 4;
        int OVERPOWER_PROTECTED = 5;
        int TRANSMITTING_COIL_DISABLE = 6;
        int OVER_TEMPERATURE_PROTECTED = 7;
        int CHARGING_COMPLETED = 8;
        int INVALID = 15;
    }
    /**
     *  Charging Cover Req
     * 0 "Open" 1 "Close"
     */
    @IntDef({
        ChargingCoverReq.OPEN,
        ChargingCoverReq.CLOSE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChargingCoverReq {
        int OPEN = 0;
        int CLOSE = 1;
    }
    
}
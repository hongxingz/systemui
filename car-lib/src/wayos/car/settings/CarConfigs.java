package wayos.car.settings;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarConfigs implements Parcelable {
    public static final String CAR_CONFIG_TEST_MODE = "carConfingTestMode";

    private final static String STR_ITEM_SUFFIX = "_Str";

    public final static String ITEM_VERSIONNUM = "VersionNum" + STR_ITEM_SUFFIX;
    public final static String ITEM_VINCODE = "VinCode" + STR_ITEM_SUFFIX;
    public final static String ITEM_CARWEIGHT = "CarWeight";
    public final static String ITEM_CARPOWER = "CarPower";
    public final static String ITEM_CARTORQUE = "CarTorque";
    public final static String ITEM_CARTYPE = "CarType";
    public final static String ITEM_CARMODE = "CarMode";
    public final static String ITEM_SALESPACKAGE = "SalesPackage";
    public final static String ITEM_DRIVEMODE = "DriveMode";
    public final static String ITEM_RECHARGEMILEAGE = "RechargeMileage";
    public final static String ITEM_BATTERYCAPACITY = "BatteryCapacity";
    public final static String ITEM_DRIVEFORM = "DriveForm";
    public final static String ITEM_GRILLETYPE = "GrilleType";
    public final static String ITEM_REARMIRELCADJ = "RearMirElcAdj";
    public final static String ITEM_REARMIRELCHEAT = "RearMirElcHeat";
    public final static String ITEM_REARMIRRFOLDTYPE = "RearMirrFoldType";
    public final static String ITEM_SEATBELTREMIND = "SeatBeltRemind";
    public final static String ITEM_FRONTSEATBELTFORCE = "FrontSeatBeltForce";
    public final static String ITEM_REARLEFTSEATBELTFORCE = "RearLeftSeatBeltForce";
    public final static String ITEM_TIRETYPE = "TireType";
    public final static String ITEM_WHEELSIZE = "WheelSize";
    public final static String ITEM_EMEBRAKEFLASREMIND = "EmeBrakeFlasRemind";
    public final static String ITEM_BRAKELINWEARLIMITALARM = "BrakelinWearLimitAlarm";
    public final static String ITEM_AEBAUTOEMEBRAKE = "AEBAutoEmeBrake";
    public final static String ITEM_BRAKEENERGYRECOVSYS = "BrakeEnergyRecovSys";
    public final static String ITEM_IBOOSTERTELEBRAKSYS = "IBoosterTeleBrakSys";
    public final static String ITEM_TAILREMCTLELECUNLOCK = "TailRemCtlElecUnlock";
    public final static String ITEM_TRUNKELECOPENHEIADJ = "TrunkElecOpenHeiAdj";
    public final static String ITEM_TAILDOORAVOIDCLAMP = "TailDoorAvoidClamp";
    public final static String ITEM_TRUNKOPENFORM = "TrunkOpenForm";
    public final static String ITEM_STEERWHEELFORM = "SteerWheelForm";
    public final static String ITEM_AIRCONDITIONFORM = "AirConditionForm";
    public final static String ITEM_FRANDSECAIROUTLET = "FrAndSecAirOutlet";
    public final static String ITEM_AIRCONDITONFILTER = "AirConditonFilter";
    public final static String ITEM_FRONTAIRBAG = "FrontAirbag";
    public final static String ITEM_FRONTSEATSIDEAIRBAG = "FrontSeatSideAirbag";
    public final static String ITEM_CHARGINGGUN = "ChargingGun";
    public final static String ITEM_SIDEAIRCURTAIN = "SideAirCurtain";
    public final static String ITEM_SUNROOF = "Sunroof";
    public final static String ITEM_SUNSHADE = "Sunshade";
    public final static String ITEM_WINDOWLIFT = "WindowLift";
    public final static String ITEM_HEADLIGHTTYPE = "HeadLightType";
    public final static String ITEM_HEADLIGHTOPENSTYLE = "HeadLightOpenStyle";
    public final static String ITEM_TURNLIGHTFLASHTHIRD = "TurnLightFlashThird";
    public final static String ITEM_CENCONTROLPANELADJ = "CenControlPanelAdj";
    public final static String ITEM_COMBINATIONMETER = "CombinationMeter";
    public final static String ITEM_SPEEKER = "Speeker";
    public final static String ITEM_USBINTERFACE = "USBInterface";
    public final static String ITEM_TRUNKPOWER = "TrunkPower";
    public final static String ITEM_FRONTPOWERINTERFACE = "FrontPowerInterface";
    public final static String ITEM_ANTENNA = "Antenna";
    public final static String ITEM_REVERSEIMAGE = "ReverseImage";
    public final static String ITEM_KEYLESSENTRY = "KeylessEntry";
    public final static String ITEM_REMOTESUNROOF = "RemoteSunroof";
    public final static String ITEM_DRIVEAUTOLOCK = "DriveAutoLock";
    public final static String ITEM_LOCKAUTOCLOSESUNROOF = "LockAutoCloseSunroof";
    public final static String ITEM_LOCKAUTOCLOSEWINDOW = "LockAutoCloseWindow";
    public final static String ITEM_FRONTWIPEROPEN = "FrontWiperOpen";
    public final static String ITEM_REARWIPER = "RearWiper";
    public final static String ITEM_BLINDAREAMONITOR = "BlindAreaMonitor";
    public final static String ITEM_FRONTPARKRADAR = "FrontParkRadar";
    public final static String ITEM_REARPARKRADAR = "RearParkRadar";
    public final static String ITEM_COLLIDEWANSYS = "CollideWanSys";
    public final static String ITEM_LANEDEPARTUREWARN = "LaneDepartureWarn";
    public final static String ITEM_DRVSEATADJ = "DrvSeatAdj";
    public final static String ITEM_DRVSEATLUMBARSUPPORT = "DrvSeatLumbarSupport";
    public final static String ITEM_PASSEATADJ = "PasSeatAdj";
    public final static String ITEM_FRONTSEATHEAT = "FrontSeatHeat";
    public final static String ITEM_ACCADAPTCRUISBELT = "ACCAdaptCruisBelt";
    public final static String ITEM_AUTOPARKSYS = "AutoParkSys";
    public final static String ITEM_CONGAUTODRVAST = "CongAutoDrvAst";
    public final static String ITEM_HIGHSPEEDAUTODRVAST = "HighSpeedAutoDrvAst";
    public final static String ITEM_RTCAREARSIDECARWARN = "RTCARearSideCarWarn";
    public final static String ITEM_LANECHANGEWARN = "LaneChangeWarn";
    public final static String ITEM_IHBCLIGHTAUTOSWITCH = "IHBCLightAutoSwitch";
    public final static String ITEM_DOWSIDEOPENWARN = "DowSideOpenWarn";
    public final static String ITEM_ROADIDENTIFY = "RoadIdentify";
    public final static String ITEM_AEBAUTOEEMBRAKE = "AEBAutoEemBrake";
    public final static String ITEM_LKSRADIOAUTOKEEP = "LKSRadioAutoKeep";
    public final static String ITEM_PHONEREMOTEDRIVE = "PhoneRemoteDrive";
    public final static String ITEM_PHONEREMOTEPARK = "PhoneRemotePark";
    public final static String ITEM_TAILFOOTINDUCT = "TailFootInduct";
    public final static String ITEM_PHONEWIRELESSCHAG = "PhoneWirelessChag";
    public final static String ITEM_FACERECOGN = "FaceRecogn";
    public final static String ITEM_BPKGREAY = "BPkgReay";
    public final static String ITEM_AIRCAPSCREENCTRLPANEL = "AirCapScreenCtrlPanel";
    public final static String ITEM_CARCOLOR = "CarColor";
    public final static String ITEM_ONEBTNSTARTSWCH = "OneBtnStartSwch";
    public final static String ITEM_BIOMETRICSIDENTIFY = "BiometricsIdentify";
    public final static String ITEM_MCUVERSION = "McuVersion" + STR_ITEM_SUFFIX;
    public final static String ITEM_APPVERSION = "AppVersion" + STR_ITEM_SUFFIX;
    public final static String ITEM_OSVERSION = "OsVersion" + STR_ITEM_SUFFIX;
    public final static String ITEM_SERIALNUMBER = "SerialNumber" + STR_ITEM_SUFFIX;
    public final static String ITEM_UUID = "UUID" + STR_ITEM_SUFFIX;
    public final static String ITEM_CUSTOM_KEY = "CustomKey";

    private HashMap<String, Integer> mIntConfigsMap = new HashMap<>();
    private HashMap<String, String> mStringConfigsMap = new HashMap<>();

    public static final Creator<CarConfigs> CREATOR = new Creator<CarConfigs>() {
        public CarConfigs createFromParcel(Parcel source) {
            CarConfigs carconfig = new CarConfigs(source);
            return carconfig;
        }

        public CarConfigs[] newArray(int size) {
            return null;
        }
    };

    public CarConfigs() {

    }

    public String getStringConfig(String name) {
        return mStringConfigsMap.get(name);
    }

    public int getIntConfig(String name) {
        return mIntConfigsMap.get(name);
    }

    public void setConfig(String name, String value) {
        if (null != value) {
            mStringConfigsMap.put(name, value);
        } else {
            mStringConfigsMap.put(name, "");
        }
    }

    public void setConfig(String name, int value) {
        mIntConfigsMap.put(name, value);
    }

    public CarConfigs(Parcel source) {
        int intConfigSize = source.readInt();
        int stringConfigSize = source.readInt();
        for (int i = 0; i < intConfigSize; i++) {
            mIntConfigsMap.put(source.readString(), source.readInt());
        }

        for (int i = 0; i < stringConfigSize; i++) {
            mStringConfigsMap.put(source.readString(), source.readString());
        }
    }

    public CarConfigs(JsonReader in) throws IOException {
        in.beginObject();
        while (in.hasNext()) {
            String itemName = in.nextName();
            if (itemName.endsWith(STR_ITEM_SUFFIX)) {
                mStringConfigsMap.put(itemName, in.nextString());
            } else {
                mIntConfigsMap.put(itemName, in.nextInt());
            }
        }
        in.endObject();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIntConfigsMap.size());
        dest.writeInt(mStringConfigsMap.size());
        for (Map.Entry<String, Integer> entry : mIntConfigsMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeInt(entry.getValue());
        }

        for (Map.Entry<String, String> entry : mStringConfigsMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public static Creator<CarConfigs> getCreator() {
        return CREATOR;
    }

    public void writeToJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        for (Map.Entry<String, Integer> entry : mIntConfigsMap.entrySet()) {
            ;
            jsonWriter.name(entry.getKey()).value(entry.getValue());
        }

        for (Map.Entry<String, String> entry : mStringConfigsMap.entrySet()) {
            if (null != entry.getValue()) {
                jsonWriter.name(entry.getKey()).value(entry.getValue());
            } else {
                jsonWriter.name(entry.getKey()).value("");
            }
        }
        jsonWriter.endObject();
    }
}

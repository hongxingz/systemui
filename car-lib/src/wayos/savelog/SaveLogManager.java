package wayos.savelog;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;

import wayos.savelog.ISaveLogService;
import android.util.Log;

public class SaveLogManager {
    private static final String TAG = "SaveLogManager";
    // private ISaveLogService mService;
    private final static String LOG_PATH = "/data/misc/xlog/logs";
    private static ISaveLogService getService () {
        IBinder binder = ServiceManager.getService("SaveLogService");
        if (binder != null) {
            return ISaveLogService.Stub.asInterface(binder);
        }
        return null;
    }

    public static boolean isEnable() {
        return (SystemProperties.getInt("persist.service.aiwaylog.enable",0) == 1) ? true:false;
    }

    public static void setEnable(boolean enable) {
        if (enable) {
            SystemProperties.set("persist.service.aiwaylog.enable", "1");
        } else {
            SystemProperties.set("persist.service.aiwaylog.enable", "0");
        }
    }
    public  static void flushLog() {
        ISaveLogService service = getService();
        if (service != null) {
            try {
                service.flushLog(); 
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG,"ISaveLogService is null ");
        }

    }
    public static String getLogDir() {
        return LOG_PATH;
    }
}
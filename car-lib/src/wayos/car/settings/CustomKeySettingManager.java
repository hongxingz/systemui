package wayos.car.settings;

import android.content.Context;
import android.os.IBinder;
import android.provider.Settings;
import android.car.CarManagerBase;
import wayos.car.view.KeyEvent;

import static wayos.car.settings.CarSettings.System.KEY_COSTOM_KEY;

public class CustomKeySettingManager implements CarManagerBase {
    private final String TAG = "CustomKeySettingManager";
    private Context mContext ;
    public CustomKeySettingManager(IBinder service,Context context){
        mContext = context;
    }
    @Override
    public void onCarDisconnected() {

    }

    public void setCustomKeyValue(int keyValue) {
        Settings.System.putInt(mContext.getContentResolver(),KEY_COSTOM_KEY,keyValue);
    }
    public int getCustomKeyValue() {
        return Settings.System.getInt(mContext.getContentResolver(),KEY_COSTOM_KEY,KeyEvent.KEYCODE_SOURCE);
    }
}

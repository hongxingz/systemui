package wayos.car.settings;

import java.lang.reflect.Method;

public class PropertyUtils {

    private static volatile Method set = null;
    private static volatile Method get = null;
    private final static String SYSTE_PROPERTIES_PACKAGE = "android.os.SystemProperties";
    private final static String FUNCTION_GET = "get";
    private final static String FUNCTION_SET = "set";

    public static void set(String prop, String value) {

        try {
            if (null == set) {
                synchronized (PropertyUtils.class) {
                    if (null == set) {
                        Class<?> cls = Class.forName(SYSTE_PROPERTIES_PACKAGE);
                        set = cls.getDeclaredMethod(FUNCTION_SET, new Class<?>[] { String.class, String.class });
                    }
                }
            }
            set.invoke(null, new Object[] { prop, value });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String get(String prop, String defaultvalue) {
        String value = defaultvalue;
        try {
            if (null == get) {
                synchronized (PropertyUtils.class) {
                    if (null == get) {
                        Class<?> cls = Class.forName(SYSTE_PROPERTIES_PACKAGE);
                        get = cls.getDeclaredMethod(FUNCTION_GET, new Class<?>[] { String.class, String.class });
                    }
                }
            }
            value = (String) (get.invoke(null, new Object[] { prop, defaultvalue }));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }
}
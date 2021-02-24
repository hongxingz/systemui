package wayos.car.view;

public class KeyEvent {

    public static final int LONG_PRESS_TIMEOUT = 1000;
    /** Key code constant: Unknown key code. */
    public static final int KEYCODE_UNKNOWN         = 0;

    public static final int KEYCODE_MEDIA_NEXT = android.view.KeyEvent.KEYCODE_MEDIA_NEXT;
    public static final int KEYCODE_MEDIA_PREVIOUS = android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS;
    public static final int KEYCODE_MEDIA_PLAY_PAUSE = android.view.KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
    public static final int KEYCODE_MUTE = android.view.KeyEvent.KEYCODE_MUTE;
    public static final int KEYCODE_VOLUME_UP = android.view.KeyEvent.KEYCODE_VOLUME_UP;
    public static final int KEYCODE_VOLUME_DOWN = android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
    public static final int KEYCODE_CALL = android.view.KeyEvent.KEYCODE_CALL;
    public static final int KEYCODE_VOICE_ASSIST = android.view.KeyEvent.KEYCODE_VOICE_ASSIST;
    public static final int KEYCODE_CUSTOM = android.view.KeyEvent.KEYCODE_ASSIST;
    public static final int KEYCODE_LEFT_ROLLER = android.view.KeyEvent.KEYCODE_BUTTON_1;
    public static final int KEYCODE_RIGHT_ROLLER = android.view.KeyEvent.KEYCODE_BUTTON_2;
    public static final int KEYCODE_RESET = android.view.KeyEvent.KEYCODE_POWER;
    public static final int KEYCODE_AVM = android.view.KeyEvent.KEYCODE_BUTTON_3;
    public static final int KEYCODE_SOURCE = android.view.KeyEvent.KEYCODE_MUSIC;

    /**
     * value: the key has been pressed down.
     */
    public static final int ACTION_DOWN             = 0;
    /**
     * value: the key has been released.
     */
    public static final int ACTION_UP               = 1;
    /**
     * value: multiple duplicate key events have
     * occurred in a row, or a complex string is being delivered.  If the
     * key code is not {#link {@link #KEYCODE_UNKNOWN} then the
     * {#link {link #getRepeatCount()} method returns the number of times
     * the given key code should be executed.
     * Otherwise, if the key code is {@link #KEYCODE_UNKNOWN}, then
     * this is a sequence of characters as returned by {link #getCharacters}.
     */
    public static final int ACTION_MULTIPLE         = 2;
}

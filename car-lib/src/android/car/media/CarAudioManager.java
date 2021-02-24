/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.car.media;

import android.annotation.NonNull;
import android.annotation.IntDef;
import android.annotation.SystemApi;
import android.car.CarLibLog;
import android.car.CarManagerBase;
import android.car.CarNotConnectedException;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;

import wayos.car.media.CarEqualerInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * APIs for handling car specific audio stuff.
 */
public final class CarAudioManager implements CarManagerBase {
    //+ @WAYOS,add for Audio effect begin.
    public static final int EQ_INVALID = 0;
    public static final int EQ_FLAT = 1; 
    public static final int EQ_POPS = 2; 
    public static final int EQ_ROCKS = 3; 
    public static final int EQ_NEWS = 4; 
    public static final int EQ_CLASSICAL = 5;
    public static final int EQ_HEAVYBASS = 6; 
    public static final int EQ_ELECTRONIC = 7; 
    public static final int EQ_JAZZ = 8; 
    public static final int EQ_DANCEMUSIC = 9; 
    public static final int EQ_USER =100; //user to set own vaules


    // private static final int ID_AUDIO_PROPERTY_BALANCE = 0x6003;
    // private static final int ID_AUDIO_PROPERTY_EQUALIZER = 0x6004;
    // private static final int ID_AUDIO_PROPERTY_TOUCH = 0x6006;
    // private static final int ID_AUDIO_COMPENSITION_WITH_SPEED = 0x6007;

    public static final int CALL_TYPE_BT = 0;
    public static final int CALL_TYPE_BREAKDOWN = 1;//Breakdown Call
    public static final int CALL_TYPE_EMERGENCY = 2; //Emergency Call

    @IntDef({
            EQ_INVALID,
            EQ_FLAT ,
            EQ_POPS ,
            EQ_ROCKS,
            EQ_NEWS ,
            EQ_CLASSICAL ,
            EQ_HEAVYBASS ,
            EQ_ELECTRONIC,
            EQ_JAZZ ,
            EQ_DANCEMUSIC ,
            EQ_USER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface EQType {}
    //- @WAYOS,add for Audio effect end.
    /**
     * Zone id of the primary audio zone.
     * @hide
     */
    @SystemApi
    public static final int PRIMARY_AUDIO_ZONE = 0x0;

    /*Car and mobile Internet eg:carplay*/
    @SystemApi
    public static final int CONTENT_TYPE_INTERACTION = AudioAttributes.CONTENT_TYPE_INTERACTION;
    /**
     * Extra for {@link android.media.AudioAttributes.Builder#addBundle(Bundle)}: when used in an
     * {@link android.media.AudioFocusRequest}, the requester should receive all audio focus events,
     * including {@link android.media.AudioManager#AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK}.
     * The requester must hold {@link Car#PERMISSION_RECEIVE_CAR_AUDIO_DUCKING_EVENTS}; otherwise,
     * this extra is ignored.
     *
     * @hide
     */
    @SystemApi
    public static final String AUDIOFOCUS_EXTRA_RECEIVE_DUCKING_EVENTS =
            "android.car.media.AUDIOFOCUS_EXTRA_RECEIVE_DUCKING_EVENTS";

    /**
     * Extra for {@link android.media.AudioAttributes.Builder#addBundle(Bundle)}: when used in an
     * {@link android.media.AudioFocusRequest}, the requester should receive all audio focus for the
     * the zone. If the zone id is not defined: the audio focus request will default to the
     * currently mapped zone for the requesting uid or {@link CarAudioManager.PRIMARY_AUDIO_ZONE}
     * if no uid mapping currently exist.
     *
     * @hide
     */
    public static final String AUDIOFOCUS_EXTRA_REQUEST_ZONE_ID =
            "android.car.media.AUDIOFOCUS_EXTRA_REQUEST_ZONE_ID";

  /**
     * Extra for {@link android.media.AudioAttributes.Builder#addBundle(Bundle)}: when used in an
     * {@link android.media.AudioFocusRequest}, the requester should recevice audio focus for this type call ,
     * if the type is not defined: the audio focus request will default to the bt call.
     * this extra is ignored.
     *
     * @hide
     */
    @SystemApi
    public static final String AUDIOFOCUS_EXTRA_REQUEST_CALL_TYPE =
            "android.car.media.AUDIOFOCUS_EXTRA_REQUEST_CALL_TYPE";

    // The trailing slash forms a directory-liked hierarchy and
    // allows listening for both GROUP/MEDIA and GROUP/NAVIGATION.
    public static final String VOLUME_SETTINGS_KEY_FOR_GROUP_PREFIX = "android.car.VOLUME_GROUP/";

    /**
     * @param groupId The volume group id
     * @return Key to persist volume index for volume group in {@link Settings.Global}
     */
    public static String getVolumeSettingsKeyForGroup(int groupId) {
        return VOLUME_SETTINGS_KEY_FOR_GROUP_PREFIX + groupId;
    }

    /**
     * Key to persist master mute state in {@link Settings.Global}
     *
     * @hide
     */
    public static final String VOLUME_SETTINGS_KEY_MASTER_MUTE = "android.car.MASTER_MUTE";

    private final ContentResolver mContentResolver;
    private final ICarAudio mService;
    /**
     * Registers a {@link ContentObserver} to listen for volume group changes.
     * Note that this observer is valid for bus based car audio stack only.
     *
     * {@link ContentObserver#onChange(boolean)} will be called on every group volume change.
     *
     * @param observer The {@link ContentObserver} instance to register, non-null
     */
    @SystemApi
    public void registerVolumeChangeObserver(@NonNull ContentObserver observer) {
        mContentResolver.registerContentObserver(
                Settings.Global.getUriFor(VOLUME_SETTINGS_KEY_FOR_GROUP_PREFIX),
                true, observer);
    }

    /**
     * Unregisters the {@link ContentObserver} which listens for volume group changes.
     *
     * @param observer The {@link ContentObserver} instance to unregister, non-null
     */
    @SystemApi
    public void unregisterVolumeChangeObserver(@NonNull ContentObserver observer) {
        mContentResolver.unregisterContentObserver(observer);
    }

    /**
     * Sets the volume index for a volume group.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param groupId The volume group id whose volume index should be set.
     * @param index The volume index to set. See
     *            {@link #getGroupMaxVolume(int)} for the largest valid value.
     * @param flags One or more flags (e.g., {@link android.media.AudioManager#FLAG_SHOW_UI},
     *              {@link android.media.AudioManager#FLAG_PLAY_SOUND})
     */
    @SystemApi
    public void setGroupVolume(int groupId, int index, int flags) throws CarNotConnectedException {
        try {
            mService.setGroupVolume(groupId, index, flags);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "setGroupVolume failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Returns the maximum volume index for a volume group.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param groupId The volume group id whose maximum volume index is returned.
     * @return The maximum valid volume index for the given group.
     */
    @SystemApi
    public int getGroupMaxVolume(int groupId) throws CarNotConnectedException {
        try {
            return mService.getGroupMaxVolume(groupId);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getUsageMaxVolume failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Returns the minimum volume index for a volume group.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param groupId The volume group id whose minimum volume index is returned.
     * @return The minimum valid volume index for the given group, non-negative
     */
    @SystemApi
    public int getGroupMinVolume(int groupId) throws CarNotConnectedException {
        try {
            return mService.getGroupMinVolume(groupId);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getUsageMinVolume failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Returns the current volume index for a volume group.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param groupId The volume group id whose volume index is returned.
     * @return The current volume index for the given group.
     *
     * @see #getGroupMaxVolume(int)
     * @see #setGroupVolume(int, int, int)
     */
    @SystemApi
    public int getGroupVolume(int groupId) throws CarNotConnectedException {
        try {
            return mService.getGroupVolume(groupId);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getUsageVolume failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Adjust the relative volume in the front vs back of the vehicle cabin.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param value in the range -1.0 to 1.0 for fully toward the back through
     *              fully toward the front.  0.0 means evenly balanced.
     *
     * @see #setBalanceTowardRight(float)
     */
    @SystemApi
    public void setFadeTowardFront(float value) throws CarNotConnectedException {
        try {
            mService.setFadeTowardFront(value);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "setFadeTowardFront failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Adjust the relative volume on the left vs right side of the vehicle cabin.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param value in the range -1.0 to 1.0 for fully toward the left through
     *              fully toward the right.  0.0 means evenly balanced.
     *
     * @see #setFadeTowardFront(float)
     */
    @SystemApi
    public void setBalanceTowardRight(float value) throws CarNotConnectedException {
        try {
            mService.setBalanceTowardRight(value);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "setBalanceTowardRight failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Queries the system configuration in order to report the available, non-microphone audio
     * input devices.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_SETTINGS} permission.
     *
     * @return An array of strings representing the available input ports.
     * Each port is identified by it's "address" tag in the audioPolicyConfiguration xml file.
     * Empty array if we find nothing.
     *
     * @see #createAudioPatch(String, int, int)
     * @see #releaseAudioPatch(CarAudioPatchHandle)
     */
    @SystemApi
    public @NonNull String[] getExternalSources() throws CarNotConnectedException {
        try {
            return mService.getExternalSources();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getExternalSources failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Given an input port identified by getExternalSources(), request that it's audio signal
     * be routed below the HAL to the output port associated with the given usage.  For example,
     * The output of a tuner might be routed directly to the output buss associated with
     * AudioAttributes.USAGE_MEDIA while the tuner is playing.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_SETTINGS} permission.
     *
     * @param sourceAddress the input port name obtained from getExternalSources().
     * @param usage the type of audio represented by this source (usually USAGE_MEDIA).
     * @param gainInMillibels How many steps above the minimum value defined for the source port to
     *                       set the gain when creating the patch.
     *                       This may be used for source balancing without affecting the user
     *                       controlled volumes applied to the destination ports.  A value of
     *                       0 indicates no gain change is requested.
     * @return A handle for the created patch which can be used to later remove it.
     *
     * @see #getExternalSources()
     * @see #releaseAudioPatch(CarAudioPatchHandle)
     */
    @SystemApi
    public CarAudioPatchHandle createAudioPatch(String sourceAddress,
            @AudioAttributes.AttributeUsage int usage, int gainInMillibels)
            throws CarNotConnectedException {
        try {
            return mService.createAudioPatch(sourceAddress, usage, gainInMillibels);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "createAudioPatch failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Removes the association between an input port and an output port identified by the provided
     * handle.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_SETTINGS} permission.
     *
     * @param patch CarAudioPatchHandle returned from createAudioPatch().
     *
     * @see #getExternalSources()
     * @see #createAudioPatch(String, int, int)
     */
    @SystemApi
    public void releaseAudioPatch(CarAudioPatchHandle patch) throws CarNotConnectedException {
        try {
            mService.releaseAudioPatch(patch);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "releaseAudioPatch failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Gets the count of available volume groups in the system.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @return Count of volume groups
     */
    @SystemApi
    public int getVolumeGroupCount() throws CarNotConnectedException {
        try {
            return mService.getVolumeGroupCount();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Gets the volume group id for a given {@link AudioAttributes} usage.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param usage The {@link AudioAttributes} usage to get a volume group from.
     * @return The volume group id where the usage belongs to
     */
    @SystemApi
    public int getVolumeGroupIdForUsage(@AudioAttributes.AttributeUsage int usage)
            throws CarNotConnectedException {
        try {
            return mService.getVolumeGroupIdForUsage(usage);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupIdForUsage failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Gets array of {@link AudioAttributes} usages for a given volume group id.
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param groupId The volume group id whose associated audio usages is returned.
     * @return Array of {@link AudioAttributes} usages for a given volume group id
     */
    @SystemApi
    public @NonNull int[] getUsagesForVolumeGroupId(int groupId) throws CarNotConnectedException {
        try {
            return mService.getUsagesForVolumeGroupId(groupId);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getUsagesForVolumeGroupId failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Register {@link ICarVolumeCallback} to receive the volume key events
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param binder {@link IBinder} instance of {@link ICarVolumeCallback} to receive
     *                              volume key event callbacks
     * @throws CarNotConnectedException
     */
    @SystemApi
    public void registerVolumeCallback(@NonNull IBinder binder)
            throws CarNotConnectedException {
        try {
            mService.registerVolumeCallback(binder);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "registerVolumeCallback failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Unregister {@link ICarVolumeCallback} from receiving volume key events
     *
     * Requires {@link android.car.Car#PERMISSION_CAR_CONTROL_AUDIO_VOLUME} permission.
     *
     * @param binder {@link IBinder} instance of {@link ICarVolumeCallback} to stop receiving
     *                              volume key event callbacks
     * @throws CarNotConnectedException
     */
    @SystemApi
    public void unregisterVolumeCallback(@NonNull IBinder binder)
            throws CarNotConnectedException {
        try {
            mService.unregisterVolumeCallback(binder);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "unregisterVolumeCallback failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /** @hide */
    @Override
    public void onCarDisconnected() {
    }

    /** @hide */
    public CarAudioManager(IBinder service, Context context, Handler handler) {
        mContentResolver = context.getContentResolver();
        mService = ICarAudio.Stub.asInterface(service);
    }
    //+ @WAYOS, add for Audio effect begin.
    /**
     * Set current sound blance..
     *
     * @param x the x value of blance.
     * @param y the y value of blance.
     * @return the sound blance x and y.
     */
    private final static int MAX_BLANCE_COUNT = 2;
    @SystemApi
    public void setBalance(int x, int y) throws CarNotConnectedException {
        int balance[] = new int[MAX_BLANCE_COUNT];
        balance[0] = x;
        balance[1] = y;

        try {
            mService.setBalance(balance);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * !!!now no use 
     * Register {@link ICarAudioEffectCallback} to receive the audioEffect  events
     * <p>
     *
     * @param binder {@link IBinder} instance of {@link ICarAudioEffectCallback} to receive
     *               volume key event callbacks
     * @throws CarNotConnectedException
     */
    @SystemApi
    public void registerAudioEffectCallback(IBinder callback)
            throws CarNotConnectedException {
        try {
            mService.registerAudioEffectCallback(callback);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * !!!now no use 
     * Unregister {@link ICarAudioEffectCallback} from receiving audio effect events
     * <p>
     *
     * @param callback {@link CarAudioEffectEventCallback} instance of {@link CarAudioEffectCallback} to stop receiving
     *               volume key event callbacks
     * @throws CarNotConnectedException
     */
    @SystemApi
    public void unregisterAudioEffectCallback(IBinder callback)
            throws CarNotConnectedException {
        try {
             mService.unregisterAudioEffectCallback(callback);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Set the sound equalizer.
     * <p>
     *
     * @param eqType    Equalizer type.
     * @param eqConfigs The equlizer configs..
     * @hide
     */
    @SystemApi
    public void setEqualizer(@EQType int eqType, float[] eqConfigs) throws CarNotConnectedException {
        try {
            mService.setEqualizer(eqType, eqConfigs);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

   
    public void setEqualizer(@EQType int eqType) throws CarNotConnectedException {
        setEqualizer(eqType, null);
    }

    public void setEqualizer(float freqLlow, float freqLow, float freqNormal, float freqHigh, float freqHhigh)  throws CarNotConnectedException {
            setEqualizer(EQ_USER,new float[]{freqLlow,  freqLow,  freqNormal,  freqHigh,  freqHhigh});   
    }
    /**
     * Get current sound equalizer.
     * <p>
     *
     * @return eqConfigs The equlizer configs..
     */
    public CarEqualerInfo getEqualizer() throws CarNotConnectedException {
        try {
             return mService.getEqualizer();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Get user sound equalizer.
     * <p>
     *
     * @return eqConfigs The equlizer configs..
     */
    public CarEqualerInfo getUserEqualizer() throws CarNotConnectedException {
        try {
             return mService.getUserEqualizer();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Get current sound blance..
     * <p>
     *
     * @return the sound blance x and y.
     */
    public int[] getBalance() throws CarNotConnectedException {
        try {
             return mService.getBalance();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    /**
     * Set up mid bass
     */
    public void setBassMiddleTreble(int bass, int middle, int treble) throws CarNotConnectedException {
        try {
            mService.setBassMiddleTreble(bass,middle,treble);
       } catch (RemoteException e) {
           Log.e(CarLibLog.TAG_CAR, "setBassMiddleTreble failed", e);
           throw new CarNotConnectedException(e);
       }
    }

    public int[] getBassMiddleTreble() throws CarNotConnectedException {
        try {
             return mService.getBassMiddleTreble();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getBassMiddleTreble failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    public boolean getTouchSoundSetting() throws CarNotConnectedException {
        try {
             return mService.getTouchSoundSetting();
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }

    public void setTouchSoundSetting(boolean isSound) throws CarNotConnectedException {
        try {
             mService.setTouchSoundSetting(isSound);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "getVolumeGroupCount failed", e);
            throw new CarNotConnectedException(e);
        }
    }


    //- @WAYOS, add for Audio effect end.

    /**
    *add for audiofocus policy
     */
    public void setAdditionalAudioFocusPolicy(IBinder carAudioPolicyListener ) throws CarNotConnectedException{
        try {
            mService.setAdditionalAudioFocusPolicy(carAudioPolicyListener);
        } catch (RemoteException e) {
            Log.e(CarLibLog.TAG_CAR, "setAdditionalAudioFocusPolicy failed", e);
            throw new CarNotConnectedException(e);
        }
    }
}

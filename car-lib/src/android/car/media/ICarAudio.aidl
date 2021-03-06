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

import android.car.media.CarAudioPatchHandle;
import wayos.car.media.CarEqualerInfo;

/**
 * Binder interface for {@link android.car.media.CarAudioManager}.
 * Check {@link android.car.media.CarAudioManager} APIs for expected behavior of each call.
 *
 * @hide
 */
interface ICarAudio {
    void setGroupVolume(int groupId, int index, int flags);
    int getGroupMaxVolume(int groupId);
    int getGroupMinVolume(int groupId);
    int getGroupVolume(int groupId);

    void setFadeTowardFront(float value);
    void setBalanceTowardRight(float value);

    String[] getExternalSources();
    CarAudioPatchHandle createAudioPatch(in String sourceAddress, int usage, int gainInMillibels);
    void releaseAudioPatch(in CarAudioPatchHandle patch);

    int getVolumeGroupCount();
    int getVolumeGroupIdForUsage(int usage);
    int[] getUsagesForVolumeGroupId(int groupId);

    /**
     * IBinder is ICarVolumeCallback but passed as IBinder due to aidl hidden.
     */
    void registerVolumeCallback(in IBinder binder);
    void unregisterVolumeCallback(in IBinder binder);
    /*
     * @WAYOS add for audio effect begin.
     */
    void setEqualizer(int eqType, in float[] eqConfigs);
    CarEqualerInfo getEqualizer();
    CarEqualerInfo getUserEqualizer();
    int[] getBalance();
    void setBalance(in int[] balance);
    boolean getTouchSoundSetting();
    void setTouchSoundSetting(boolean isSound);
    void setBassMiddleTreble(int bass, int middle, int treble);
     int[] getBassMiddleTreble();

    void registerAudioEffectCallback(IBinder carAudioEffectCb);
    void unregisterAudioEffectCallback(IBinder carAudioEffectCb);
    /*
     * @WAYOS add for audio effect end.
     */

     /* @WAYOS add for Additional policy eg:carplay*/
    void setAdditionalAudioFocusPolicy(IBinder carAudioPolicyListener );
    /*Additional policy  end*/
}

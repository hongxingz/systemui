/*
 * Copyright (C) 2018 The Android Open Source Project
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

package wayos.car.media;
import wayos.car.media.CarAudioFocusInfo;

interface ICarAudioPolicyListener {
    boolean onAudioFocusGrant(in CarAudioFocusInfo afi, int requestResult)= 0;
    boolean onAudioFocusLoss(in CarAudioFocusInfo afi, boolean wasNotified)= 1;
    /**
        * Called whenever an application requests audio focus.
        * Only ever called if the {@link AudioPolicy} was built with
        * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
        * @param afi information about the focus request and the requester
        * @param requestResult deprecated after the addition of
        *     {@link AudioManager#setFocusRequestResult(AudioFocusInfo, int, AudioPolicy)}
        *     in Android P, always equal to {@link #AUDIOFOCUS_REQUEST_GRANTED}.
        */
        boolean onAudioFocusRequest(in CarAudioFocusInfo afi, int requestResult) = 2;
        /**
         * Called whenever an application abandons audio focus.
         * Only ever called if the {@link AudioPolicy} was built with
         * {@link AudioPolicy.Builder#setIsAudioFocusPolicy(boolean)} set to {@code true}.
         * @param afi information about the focus request being abandoned and the original
         *     requester.
         */
        boolean onAudioFocusAbandon(in CarAudioFocusInfo afi) = 4;
}

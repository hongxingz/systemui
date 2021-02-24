/*
 * Copyright (C) 2016 The Android Open Source Project
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
package android.car;

import android.annotation.SystemApi;

/**
 * VehicleAreaLight is an abstraction for a turn linght in a car. 
 * @hide
 */
@SystemApi
public final class VehicleAreaLight {
    public static final int LIGHT_TURN_LEFT   = 0x00000001;
    public static final int LIGHT_TURN_RIGHT  = 0x00000002;

    private VehicleAreaLight() {}
}

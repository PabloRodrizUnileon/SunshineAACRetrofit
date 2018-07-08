/*
 * Copyright (C) 2017 The Android Open Source Project
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
package com.example.android.sunshine.data.network;

import android.support.annotation.NonNull;

import com.example.android.sunshine.data.database.WeatherEntry;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Weather response from the backend. Contains the weather forecasts.
 */
class WeatherResponse {


    @SerializedName("list")
    private List<WeatherEntry> weatherEntries;

    public List<WeatherEntry> getWeatherEntries() {
        return weatherEntries;
    }

    public void setWeatherEntries(List<WeatherEntry> weatherEntries) {
        this.weatherEntries = weatherEntries;
    }

    public WeatherResponse(List<WeatherEntry> weatherEntries) {
        this.weatherEntries = weatherEntries;
    }
}
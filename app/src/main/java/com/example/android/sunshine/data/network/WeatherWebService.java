package com.example.android.sunshine.data.network;

import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pablo.
 * On 07/07/2018.
 * Modified on 07/07/2018.
 */

public interface WeatherWebService {

    @GET("weather")
    Call<WeatherResponse> getWeatherResponse();
}

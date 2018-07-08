package com.example.android.sunshine.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pablo.
 * On 08/07/2018.
 * Modified on 08/07/2018.
 */

public class WeatherApi {

    public static WeatherWebService weatherWebService;
    public static WeatherWebService getWeatherWebServiceInstance()
    {
        if(weatherWebService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://andfun-weather.udacity.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            weatherWebService = retrofit.create(WeatherWebService.class);
        }
        return weatherWebService;
    }
}

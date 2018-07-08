package com.example.android.sunshine.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.sunshine.data.SunshineRepository;
import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.List;

/**
 * Created by Pablo.
 * On 30/06/2018.
 * Modified on 30/06/2018.
 */

public class MainActivityViewModel extends ViewModel{

    private final SunshineRepository repository;
    private LiveData<List<WeatherEntry>> weatherForecasts;

    public MainActivityViewModel(SunshineRepository sunshineRepository){
        repository = sunshineRepository;
        weatherForecasts = repository.getCurrentWeatherForecasts();
    }

    public LiveData<List<WeatherEntry>> getWeatherForecasts() {
        return weatherForecasts;
    }
}

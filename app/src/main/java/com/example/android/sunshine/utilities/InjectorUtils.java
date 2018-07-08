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

package com.example.android.sunshine.utilities;

import android.content.Context;

import com.example.android.sunshine.AppExecutors;
import com.example.android.sunshine.data.SunshineRepository;
import com.example.android.sunshine.data.database.SunshineDatabase;
import com.example.android.sunshine.data.network.WeatherNetworkDataSource;
import com.example.android.sunshine.ui.detail.DetailViewModelFactory;
import com.example.android.sunshine.ui.list.MainActivityViewModelFactory;

import java.util.Date;

/**
 * <b> OverView: </b><br>
 * Provides static methods to inject the various classes needed for Sunshine.
 * <p>
 * Dependency injection is the idea that you should make required components available for a class,
 * instead of creating them within the class itself.
 * An example of how the Sunshine code does this is that instead of constructing the <code>WeatherNetworkDatasource</code>
 * within the <code>SunshineRepository</code>, the <code>WeatherNetworkDatasource</code> is created via InjectorUtils
 * and passed into the <code>SunshineRepository</code> constructor.
 * <p>
 * One of the benefits of this is that components are easier to replace when you're testing.
 * You can learn more about dependency injection <a href = "https://en.wikipedia.org/wiki/Dependency_injection">here</a>.
 * <p>
 * For now, know that the methods in <code>InjectorUtils</code> create the classes you need,
 * so they can be passed into constructors.
 */
public class InjectorUtils {

    /**
     * Method that provides the SunshineRepository
     * @param context
     * @return
     */
    public static SunshineRepository provideRepository(Context context) {
        SunshineDatabase database = SunshineDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        WeatherNetworkDataSource networkDataSource =
                WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return SunshineRepository.getInstance(database.weatherDao(), networkDataSource, executors);
    }

    public static WeatherNetworkDataSource provideNetworkDataSource(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new DetailViewModelFactory(repository, date);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository);
    }

}
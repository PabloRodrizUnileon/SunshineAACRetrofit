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

package com.example.android.sunshine.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.sunshine.AppExecutors;
import com.example.android.sunshine.data.database.WeatherDao;
import com.example.android.sunshine.data.database.WeatherEntry;
import com.example.android.sunshine.data.network.WeatherNetworkDataSource;
import com.example.android.sunshine.utilities.SunshineDateUtils;

import java.util.Date;
import java.util.List;

/**
 * Handles data operations in Sunshine. Acts as a mediator between {@link WeatherNetworkDataSource}
 * and {@link WeatherDao}
 * Commented by Pablo
 */
public class SunshineRepository {
    private static final String LOG_TAG = SunshineRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static SunshineRepository sInstance;
    private final WeatherDao mWeatherDao;
    private final WeatherNetworkDataSource mWeatherNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    /**
     * Constructor of Sunshine's Repository.
     *
     * @param weatherDao               WeatherDao object to get the persisted data.
     * @param weatherNetworkDataSource WeatherNetworkDataSource object to fetch data from backend.
     * @param executors                AppExecutors object to execute code off the main thread.
     *                                 <p>
     *                                 <b>More:</b> <br>
     *                                 <p>
     *                                 Why use observeForever()?
     *                                 <code>observeForever()</code> is very similar to <code>observe</code>,
     *                                 with one major difference, it is always considered active.
     *                                 Because of this, it does not take an object with a Lifecycle.
     *                                 Why are you using it here? --> SunshineRepository is observing <code>WeatherNetworkDataSource</code>;
     *                                 neither of these have an associated UI controller lifecycle,
     *                                 rather, they exist for the entire lifecycle of the app. <br>
     *                                 Therefore, you can safely use <code>observeForever()</code>.
     */
    private SunshineRepository(WeatherDao weatherDao,
                               WeatherNetworkDataSource weatherNetworkDataSource,
                               AppExecutors executors) {
        mWeatherDao = weatherDao;
        mWeatherNetworkDataSource = weatherNetworkDataSource;
        mExecutors = executors;
        LiveData<List<WeatherEntry>> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecasts();
        networkData.observeForever(weatherEntries -> {
            mExecutors.diskIO().execute(() -> {
                // Deletes old historical data
                deleteOldData();
                Log.d(LOG_TAG, "Old weather deleted");
                mWeatherDao.bullInsert(weatherEntries.toArray(new WeatherEntry[networkData.getValue().size()]));
                Log.d(LOG_TAG, "New values inserted");
            });
        });
    }

    public synchronized static SunshineRepository getInstance(
            WeatherDao weatherDao, WeatherNetworkDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SunshineRepository(weatherDao, weatherNetworkDataSource,
                        executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     *
     * @return
     */
    public LiveData<List<WeatherEntry>> getCurrentWeatherForecasts()
    {
        initializeData();
        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        return mWeatherDao.getCurrentWeatherForecasts(today);
    }

    /**
     * Method to retrieve a LiveData observable of WeatherEntry. <br>
     * <p>
     * The call to get this data could be directly over the DAO,
     * but that would defeat the whole point of the repository class.
     *
     * @param date Date used to get the weather information.
     * @return LiveData of WeatherEntry with the specified weather info.
     */
    public LiveData<WeatherEntry> getWeatherByDate(Date date) {
        /*
            You'll do "lazy" instantiation of our data - when it's requested, you'll load from the network.
            This shows off one helpful aspect of the repository:
                Since it is the API through which all data requests are made,
                you ensure that every time you getWeatherByDate(), the data initialization is triggered.
                This would not be possible if you directly accessed the WeatherDao
         */
        initializeData();
        return mWeatherDao.getWeatherByDate(date);
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    private synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {
            if (isFetchNeeded()) {
                startFetchWeatherService();
            }
        });
    }
    /**
     * Database related operations
     **/

    /**
     * Deletes old weather data because we don't need to keep multiple days' data
     * <p>
     * This will delete all old data whenever you do a save to the database.
     * Because the app uses the OnConflictReplace strategy and has ensured uniqueness by date,
     * if it gets new weather information it'll also update what's already in the database.
     */
    private void deleteOldData() {
        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        mWeatherDao.deleteOldWeather(today);
    }

    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded() {
        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        int count = mWeatherDao.countAllFutureWeather(today);
        return (count < WeatherNetworkDataSource.NUM_DAYS);
    }

    /**
     * Network related operation
     */

    private void startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }

}
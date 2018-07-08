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

package com.example.android.sunshine.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.sunshine.data.SunshineRepository;
import com.example.android.sunshine.data.database.WeatherEntry;

import java.util.Date;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link SunshineRepository} and an ID for the current {@link WeatherEntry}
 *
 * <p>
 * Part of the reason it's hard to just construct a view model or pass in data
 * is that you don't want to accidentally store UI controller state.
 * ViewModels should not store objects with Contexts, such as activities, fragments or even Views.
 * This is because these objects are meant to have shorter lifespans than the view model.
 * Storing a reference to these objects will keep them from getting garbage collected,
 * which causes a memory leak.
 */
public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SunshineRepository mRepository;
    private final Date date;

    public DetailViewModelFactory(SunshineRepository repository, Date date) {
        this.mRepository = repository;
        this.date = date;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository, date);
    }
}

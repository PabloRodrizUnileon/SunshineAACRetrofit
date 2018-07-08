package com.example.android.sunshine.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.example.android.sunshine.data.SunshineRepository;

/**
 * Created by Pablo.
 * On 30/06/2018.
 * Modified on 30/06/2018.
 */

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SunshineRepository repository;

    public MainActivityViewModelFactory(SunshineRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}

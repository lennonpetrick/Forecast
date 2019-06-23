package com.test.forecast.di.modules;

import com.test.forecast.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = PresenterModule.class)
    abstract MainActivity mainActivity();

}

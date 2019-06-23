package com.test.forecast.di.modules;

import androidx.annotation.NonNull;

import com.test.forecast.presentation.MainContract;
import com.test.forecast.presentation.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PresenterModule {

    @Binds
    abstract MainContract.Presenter providesMainPresenter(@NonNull MainPresenter presenter);

}

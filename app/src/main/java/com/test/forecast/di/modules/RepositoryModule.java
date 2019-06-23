package com.test.forecast.di.modules;

import androidx.annotation.NonNull;

import com.test.forecast.data.repository.ForecastRepositoryImpl;
import com.test.forecast.domain.ForecastRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract ForecastRepository providesForecastRepository(@NonNull ForecastRepositoryImpl repository);

}

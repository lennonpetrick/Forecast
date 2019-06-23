package com.test.forecast.di.modules;

import com.test.forecast.BuildConfig;
import com.test.forecast.di.qualifiers.ApiKey;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDataSourceModule {

    @Provides
    @ApiKey
    static String providesApiKey() {
        return BuildConfig.API_KEY;
    }

}

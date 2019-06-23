package com.test.forecast.di.modules;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.forecast.utils.LocationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context providesContext(@NonNull Application application) {
        return application;
    }

    @Provides
    @Nullable
    LocationManager providesLocationManager(@NonNull Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    LocationHelper providesLocationHelper(@Nullable LocationManager locationManager) {
        return new LocationHelper(locationManager);
    }

}

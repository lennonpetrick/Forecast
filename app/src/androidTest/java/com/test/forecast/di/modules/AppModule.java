package com.test.forecast.di.modules;

import android.app.Activity;
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
        return null;
    }

    @Provides
    LocationHelper providesLocationHelper(@Nullable LocationManager locationManager) {
        return new LocationHelper(locationManager) {

            @Override
            public boolean hasPermission(@NonNull Context context) {
                return true;
            }

            @Override
            public boolean isLocationAvailable(@NonNull Activity activity, int requestCode) {
                return true;
            }

            @Override
            public void requestLocation(@NonNull Listener callback) {
                callback.onGetLocation(59.3310373, 18.0706638);
            }
        };
    }

}
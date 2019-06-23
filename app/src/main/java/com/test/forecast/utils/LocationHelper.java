package com.test.forecast.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.test.forecast.R;

public class LocationHelper {

    private LocationManager mLocationManager;

    public LocationHelper(@Nullable LocationManager locationManager) {
        this.mLocationManager = locationManager;
    }

    public boolean hasPermission(@NonNull Context context) {
        return ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(@NonNull Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[] {
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                },
                requestCode);
    }

    public boolean isPermissionGranted(int[] grantResults) {
        return grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(@NonNull Listener callback) {
        if (mLocationManager == null)
            return;

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);

        mLocationManager.requestSingleUpdate(criteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                callback.onGetLocation(location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        }, null);
    }

    public boolean isLocationAvailable(@NonNull Activity activity, int requestCode) {
        if (mLocationManager != null
                && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }

        askToEnable(activity, requestCode);
        return false;
    }

    private void askToEnable(@NonNull Activity activity, int requestCode) {
        new AlertDialog.Builder(activity)
                .setMessage(R.string.message_enable_location)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes,
                        (dialog, which) -> activity.startActivityForResult(
                                new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                requestCode))
                .show();
    }

    public interface Listener {
        void onGetLocation(double lon, double lat);
    }
}

package com.test.forecast.data;

import com.google.gson.annotations.SerializedName;

public class ForecastEntity {

    private double latitude,
                   longitude;

    private String timezone;

    @SerializedName("currently")
    private WeatherEntity weather;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public WeatherEntity getWeather() {
        return weather;
    }
}

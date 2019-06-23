package com.test.forecast.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Forecast implements Parcelable {

    private double longitude,
                   latitude,
                   temperature;

    private String timezone,
                   summary,
                   icon;

    public Forecast() { }

    private Forecast(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
        temperature = in.readDouble();
        timezone = in.readString();
        summary = in.readString();
        icon = in.readString();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(temperature);
        dest.writeString(timezone);
        dest.writeString(summary);
        dest.writeString(icon);
    }
}

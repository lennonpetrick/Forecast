package com.test.forecast.data;

public class WeatherEntity   {

    private double temperature;
    private String summary,
                   icon;

    public double getTemperature() {
        return temperature;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }
}

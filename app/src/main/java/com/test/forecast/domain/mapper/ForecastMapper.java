package com.test.forecast.domain.mapper;

import androidx.annotation.NonNull;

import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.data.entities.WeatherEntity;
import com.test.forecast.domain.Forecast;

public class ForecastMapper {

    @NonNull
    public static Forecast transformForecast(@NonNull ForecastEntity entity) {
        Forecast model = new Forecast();
        model.setLongitude(entity.getLongitude());
        model.setLatitude(entity.getLatitude());
        model.setTimezone(entity.getTimezone());

        WeatherEntity weatherEntity = entity.getWeather();
        if (weatherEntity != null) {
            model.setTemperature(weatherEntity.getTemperature());
            model.setSummary(weatherEntity.getSummary());
            model.setIcon(weatherEntity.getIcon());
        }

        return model;
    }

}

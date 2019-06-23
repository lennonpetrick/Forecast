package com.test.forecast.domain;

import com.test.forecast.data.entities.ForecastEntity;

import io.reactivex.Single;

public interface ForecastRepository {
    Single<ForecastEntity> getForecast(double lon, double lat);
}

package com.test.forecast.data.repository.datasource;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.data.repository.datasource.apiservice.ForecastService;
import com.test.forecast.di.qualifiers.ApiKey;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * This data source is used to fetch forecast data from the api.
 * */
@WorkerThread
public class RemoteForecastDataSource {

    private final ForecastService mService;
    private final String mApiKey;

    @Inject
    public RemoteForecastDataSource(@NonNull ForecastService service,
                                    @ApiKey String apiKey) {
        this.mService = service;
        this.mApiKey = apiKey;
    }

    /**
     * Returns the weather forecast from a specific location.
     *
     * @param lon The location's longitude.
     * @param lat The location's latitude.
     * @return The forecast.
     **/
    public Single<ForecastEntity> getForecast(double lon, double lat) {
        return mService.getForecast(mApiKey, lon, lat);
    }

}

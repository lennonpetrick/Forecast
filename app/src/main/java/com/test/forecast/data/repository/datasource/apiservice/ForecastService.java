package com.test.forecast.data.repository.datasource.apiservice;

import com.test.forecast.data.entities.ForecastEntity;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * This interface is a service used for getting the forecast data from api using
 * retrofit. The framework uses this interface.
 */
public interface ForecastService {

    /**
     * Returns the weather forecast from a specific location.
     *
     * @param apiKey Api key.
     * @param lon The location's longitude.
     * @param lat The location's latitude.
     * @return The forecast.
     **/
    @GET("forecast/{apiKey}/{lat},{lon}")
    Single<ForecastEntity> getForecast(@Path("apiKey") String apiKey,
                                       @Path("lon") double lon,
                                       @Path("lat") double lat);

}

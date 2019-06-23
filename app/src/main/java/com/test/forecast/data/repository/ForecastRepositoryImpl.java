package com.test.forecast.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.data.repository.datasource.RemoteForecastDataSource;
import com.test.forecast.domain.ForecastRepository;
import com.test.forecast.exceptions.NoConnectionException;
import com.test.forecast.utils.ConnectionUtils;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * This class is a implementation of {@link ForecastRepository} and it is responsible for
 * working with forecast data from local and cloud. In this case, only cloud.
 * */
@WorkerThread
public class ForecastRepositoryImpl implements ForecastRepository {

    private final RemoteForecastDataSource mRemoteDataSource;

    @Inject
    public ForecastRepositoryImpl(@NonNull RemoteForecastDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    /**
     * Returns the weather forecast from the data source.
     *
     * @param lon The location's longitude.
     * @param lat The location's latitude.
     * @return The forecast.
     **/
    @Override
    public Single<ForecastEntity> getForecast(double lon, double lat) {
        return mRemoteDataSource.getForecast(lon, lat)
                .onErrorResumeNext(throwable -> {
                    if (ConnectionUtils.noInternetAvailable(throwable)) {
                        return Single.error(new NoConnectionException());
                    }

                    return Single.error(throwable);
                });
    }
}

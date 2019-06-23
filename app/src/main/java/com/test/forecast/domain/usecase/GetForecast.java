package com.test.forecast.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.forecast.di.qualifiers.UIScheduler;
import com.test.forecast.di.qualifiers.WorkScheduler;
import com.test.forecast.domain.Forecast;
import com.test.forecast.domain.ForecastRepository;
import com.test.forecast.domain.mapper.ForecastMapper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

/**
 * This class is an implementation of {@link UseCase} which fetches
 * the weather forecast based on the location.
 */
public class GetForecast extends UseCase<Forecast, GetForecast.Params> {

    private final ForecastRepository mRepository;

    @Inject
    public GetForecast(@NonNull @WorkScheduler Scheduler workThread,
                       @NonNull @UIScheduler Scheduler uiThread,
                       @NonNull CompositeDisposable disposables,
                       @NonNull ForecastRepository repository) {
        super(workThread, uiThread, disposables);
        mRepository = repository;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected Observable<Forecast> buildUseCase(@Nullable Params params) {
        ensureNotNull(params);
        return mRepository
                .getForecast(params.lon, params.lat)
                .map(ForecastMapper::transformForecast)
                .toObservable();
    }

    public static final class Params {

        private final double lon,
                             lat;

        private Params(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;
        }

        public static Params forLonLat(double lon, double lat) {
            return new Params(lon, lat);
        }
    }
}

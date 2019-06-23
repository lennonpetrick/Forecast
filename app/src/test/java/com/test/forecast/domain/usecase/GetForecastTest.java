package com.test.forecast.domain.usecase;

import com.test.forecast.BaseTest;
import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.domain.ForecastRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class GetForecastTest extends BaseTest {

    private GetForecast mUseCase;
    private CompositeDisposable mDisposable;

    @Before
    public void setUp() throws Exception {
        ForecastEntity entity = createObjectFromFile(
                "Forecast.json", ForecastEntity.class);

        ForecastRepository repository = Mockito.mock(ForecastRepository.class);
        when(repository.getForecast(anyDouble(), anyDouble()))
                .thenReturn(Single.just(entity));

        Scheduler scheduler = Schedulers.trampoline();
        mDisposable = new CompositeDisposable();
        mUseCase = new GetForecast(scheduler, scheduler, mDisposable, repository);
    }

    @Test
    public void getForecast() {
        mUseCase.execute(GetForecast.Params.forLonLat(anyDouble(), anyDouble()))
                .test()
                .assertValue(forecast ->
                        forecast.getIcon().equals("clear-day")
                                && forecast.getSummary().equals("Clear")
                                && forecast.getTimezone().equals("Europe/Stockholm")
                                && forecast.getTemperature() == 40.46
                                && forecast.getLongitude() == 18.0706638
                                && forecast.getLatitude() == 59.3310373);

        mUseCase.dispose();
        assertThat(mDisposable.isDisposed(), is(true));
    }
}
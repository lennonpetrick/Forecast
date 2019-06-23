package com.test.forecast.presentation;

import com.test.forecast.BaseTest;
import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.domain.Forecast;
import com.test.forecast.domain.mapper.ForecastMapper;
import com.test.forecast.domain.usecase.GetForecast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest extends BaseTest {

    private MainContract.Presenter mPresenter;

    @Mock
    private MainContract.View mView;

    @Mock
    private GetForecast mUseCase;

    @Before
    public void setUp() {
        mPresenter = new MainPresenter(mUseCase);
        mPresenter.setView(mView);
    }

    @Test
    public void getForecast() throws Exception {
        Forecast model = ForecastMapper.transformForecast(
                createObjectFromFile("Forecast.json", ForecastEntity.class));

        when(mUseCase.execute(any())).thenReturn(Observable.just(model));

        mPresenter.getForecast(0, 0);

        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).setWeatherBackground(anyString());
        verify(mView).setWeatherIcon(anyString());
        verify(mView).setWeatherDescription(anyString());
        verify(mView).setWeatherTemperature(anyInt());
        verify(mView).setTimezone(anyString());
        verify(mView).setDateInfo(anyString(), anyString(), anyInt(), anyInt(), anyInt());
        verify(mView, never()).showError(anyString());
    }

    @Test
    public void getForecast_error() {
        when(mUseCase.execute(any())).thenReturn(Observable.error(new Exception("Error")));

        mPresenter.getForecast(0, 0);

        verify(mView).showLoading();
        verify(mView, never()).setWeatherBackground(anyString());
        verify(mView, never()).setWeatherIcon(anyString());
        verify(mView, never()).setWeatherDescription(anyString());
        verify(mView, never()).setWeatherTemperature(anyInt());
        verify(mView, never()).setTimezone(anyString());
        verify(mView, never()).setDateInfo(anyString(), anyString(),
                anyInt(), anyInt(), anyInt());
        verify(mView).showError(anyString());
    }
}
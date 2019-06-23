package com.test.forecast.domain.mapper;

import com.test.forecast.BaseTest;
import com.test.forecast.data.entities.ForecastEntity;
import com.test.forecast.data.entities.WeatherEntity;
import com.test.forecast.domain.Forecast;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ForecastMapperTest extends BaseTest {

    @Test
    public void transformForecast() throws IOException {
        ForecastEntity entity = createObjectFromFile(
                "Forecast.json", ForecastEntity.class);

        Forecast model = ForecastMapper.transformForecast(entity);

        assertThat(model.getLongitude(), is(entity.getLongitude()));
        assertThat(model.getLatitude(), is(entity.getLatitude()));
        assertThat(model.getTimezone(), is(entity.getTimezone()));

        WeatherEntity weatherEntity = entity.getWeather();
        assertThat(model.getIcon(), is(weatherEntity.getIcon()));
        assertThat(model.getSummary(), is(weatherEntity.getSummary()));
        assertThat(model.getTemperature(), is(weatherEntity.getTemperature()));
    }
}
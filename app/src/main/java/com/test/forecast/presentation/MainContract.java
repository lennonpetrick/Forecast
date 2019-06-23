package com.test.forecast.presentation;

import androidx.annotation.Nullable;

public interface MainContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void setWeatherBackground(@Nullable String theme);
        void setWeatherIcon(@Nullable String theme);
        void setWeatherDescription(String description);
        void setWeatherTemperature(int temperature);
        void setTimezone(String timezone);
        void setDateInfo(String dayOfWeek, String month, int day, int hour, int minute);
    }

    interface Presenter {
        void setView(View view);
        void destroy();
        void fetchCurrentWeather(double lon, double lat);
    }
}

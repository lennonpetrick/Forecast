package com.test.forecast.presentation;

import android.util.SparseArray;

import com.test.forecast.domain.Forecast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private SparseArray<String> mWeekMap,
                                mMonthMap;

    @Inject
    public MainPresenter() { }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void destroy() {
        mView = null;
        mWeekMap = null;
        mMonthMap = null;
    }

    @Override
    public void fetchCurrentWeather(double lon, double lat) {
        Forecast forecast = new Forecast();
        forecast.setIcon("clear-day");
        forecast.setSummary("Clear");
        forecast.setTemperature(40.46);
        forecast.setTimezone("Europe/Stockholm");

        displayForecast(forecast);
    }

    private void displayForecast(Forecast forecast) {
        final String icon = forecast.getIcon();
        mView.setWeatherBackground(icon);
        mView.setWeatherIcon(icon);

        mView.setWeatherDescription(forecast.getSummary());
        mView.setWeatherTemperature((int) Math.round(forecast.getTemperature()));
        mView.setTimezone(forecast.getTimezone());

        GregorianCalendar calendar = new GregorianCalendar();
        mView.setDateInfo(
                getDayOfWeekText(calendar.get(Calendar.DAY_OF_WEEK)),
                getMonthText(calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE)
        );
    }

    private String getDayOfWeekText(int day) {
        if (mWeekMap == null) {
            initWeekMap();
        }

        return mWeekMap.get(day);
    }

    private String getMonthText(int month) {
        if (mMonthMap == null) {
            initMonthMap();
        }

        return mMonthMap.get(month);
    }

    private void initWeekMap() {
        mWeekMap = new SparseArray<>();
        mWeekMap.put(Calendar.SUNDAY, "Sunday");
        mWeekMap.put(Calendar.MONDAY, "Monday");
        mWeekMap.put(Calendar.TUESDAY, "Tuesday");
        mWeekMap.put(Calendar.WEDNESDAY, "Wednesday");
        mWeekMap.put(Calendar.THURSDAY, "Thursday");
        mWeekMap.put(Calendar.FRIDAY, "Friday");
        mWeekMap.put(Calendar.SATURDAY, "Saturday");
    }

    private void initMonthMap() {
        mMonthMap = new SparseArray<>();
        mMonthMap.put(Calendar.JANUARY, "January");
        mMonthMap.put(Calendar.FEBRUARY, "February");
        mMonthMap.put(Calendar.MARCH, "March");
        mMonthMap.put(Calendar.APRIL, "April");
        mMonthMap.put(Calendar.MAY, "May");
        mMonthMap.put(Calendar.JUNE, "June");
        mMonthMap.put(Calendar.JULY, "July");
        mMonthMap.put(Calendar.AUGUST, "August");
        mMonthMap.put(Calendar.SEPTEMBER, "September");
        mMonthMap.put(Calendar.OCTOBER, "October");
        mMonthMap.put(Calendar.NOVEMBER, "November");
        mMonthMap.put(Calendar.DECEMBER, "December");
    }
}

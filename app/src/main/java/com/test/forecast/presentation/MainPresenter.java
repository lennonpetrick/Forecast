package com.test.forecast.presentation;

import android.location.Location;
import android.os.Parcel;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.test.forecast.domain.Forecast;
import com.test.forecast.domain.usecase.GetForecast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class MainPresenter implements MainContract.Presenter {

    private GetForecast mGetForecast;
    private Forecast mCurrentForecast;

    private MainContract.View mView;

    private SparseArray<String> mWeekMap,
                                mMonthMap;

    @Inject
    public MainPresenter(@NonNull GetForecast useCase) {
        this.mGetForecast = useCase;
    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void destroy() {
        mGetForecast.dispose();
        mGetForecast = null;
        mView = null;
        mWeekMap = null;
        mMonthMap = null;
        mCurrentForecast = null;
    }

    @Override
    public void getForecast(double lon, double lat) {
        if (isLonLatCloseToCurrent(lon, lat)) {
            displayForecast(mCurrentForecast);
        } else {
            fetchForecast(lon, lat);
        }
    }

    @Override
    public MainContract.State getState() {
        return new State(this);
    }

    @Override
    public void restoreState(MainContract.State state) {
        ((State) state).restore(this);
    }

    /**
     * Checks if the latitude and longitude is close enough to the current.
     *
     * @return true if the distance is between 10 kilometers, false otherwise.
     * */
    private boolean isLonLatCloseToCurrent(double lon, double lat) {
        if (mCurrentForecast == null) {
            return false;
        }

        float[] results = new float[1];

        Location.distanceBetween(
                mCurrentForecast.getLatitude(),
                mCurrentForecast.getLongitude(),
                lon,
                lat,
                results
        );

        float distance = results[0] / 1000;
        return distance < 10;
    }

    private void fetchForecast(double lon, double lat) {
        if (mView == null)
            return;

        mView.showLoading();
        mGetForecast
                .execute(GetForecast.Params.forLonLat(lon, lat))
                .subscribe(new DisposableObserver<Forecast>() {

                    @Override
                    public void onNext(Forecast forecast) {
                        mCurrentForecast = forecast;
                        displayForecast(forecast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    private void displayForecast(Forecast forecast) {
        if (mView == null)
            return;

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

    /**
     * Internal class used to expose this presenter's state to its view when
     * onSaveInstanceState is called.
     * */
    public static class State implements MainContract.State {

        private Forecast item;

        private State(MainPresenter presenter) {
            this.item = presenter.mCurrentForecast;
        }

        private State(Parcel in) {
            item = in.readParcelable(Forecast.class.getClassLoader());
        }

        private void restore(MainPresenter presenter) {
            presenter.mCurrentForecast = item;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(item, flags);
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }
}

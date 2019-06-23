package com.test.forecast.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.test.forecast.R;
import com.test.forecast.utils.LocationHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int ENABLE_LOCATION_REQUEST_CODE = 2;

    private static final String PRESENTER_STATE = "presenter_state";

    @Inject public MainContract.Presenter mPresenter;
    @Inject public LocationHelper mLocationHelper;

    private ProgressDialog mDialog;

    private TextView mTvTemperature,
                     mTvTemperatureSymbol,
                     mTvTimezone,
                     mTvDate,
                     mTvWeatherDescription;

    private ImageView mIvWeatherIcon;
    private View mTopContent;

    private Map<String, Integer> mIconMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        if (savedInstanceState != null) {
            restoreViewState(savedInstanceState);
        }

        mPresenter.setView(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MainContract.State state = mPresenter.getState();
        if (state != null) {
            outState.putParcelable(PRESENTER_STATE, state);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchByLocation();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_LOCATION_REQUEST_CODE) {
            fetchByLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mLocationHelper.isPermissionGranted(grantResults)) {
            fetchByLocation();
        }
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        mPresenter.destroy();
        mPresenter = null;
        mIconMap = null;
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        showLoading(R.string.progress_please_wait);
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void showError(String message) {
        hideLoading();

        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.error_default);
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setWeatherBackground(@Nullable String theme) {
        setBackground(getBackgroundDrawable(theme), getBackgroundColor(theme));
    }

    @Override
    public void setWeatherIcon(@Nullable String theme) {
        mIvWeatherIcon.setImageResource(getIcon(theme));
    }

    @Override
    public void setWeatherDescription(String description) {
        mTvWeatherDescription.setText(description);
    }

    @Override
    public void setWeatherTemperature(int temperature) {
        mTvTemperature.setText(String.valueOf(temperature));
        mTvTemperatureSymbol.setText(getString(R.string.text_weather_screen_temperature_symbol));
    }

    @Override
    public void setTimezone(String timezone) {
        mTvTimezone.setText(timezone);
    }

    @Override
    public void setDateInfo(String dayOfWeek, String month, int day, int hour, int minute) {
        final String descriptionDate = String
                .format(
                        Locale.getDefault(),
                        getString(R.string.text_weather_screen_date),
                        dayOfWeek, month, day, hour, minute
                );

        mTvDate.setText(descriptionDate);
    }

    private void bindViews() {
        mTvTemperature = findViewById(R.id.tvTemperature);
        mTvTemperatureSymbol = findViewById(R.id.tvTemperatureSymbol);
        mTvTimezone = findViewById(R.id.tvTimezone);
        mTvDate = findViewById(R.id.tvDate);
        mTvWeatherDescription = findViewById(R.id.tvWeatherDescription);
        mIvWeatherIcon = findViewById(R.id.ivWeatherIcon);
        mTopContent = findViewById(R.id.topContent);
    }

    private void restoreViewState(Bundle state) {
        if (state.containsKey(PRESENTER_STATE)) {
            mPresenter.restoreState(state.getParcelable(PRESENTER_STATE));
        }
    }

    private void setBackground(@DrawableRes int drawable,
                               @ColorRes int colorRes) {
        final int color;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getColor(colorRes);
        } else {
            color = getResources().getColor(colorRes);
        }

        getWindow().setStatusBarColor(color);
        mTopContent.setBackgroundResource(drawable);
    }

    private Map<String, Integer> createIconMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("clear-day", R.drawable.clear_day);
        map.put("clear-night", R.drawable.clear_night);
        map.put("rain", R.drawable.rain);
        map.put("snow", R.drawable.snow);
        map.put("wind", R.drawable.wind);
        map.put("fog", R.drawable.fog);
        map.put("cloudy", R.drawable.cloudy);
        map.put("partly-cloudy-day", R.drawable.cloudy_day);
        map.put("partly-cloudy-night", R.drawable.cloudy_night);
        map.put("hail", R.drawable.hail);
        map.put("sleet", R.drawable.hail);
        map.put("thunderstorm", R.drawable.thunderstorm);
        map.put("tornado", R.drawable.tornado);
        return map;
    }

    @DrawableRes
    private int getIcon(@Nullable String key) {
        if (mIconMap == null) {
            mIconMap = createIconMap();
        }

        Integer drawable = mIconMap.get(key);
        return drawable == null ? 0 : drawable;
    }

    @DrawableRes
    private int getBackgroundDrawable(@Nullable String key) {
        if (key == null) {
            key = "";
        }

        switch (key) {
            case "clear-day": return R.drawable.day_background;
            case "clear-night": return R.drawable.night_background;
            default: return R.drawable.dark_background;
        }
    }

    @ColorRes
    private int getBackgroundColor(@Nullable String key) {
        if (key == null) {
            key = "";
        }

        switch (key) {
            case "clear-day": return R.color.background_day_start;
            case "clear-night": return R.color.background_night_start;
            default: return R.color.background_dark_start;
        }
    }

    private void fetchByLocation() {
        if (!mLocationHelper.hasPermission(this)) {
            mLocationHelper.requestPermission(this,
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        if (!mLocationHelper.isLocationAvailable(this,
                ENABLE_LOCATION_REQUEST_CODE)) {
            return;
        }

        showLoading(R.string.progress_fetching_location);
        mLocationHelper.requestLocation((lon, lat) -> {
            hideLoading();
            mPresenter.getForecast(lon, lat);
        });
    }

    private void showLoading(@StringRes int resId) {
        hideLoading();

        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(resId));
        mDialog.setCancelable(false);
        mDialog.show();
    }
}

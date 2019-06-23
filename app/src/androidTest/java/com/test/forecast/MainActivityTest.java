package com.test.forecast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.test.forecast.presentation.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void get_location() {
        onView(withId(R.id.tvTemperature))
                .check(matches(withText("40")));

        onView(withId(R.id.tvTimezone))
                .check(matches(withText("Europe/Stockholm")));

        onView(withId(R.id.tvWeatherDescription))
                .check(matches(withText("Clear")));
    }
}

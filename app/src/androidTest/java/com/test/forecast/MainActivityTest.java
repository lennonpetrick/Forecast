package com.test.forecast;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.test.forecast.presentation.MainActivity;
import com.test.forecast.utils.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
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

    @Before
    public void setUp() {
        IdlingRegistry
                .getInstance()
                .register(EspressoIdlingResource.get());
    }

    @After
    public void tearDown() {
        IdlingRegistry
                .getInstance()
                .unregister(EspressoIdlingResource.get());
    }

    @Test
    public void get_location() {
        onView(withId(R.id.tvTimezone))
                .check(matches(withText("Europe/Stockholm")));
    }
}

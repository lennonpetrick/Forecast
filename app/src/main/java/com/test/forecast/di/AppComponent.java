package com.test.forecast.di;

import android.app.Application;

import com.test.forecast.MainApplication;
import com.test.forecast.di.modules.ActivityModule;
import com.test.forecast.di.modules.AppModule;
import com.test.forecast.di.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        PresenterModule.class,
        AppModule.class
})
public interface AppComponent extends AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MainApplication application);
}
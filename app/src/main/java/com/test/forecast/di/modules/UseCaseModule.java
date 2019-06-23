package com.test.forecast.di.modules;

import com.test.forecast.di.qualifiers.UIScheduler;
import com.test.forecast.di.qualifiers.WorkScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    @WorkScheduler
    Scheduler workScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @UIScheduler
    Scheduler uiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }

}

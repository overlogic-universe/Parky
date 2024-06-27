package com.lucky7.parky;

import android.app.Application;

import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.core.di.AuthModule;
import com.lucky7.parky.core.di.DaggerAppComponent;
import com.lucky7.parky.core.di.FirebaseModule;
import com.lucky7.parky.core.di.ParkHistoryModule;
import com.lucky7.parky.core.di.SharedPreferencesModule;
import com.lucky7.parky.core.di.UserModule;

public class MyApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .firebaseModule(new FirebaseModule())
                .authModule(new AuthModule())
                .userModule(new UserModule())
                .parkHistoryModule(new ParkHistoryModule())
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build();

        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

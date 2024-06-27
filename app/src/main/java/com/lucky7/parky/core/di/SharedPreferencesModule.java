package com.lucky7.parky.core.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.lucky7.parky.core.constant.shared_preference.SharedPreferenceConstant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {
    private final Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SharedPreferenceConstant.PREFS_NAME, Context.MODE_PRIVATE);
    }
}

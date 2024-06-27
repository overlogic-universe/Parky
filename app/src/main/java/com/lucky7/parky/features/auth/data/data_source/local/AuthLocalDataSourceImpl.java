package com.lucky7.parky.features.auth.data.data_source.local;


import android.content.SharedPreferences;

import com.lucky7.parky.core.constant.shared_preference.SharedPreferenceConstant;

import javax.inject.Inject;

public class AuthLocalDataSourceImpl implements AuthLocalDataSource {
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public AuthLocalDataSourceImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
    @Override
    public void saveLoginStatus(boolean isLoggedIn, String userType, String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SharedPreferenceConstant.KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(SharedPreferenceConstant.KEY_USER_TYPE, userType);
        editor.putString(SharedPreferenceConstant.KEY_USER_ID, userId);
        editor.apply();
    }

    @Override
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(SharedPreferenceConstant.KEY_IS_LOGGED_IN, false);
    }

    @Override
    public String getUserType() {
        return sharedPreferences.getString(SharedPreferenceConstant.KEY_USER_TYPE, "");
    }

    @Override
    public String getUserId() {
        return sharedPreferences.getString(SharedPreferenceConstant.KEY_USER_ID, "");
    }

    @Override
    public void clearLoginStatus() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SharedPreferenceConstant.KEY_IS_LOGGED_IN);
        editor.remove(SharedPreferenceConstant.KEY_USER_TYPE);
        editor.remove(SharedPreferenceConstant.KEY_USER_ID);
        editor.apply();
    }
}


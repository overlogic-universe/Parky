package com.lucky7.parky.features.auth.data.data_source.local;

import android.content.SharedPreferences;

import javax.inject.Inject;

public interface AuthLocalDataSource {
    public void saveLoginStatus(boolean isLoggedIn, String userType, String userId);
    public boolean isLoggedIn();
    public String getUserType();
    public String getUserId();
    public void clearLoginStatus();
}

package com.lucky7.parky.features.auth.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.constant.shared_preference.SharedPreferenceConstant;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;

import javax.inject.Inject;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Inject
    AuthRepository authRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
                final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                        splashScreenView,
                        View.TRANSLATION_Y,
                        0f,
                        -splashScreenView.getHeight()
                );
                slideUp.setInterpolator(new AnticipateInterpolator());
                slideUp.setDuration(200L);

                slideUp.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        splashScreenView.remove();
                    }
                });

                slideUp.start();
            });
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginStatus();
            }
        }, 3000);
    }

    private void checkLoginStatus() {

        boolean isLoggedIn = authRepository.isLoggedIn();
        String userType = authRepository.getUserType();
        String userId = authRepository.getUserId();
//        Log.d("FREEE", "=====================: " );
//        Log.d("FREEE", "userType: "  + userType);
//        Log.d("FREEE", "userId: " + userId);
//        Log.d("FREEE", "isLoggedIn: " + isLoggedIn);

        if (isLoggedIn) {
            if (SharedPreferenceConstant.KEY_ADMIN.equals(userType)) {
                authRepository.getAdminFromFirestore(userId, new RepositoryCallback<AdminModel>() {
                    @Override
                    public void onSuccess(AdminModel adminModel) {
                        navigateToAdminHome(adminModel);
                    }

                    @Override
                    public void onError(Exception e) {
                        navigateToLogin();
                    }
                });
            } else if (SharedPreferenceConstant.KEY_USER.equals(userType)) {
                authRepository.getUserFromFirestore(userId, new RepositoryCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel userModel) {
                        navigateToUserHome(userModel);
                    }

                    @Override
                    public void onError(Exception e) {
                        navigateToLogin();
                    }
                });
            }
        } else {
            navigateToLogin();
        }
    }

    private void navigateToLogin() {
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }

    private void navigateToAdminHome(AdminModel adminModel) {
        Intent intent = new Intent(SplashScreenActivity.this, AdminHomeActivity.class);
        intent.putExtra(AdminHomeActivity.EXTRA_ADMIN, adminModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToUserHome(UserModel userModel) {
        Intent intent = new Intent(SplashScreenActivity.this, UserHomeActivity.class);
        intent.putExtra(UserHomeActivity.EXTRA_USER, userModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

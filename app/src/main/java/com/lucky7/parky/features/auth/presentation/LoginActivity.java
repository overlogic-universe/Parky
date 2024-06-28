package com.lucky7.parky.features.auth.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.callback.RepositoryCallback;

import com.lucky7.parky.core.constant.shared_preference.SharedPreferenceConstant;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;

import java.util.Objects;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    AuthRepository authRepository;
    private Button btnLogin;
    private EditText edStudentId, edPass;

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        edStudentId = findViewById(R.id.ed_student_id);
        edPass = findViewById(R.id.ed_pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);
        setContentView(R.layout.activity_login);

        initView();

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = edStudentId.getText().toString().trim();
        String pass = edPass.getText().toString().trim();

        if (v.getId() == R.id.btn_login) {
            if (!validateEmail(email) || !validatePassword(pass)) {
                showLoginFailedDialog("Please fill it in!");
            } else {
                performAdminLogin(email, pass);
            }
        }
    }

    private Boolean validateEmail(String email) {
        return !email.isEmpty();
    }

    private Boolean validatePassword(String pass) {
        return !pass.isEmpty();
    }

    private void performUserLogin(String email, String password) {
        UserModel userModel = new UserModel(email, password);
        authRepository.loginWithEmailAndPasswordUser(userModel, new RepositoryCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel result) {
                navigateToUserHome(result);
            }

            @Override
            public void onError(Exception e) {
                showLoginFailedDialog("Your email or password is wrong");
            }

        });
    }
    private void performAdminLogin(String email, String password) {

        AdminModel adminModel = new AdminModel(email, password);

        authRepository.loginWithEmailAndPasswordAdmin(adminModel, new RepositoryCallback<AdminModel>() {
            @Override
            public void onSuccess(AdminModel result) {
                navigateToAdminHome(result);
            }

            @Override
            public void onError(Exception e) {
                performUserLogin(email, password);
            }
        });
    }

    private void navigateToAdminHome(AdminModel adminModel) {
        authRepository.saveLoginStatus(true, SharedPreferenceConstant.KEY_ADMIN, adminModel.getId());
        Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
        intent.putExtra(AdminHomeActivity.EXTRA_ADMIN, adminModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void navigateToUserHome(UserModel userModel) {
        authRepository.saveLoginStatus(true, SharedPreferenceConstant.KEY_USER, userModel.getId());
        Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
        intent.putExtra(UserHomeActivity.EXTRA_USER, userModel);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showLoginFailedDialog(String message) {
        String loginFailedMessage = getResources().getString(R.string.login_failed);
        loginFailedMessage = message;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.failed_dialog);

        TextView tvLoginFailedDialog = dialog.findViewById(R.id.login_failed_dialog);
        tvLoginFailedDialog.setText(loginFailedMessage);

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }
}

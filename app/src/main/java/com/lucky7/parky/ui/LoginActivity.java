package com.lucky7.parky.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lucky7.parky.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;

    private void initView(){
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeAdminActivity.class));
        });
    }
}
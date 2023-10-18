package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lucky7.parky.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLogin;

    private void initView(){
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
            startActivity(new Intent(this, UserHomeActivity.class));
        }
    }
}
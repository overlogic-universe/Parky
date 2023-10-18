package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lucky7.parky.R;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLogin;
    private EditText edEmail, edPass;

    private void initView(){
        btnLogin = findViewById(R.id.btn_login);
        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
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
            String email = edEmail.getText().toString();
            String pass = edPass.getText().toString();
            if(email.toLowerCase(Locale.ROOT).equals("admin@gmail.com") && pass.toLowerCase(Locale.ROOT).equals("admin123")){
                startActivity(new Intent(this, AdminHomeActivity.class));
            } else if (email.toLowerCase(Locale.ROOT).equals("user@gmail.com") && pass.toLowerCase(Locale.ROOT).equals("user123")) {
                startActivity(new Intent(this, UserHomeActivity.class));
            }
        }
    }
}
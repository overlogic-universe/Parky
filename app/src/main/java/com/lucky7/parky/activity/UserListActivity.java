package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lucky7.parky.R;

public class UserListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBackToHomeAdmin;

    private void initView(){
        ivBackToHomeAdmin = findViewById(R.id.iv_back_to_home_admin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initView();

        ivBackToHomeAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back_to_home_admin){
            super.getOnBackPressedDispatcher().onBackPressed();
        }
    }
}
package com.lucky7.parky.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.lucky7.parky.R;

public class UserListActivity extends AppCompatActivity {
    private ImageView ivBackToHomeAdmin;

    private void initView(){
        ivBackToHomeAdmin = findViewById(R.id.iv_back_to_home_admin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initView();

        ivBackToHomeAdmin.setOnClickListener(v->{
            super.getOnBackPressedDispatcher().onBackPressed();
        });
    }
}
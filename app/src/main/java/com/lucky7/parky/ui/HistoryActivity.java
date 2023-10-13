package com.lucky7.parky.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.lucky7.parky.R;

public class HistoryActivity extends AppCompatActivity {
    private ImageView ivArrowBack;

    private void initView(){
        ivArrowBack = findViewById(R.id.iv_back_to_home_admin);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        ivArrowBack.setOnClickListener(v->{
            super.onBackPressed();
        });
    }
}
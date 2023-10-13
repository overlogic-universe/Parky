package com.lucky7.parky.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lucky7.parky.R;

public class HomeAdminActivity extends AppCompatActivity {
    private View viewHistory;

    private void initView(){
        viewHistory = findViewById(R.id.v_history);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initView();

        viewHistory.setOnClickListener(v->{
            startActivity(new Intent(this, HistoryActivity.class));
        });
    }
}
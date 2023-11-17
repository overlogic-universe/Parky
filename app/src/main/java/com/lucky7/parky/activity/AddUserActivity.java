package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lucky7.parky.R;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBackFromUserAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ivBackFromUserAdd = findViewById(R.id.iv_back_from_add_user);

        ivBackFromUserAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back_from_add_user){
            getOnBackPressedDispatcher().onBackPressed();
        }
    }
}
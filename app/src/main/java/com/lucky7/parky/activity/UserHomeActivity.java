package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";

    private TextView tvUsername;
    private TextView tvDate;
    private TextView tvStudentId;
    private TextView tvPlate;
    private TextView tvStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        tvUsername = findViewById(R.id.tv_username);
        tvDate = findViewById(R.id.tv_the_date);
        tvStudentId = findViewById(R.id.tv_the_nim);
        tvPlate = findViewById(R.id.tv_the_plat);
        tvStatus = findViewById(R.id.tv_the_status);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        User user;
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            user = getIntent().getParcelableExtra(EXTRA_USER, User.class);
        } else {
            user = getIntent().getParcelableExtra(EXTRA_USER);
        }

        if (user != null) {
            tvUsername.setText(user.getName());
            tvDate.setText(currentDate);
            tvStudentId.setText(user.getStudentId());
            tvPlate.setText(user.getPlate());
            tvStatus.setText(user.getParkStatus());
        }

    }
}
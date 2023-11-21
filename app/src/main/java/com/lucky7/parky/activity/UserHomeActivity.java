package com.lucky7.parky.activity;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.lucky7.parky.R;
import com.lucky7.parky.model.User;
import com.lucky7.parky.util.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    private User user;
    private TextView tvUsername;
    private TextView tvDate;
    private TextView tvStudentId;
    private TextView tvPlate;
    private TextView tvStatus;
    private ImageView ivBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        tvUsername = findViewById(R.id.tv_username);
        tvDate = findViewById(R.id.tv_the_date);
        tvStudentId = findViewById(R.id.tv_the_nim);
        tvPlate = findViewById(R.id.tv_the_plat);
        tvStatus = findViewById(R.id.tv_the_status);
        ivBarcode = findViewById(R.id.iv_barcode);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

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

        convertToBarcode();
    }

    private void convertToBarcode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(user.getStudentId(), BarcodeFormat.QR_CODE, 250, 250);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            barcodeEncoder.setBackgroundColor(Color.TRANSPARENT);
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }


    }
}
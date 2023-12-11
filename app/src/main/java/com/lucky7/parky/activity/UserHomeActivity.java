package com.lucky7.parky.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

public class UserHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_USER = "extra_user";
    private User user;
    private TextView tvUsername;
    private TextView tvDate;
    private TextView tvStudentId;
    private TextView tvPlate;
    private TextView tvStatus;
    private ImageView ivLogout;
    private ImageView ivBarcode;
    private SwipeRefreshLayout refresh;
    private String currentDate;

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
        refresh = findViewById(R.id.refresh);
        ivLogout = findViewById(R.id.iv_logout);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

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
        refresh.setOnRefreshListener(this);

        convertToBarcode();

        ivLogout.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
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

    @Override
    public void onRefresh() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("studentId").equalTo(user.getStudentId());
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            tvStatus.setText(user.getParkStatus());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            refresh.setRefreshing(false);
        }, 1000);
    }
}
package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.lucky7.parky.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QRCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        ImageView ivBackFromScanner = findViewById(R.id.iv_back_from_scanner);
        mCodeScanner = new CodeScanner(this, scannerView);

        permissionCheck();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getOnBackPressedDispatcher().onBackPressed();
                        checkUser(result.getText());
                    }
                });
            }
        });

        scannerView.setOnClickListener(this);
        ivBackFromScanner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scanner_view) {
            mCodeScanner.startPreview();

        } else if (v.getId() == R.id.iv_back_from_scanner) {
            super.getOnBackPressedDispatcher().onBackPressed();
        }
    }


    private void permissionCheck() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 12);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 12) {
            permissionCheck();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkUser(String studentId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference userRef = reference.child(studentId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String currentParkStatus = snapshot.child("parkStatus").getValue(String.class);

                    if (currentParkStatus != null) {
                        String updatedParkStatus = (currentParkStatus.equals("Parked")) ? "Not Parked" : "Parked";

                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();

                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate);
                        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(currentDate);

                        Map<String, Object> updates = new HashMap<>();
                        updates.put("parkStatus", updatedParkStatus);
                        updates.put("parkingDate", date);
                        updates.put("parkingTime", time);
                        snapshot.getRef().updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(QRCodeScannerActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(QRCodeScannerActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(QRCodeScannerActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QRCodeScannerActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}

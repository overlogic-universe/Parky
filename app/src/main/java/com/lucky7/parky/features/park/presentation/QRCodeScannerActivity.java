package com.lucky7.parky.features.park.presentation;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.zxing.Result;
import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

public class QRCodeScannerActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    AuthRepository authRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    ParkHistoryRepository parkHistoryRepository;
    private CodeScanner mCodeScanner;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);

        setContentView(R.layout.activity_qrcode_scanner);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());
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
                        checkUser(result.getText());
                        getOnBackPressedDispatcher().onBackPressed();
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

    private void checkUser(String userId) {

        userRepository.updateParkStatus(userId, currentDate, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                getUserFromFirestore(userId);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(QRCodeScannerActivity.this, "Scan failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getUserFromFirestore(String userId){

        authRepository.getUserFromFirestore(userId, new RepositoryCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel result) {
                addParkHistory(userId,  result) ;
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(QRCodeScannerActivity.this, "Scan failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addParkHistory(String userId, UserModel userModel){

        ParkHistoryModel parkHistoryModel = new ParkHistoryModel(null, userId, currentDate);
        parkHistoryRepository.addParkHistory(parkHistoryModel, userModel, new RepositoryCallback<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference result) {
                Toast.makeText(QRCodeScannerActivity.this, "Scan success", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(QRCodeScannerActivity.this, "Scan failed", Toast.LENGTH_SHORT).show();
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

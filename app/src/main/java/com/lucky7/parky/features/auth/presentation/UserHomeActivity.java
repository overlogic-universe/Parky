package com.lucky7.parky.features.auth.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.core.util.common.BarcodeEncoder;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public class UserHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    AuthRepository authRepository;
    @Inject
    UserRepository userRepository;
    public static final String EXTRA_USER = "extra_user";
    private UserModel userModel;
    private TextView tvUsername;
    private TextView tvDate;
    private TextView tvStudentId;
    private TextView tvPlate;
    private TextView tvStatus;
    private ImageView ivLogout;
    private ImageView ivBarcode;
    private ImageView ivToChangePass;
    private SwipeRefreshLayout refresh;
    private String currentDate;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);
        setContentView(R.layout.activity_user_home);

        tvUsername = findViewById(R.id.tv_username);
        tvDate = findViewById(R.id.tv_the_date);
        tvStudentId = findViewById(R.id.tv_the_nim);
        tvPlate = findViewById(R.id.tv_the_plat);
        tvStatus = findViewById(R.id.tv_the_status);
        ivBarcode = findViewById(R.id.iv_barcode);
        refresh = findViewById(R.id.refresh);
        ivLogout = findViewById(R.id.iv_logout);
        ivToChangePass = findViewById(R.id.iv_to_change_pass);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_password);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            userModel = getIntent().getParcelableExtra(EXTRA_USER, UserModel.class);
        } else {
            userModel = getIntent().getParcelableExtra(EXTRA_USER);
        }

        if (userModel != null) {
            tvUsername.setText(userModel.getName());
            tvDate.setText(currentDate);
            tvStudentId.setText(userModel.getStudentId());
            tvPlate.setText(userModel.getPlate());
            tvStatus.setText(userModel.getParkStatus());
        }
        refresh.setOnRefreshListener(this);

        convertToBarcode();

        ivLogout.setOnClickListener(view -> {
            showLogoutDialog();
        });

        ivToChangePass.setOnClickListener(v -> {
            changePass(userModel);
        });
    }

    private void changePass(UserModel userModel) {
        TextView tvSubmit = dialog.findViewById(R.id.tv_submit_change_pass);
        EditText edtNewPass = dialog.findViewById(R.id.edt_massukan_pass_baru);
        CardView cvClose = dialog.findViewById(R.id.cv_back_from_change_pass);
        TextView tvUsernameDialog = dialog.findViewById(R.id.tv_username_on_change_pass);
        TextView tvStudentIdDialog = dialog.findViewById(R.id.tv_user_id_on_change_pass);
        tvUsernameDialog.setText(userModel.getName());
        tvStudentIdDialog.setText(userModel.getStudentId());
        dialog.show();

        cvClose.setOnClickListener(view -> {
            dialog.dismiss();
            edtNewPass.setText("");
        });

        tvSubmit.setOnClickListener(v -> {
            userModel.setPassword(edtNewPass.getText().toString().trim());
            changePassword();
        });


    }

    private  void changePassword(){
        userRepository.updatePassword(userModel, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(UserHomeActivity.this, "Successfully to change password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(UserHomeActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void convertToBarcode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userModel.getId(), BarcodeFormat.QR_CODE, 250, 250);

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
        authRepository.getUserFromFirestore(userModel.getId(), new RepositoryCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel userModel) {
                tvStatus.setText(userModel.getParkStatus());
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(UserHomeActivity.this, "Failed to refresh data", Toast.LENGTH_SHORT).show();
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            refresh.setRefreshing(false);
        }, 1000);
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_logout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.tv_confirm_logout).setOnClickListener(v -> {
            logout();
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel_logout).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void logout(){
        authRepository.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
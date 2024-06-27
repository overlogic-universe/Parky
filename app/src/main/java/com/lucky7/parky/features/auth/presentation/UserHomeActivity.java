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
import com.lucky7.parky.R;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.core.util.common.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference userRef = reference.child(userModel.getStudentId());

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String existingPassword = snapshot.child("password").getValue(String.class);
                        String newPassword = edtNewPass.getText().toString().trim();

                        if (!newPassword.equals(existingPassword)) {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("password", newPassword);
                            snapshot.getRef().updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        edtNewPass.setText("");
                                        Toast.makeText(UserHomeActivity.this, "Successfully changed password", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        edtNewPass.setText("");
                                        Toast.makeText(UserHomeActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UserHomeActivity.this, "Password remains unchanged", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserHomeActivity.this, "Database error", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void convertToBarcode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(userModel.getStudentId(), BarcodeFormat.QR_CODE, 250, 250);

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
        Query checkUserDatabase = reference.orderByChild("studentId").equalTo(userModel.getStudentId());
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            tvStatus.setText(userModel.getParkStatus().toString());
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
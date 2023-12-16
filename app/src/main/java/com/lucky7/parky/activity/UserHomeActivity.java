package com.lucky7.parky.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.lucky7.parky.model.User;
import com.lucky7.parky.util.BarcodeEncoder;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.border_radius_15));
        dialog.setCancelable(false);

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

        ivToChangePass.setOnClickListener(v -> {
            changePass(user);
        });
    }

    private void changePass(User user) {
        TextView tvSubmit = dialog.findViewById(R.id.tv_submit_change_pass);
        EditText edtNewPass = dialog.findViewById(R.id.edt_massukan_pass_baru);
        TextView ivClose = dialog.findViewById(R.id.iv_close_dialog_change_pass);
        TextView tvUsername = dialog.findViewById(R.id.tv_username_on_change_pass);
        TextView tvStudentId = dialog.findViewById(R.id.tv_user_id_on_change_pass);
        tvUsername.setText(user.getName());
        tvStudentId.setText(user.getStudentId());
        dialog.show();

        ivClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        tvSubmit.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference userRef = reference.child(user.getStudentId());

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("password", edtNewPass.getText().toString().trim());
                        snapshot.getRef().updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserHomeActivity.this, "Successfully changed password", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserHomeActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
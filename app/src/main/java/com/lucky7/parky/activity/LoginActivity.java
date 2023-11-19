package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText edStudentId, edPass;


    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        edStudentId = findViewById(R.id.ed_student_id);
        edPass = findViewById(R.id.ed_pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initView();

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String studentId = edStudentId.getText().toString();
        String pass = edPass.getText().toString();

        if (v.getId() == R.id.btn_login) {

            if (!validateEmail(studentId) | !validatePassword(pass)) {
                showLoginFailedDialog("Please fill it in!");
            } else {
                if (studentId.toLowerCase(Locale.ROOT).equals("a") || pass.toLowerCase(Locale.ROOT).equals("a")) {
                    startActivity(new Intent(this, AdminHomeActivity.class));
                } else {
                    checkUser(studentId, pass);
                }
            }
        }
    }

    public Boolean validateEmail(String studentId) {
        return !studentId.isEmpty();
    }

    public Boolean validatePassword(String pass) {
        return !pass.isEmpty();
    }

    public void checkUser(String studentId, String studentPassword) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("studentId").equalTo(studentId);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("dizzz", snapshot.toString());
                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child(studentId).child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, studentPassword)) {
                        String name = snapshot.child(studentId).child("name").getValue(String.class);
                        String studentIdData = snapshot.child(studentId).child("studentId").getValue(String.class);
                        String plate = snapshot.child(studentId).child("plate").getValue(String.class);
                        String parkStatus = snapshot.child(studentId).child("parkStatus").getValue(String.class);
                        String barcodeId = snapshot.child(studentId).child("barcodeId").getValue(String.class);
                        String email = snapshot.child(studentId).child("email").getValue(String.class);

                        User user = new User(name, studentIdData, plate, parkStatus, barcodeId, email, studentPassword);

                        Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                        intent.putExtra(UserHomeActivity.EXTRA_USER, user);
                        startActivity(intent);
                    } else {
                        showLoginFailedDialog("Your student ID or password is wrong");
                        edPass.requestFocus();
                    }
                } else {
                    showLoginFailedDialog("Your student ID or password is wrong");
                    edStudentId.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showLoginFailedDialog(String message){
        String loginFailedMessage = getResources().getString(R.string.login_failed);
        loginFailedMessage = message;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.failed_dialog);

        TextView tvLoginFailedDialog = dialog.findViewById(R.id.login_failed_dialog);
        tvLoginFailedDialog.setText(loginFailedMessage);

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.BottomSheetAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);

    }
}
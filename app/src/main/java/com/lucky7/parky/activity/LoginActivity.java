package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText edEmail, edPass;

    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        edEmail = findViewById(R.id.ed_email);
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
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");

        User user = new User(email, pass);

        if (v.getId() == R.id.btn_login) {

            if (!validateEmail(email) | !validatePassword(pass)) {

            } else {
                if (email.toLowerCase(Locale.ROOT).equals("a") || pass.toLowerCase(Locale.ROOT).equals("a")) {
                    startActivity(new Intent(this, AdminHomeActivity.class));

                } else {
                    checkUser(email, pass);
                }
            }


        }
    }

    public Boolean validateEmail(String email) {
        if (email.isEmpty()) {
            edEmail.setError("Email cannot be empty");
            return false;
        } else {
            edEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(String pass) {
        if (pass.isEmpty()) {
            edEmail.setError("Password cannot be empty");
            return false;
        } else {
            edEmail.setError(null);
            return true;
        }
    }

    public void checkUser(String userEmail, String userPassword) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    edEmail.setError(null);
                    String passwordFromDB = snapshot.child(userEmail.toLowerCase()).child("password").getValue(String.class);

                    if (!Objects.equals(passwordFromDB, userPassword.toLowerCase())) {
                        edEmail.setError(null);
                        Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                    } else {
                        edPass.setError("Invalid Credentials");
                        edPass.requestFocus();
                    }
                } else {
                    edEmail.setError("User does not exist");
                    edEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBackFromUserAdd;
    private EditText edUsername;
    private EditText edNIM;
    private EditText edPlate;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        ivBackFromUserAdd = findViewById(R.id.iv_back_from_add_user);
        edUsername = findViewById(R.id.ed_username);
        edNIM = findViewById(R.id.ed_user_nim);
        edPlate = findViewById(R.id.ed_user_plate);
        edEmail = findViewById(R.id.ed_user_email);
        edPassword = findViewById(R.id.ed_user_pass);
        btnAddUser = findViewById(R.id.btn_add_user);

        ivBackFromUserAdd.setOnClickListener(this);
        btnAddUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_from_add_user) {
            getOnBackPressedDispatcher().onBackPressed();
        } else if (v.getId() == R.id.btn_add_user) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            String username =  edUsername.getText().toString();
            String NIM =  edNIM.getText().toString();
            String plate =  edPlate.getText().toString();
            String email =  edEmail.getText().toString();
            String password =  edPassword.getText().toString();

            User user = new User(username,NIM, plate, email, password );
            reference.child("users").child(email).setValue(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(this, AdminHomeActivity.class);
                            startActivity(intent);
                            Toast.makeText(this, "Berhasil menambahkan user.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Gagal menambahkan user.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("add user error", "Gagal menambahkan user: " + e.getMessage());
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
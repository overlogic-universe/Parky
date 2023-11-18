package com.lucky7.parky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users");

            String username =  edUsername.getText().toString();
            String NIM =  edNIM.getText().toString();
            String plate =  edPlate.getText().toString();
            String email =  edEmail.getText().toString();
            String password =  edPassword.getText().toString();

            User userClass = new User(username,NIM, plate, email, password );
            reference.child(username).setValue(userClass);
            Intent intent = new Intent(AddUserActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        }
    }
}
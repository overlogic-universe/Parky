package com.lucky7.parky.features.auth.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Inject;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {

    @Inject
    AuthRepository authRepository;
    private ImageView ivBackFromUserAdd;
    private EditText edUsername;
    private EditText edStudentId;
    private EditText edPlate;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);
        setContentView(R.layout.activity_add_user);

        ivBackFromUserAdd = findViewById(R.id.iv_back_from_add_user);
        edUsername = findViewById(R.id.ed_username);
        edStudentId = findViewById(R.id.ed_user_student_id);
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

            String username = edUsername.getText().toString();
            String studentId = edStudentId.getText().toString();
            String plate = edPlate.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();

            UserModel userModel = new UserModel(null,username, studentId, plate, ParkStatus.NOT_PARKED,null, email, password);

            authRepository.signUpWithEmailAndPasswordUser(userModel, new RepositoryCallback<UserModel>() {
                @Override
                public void onSuccess(UserModel result) {
                    Toast.makeText(AddUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    getOnBackPressedDispatcher().onBackPressed();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(AddUserActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
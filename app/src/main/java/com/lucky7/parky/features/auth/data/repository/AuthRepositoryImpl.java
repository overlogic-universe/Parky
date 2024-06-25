package com.lucky7.parky.features.auth.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.lucky7.parky.features.auth.data.data_resource.remote.AuthRemoteDataResource;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResource;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthRemoteDataResource authRemoteDataResource;

    @Inject
    public AuthRepositoryImpl(AuthRemoteDataResource authRemoteDataResource) {
        this.authRemoteDataResource = authRemoteDataResource;
    }


    @Override
    public void loginWithEmailAndPasswordAdmin(AdminModel adminModel) {
        authRemoteDataResource.loginWithEmailAndPasswordAdmin(adminModel)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Handle success (e.g., log user in, retrieve user data)
                        } else {
                            // Handle error (e.g., show error message)
                        }
                    }
                });
    }

    @Override
    public void loginWithEmailAndPasswordUser(UserModel userModel) {
        authRemoteDataResource.loginWithEmailAndPasswordUser(userModel)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Handle success
                        } else {
                            // Handle error
                        }
                    }
                });
    }

    @Override
    public void logout() {
        authRemoteDataResource.logout();
    }
}

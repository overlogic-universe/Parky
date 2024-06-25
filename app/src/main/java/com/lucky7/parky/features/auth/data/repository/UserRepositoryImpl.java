package com.lucky7.parky.features.auth.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResource;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {
    private final UserRemoteDataResource userRemoteDataResource;

    @Inject
    public UserRepositoryImpl(UserRemoteDataResource userRemoteDataResource) {
        this.userRemoteDataResource = userRemoteDataResource;
    }

    @Override
    public void addUser(UserModel userModel) {
        userRemoteDataResource.addUser(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Handle success (e.g., user added successfully)
                } else {
                    // Handle error
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle specific error cases
                    }
                }
            }
        });
    }

    @Override
    public void deleteUser(UserModel userModel) {
        userRemoteDataResource.deleteUser(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Handle success (e.g., user deleted successfully)
                } else {
                    // Handle error
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle specific error cases
                    }
                }
            }
        });
    }

    @Override
    public void updatePassword(UserModel userModel) {
        userRemoteDataResource.updatePassword(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Handle success (e.g., password updated successfully)
                } else {
                    // Handle error
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle specific error cases
                    }
                }
            }
        });
    }

    @Override
    public void updateParkStatus(UserModel userModel) {
        userRemoteDataResource.updateParkStatus(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Handle success (e.g., park status updated successfully)
                } else {
                    // Handle error
                    Exception exception = task.getException();
                    if (exception != null) {
                        // Handle specific error cases
                    }
                }
            }
        });
    }
}

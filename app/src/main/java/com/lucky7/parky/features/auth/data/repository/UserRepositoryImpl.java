package com.lucky7.parky.features.auth.data.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSource;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {
    private final UserRemoteDataSource userRemoteDataSource;

    @Inject
    public UserRepositoryImpl(UserRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public Task<Void> addUser(UserModel userModel, RepositoryCallback<UserModel> callback) {
        return userRemoteDataSource.addUser(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(userModel);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public Task<Void> deleteUser(UserModel userModel, RepositoryCallback<Void> callback) {
        return userRemoteDataSource.deleteUser(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public Task<Void> updatePassword(UserModel userModel, RepositoryCallback<Void> callback) {
        return userRemoteDataSource.updatePassword(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public Task<Void> updateParkStatus(UserModel userModel, RepositoryCallback<Void> callback) {
        return userRemoteDataSource.updateParkStatus(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }
}

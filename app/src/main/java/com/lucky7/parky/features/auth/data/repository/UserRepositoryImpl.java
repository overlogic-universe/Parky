package com.lucky7.parky.features.auth.data.repository;

import com.lucky7.parky.core.callback.RepositoryCallback;
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
    public void addUser(UserModel userModel, RepositoryCallback<Void> callback) {
        userRemoteDataSource.addUser(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public void deleteUser(UserModel userModel, RepositoryCallback<Void> callback) {
        userRemoteDataSource.deleteUser(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public void updatePassword(UserModel userModel, RepositoryCallback<Void> callback) {
        userRemoteDataSource.updatePassword(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    @Override
    public void updateParkStatus(UserModel userModel, RepositoryCallback<Void> callback) {
        userRemoteDataSource.updateParkStatus(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(null);
            } else {
                callback.onError(task.getException());
            }
        });
    }
}

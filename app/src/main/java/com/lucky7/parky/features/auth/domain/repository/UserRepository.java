package com.lucky7.parky.features.auth.domain.repository;

import com.google.android.gms.tasks.Task;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface UserRepository {
    void addUser(UserModel userModel, RepositoryCallback<Void> callback);
    void deleteUser(UserModel userModel, RepositoryCallback<Void> callback);
    void updatePassword(UserModel userModel, RepositoryCallback<Void> callback);
    void updateParkStatus(UserModel userModel, RepositoryCallback<Void> callback);
}

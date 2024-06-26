package com.lucky7.parky.features.auth.domain.repository;

import com.google.android.gms.tasks.Task;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface UserRepository {
    Task<Void> addUser(UserModel userModel,RepositoryCallback<UserModel> callback);
    Task<Void> deleteUser(UserModel userModel, RepositoryCallback<Void> callback);
    Task<Void> updatePassword(UserModel userModel, RepositoryCallback<Void> callback);
    Task<Void> updateParkStatus(UserModel userModel, RepositoryCallback<Void> callback);
}

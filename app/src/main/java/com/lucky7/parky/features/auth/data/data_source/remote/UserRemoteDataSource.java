package com.lucky7.parky.features.auth.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface UserRemoteDataSource {
    Task<Void> addUser(UserModel userModel);
    Task<Void> deleteUser(UserModel userModel);
    Task<Void> updatePassword(UserModel userModel);
    Task<Void> updateParkStatus(UserModel userModel);
}

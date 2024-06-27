package com.lucky7.parky.features.auth.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.List;

public interface UserRemoteDataSource {
    Task<List<UserModel>> getAllUsers();
    Task<Void> addUser(UserModel userModel);
    Task<Void> deleteUser(UserModel userModel);
    Task<Void> updatePassword(UserModel userModel);
    Task<Void> updateParkStatus(String userId, String currentDateTime);
}

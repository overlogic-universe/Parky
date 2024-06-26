package com.lucky7.parky.features.auth.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

import javax.inject.Inject;

public interface AuthRemoteDataSource {
    Task<AuthResult> loginWithEmailAndPasswordAdmin(AdminModel adminModel);
    Task<AuthResult> loginWithEmailAndPasswordUser(UserModel userModel);
    Task<QuerySnapshot> getUserFromFirestore(String userId);
    Task<QuerySnapshot> getAdminFromFirestore(String adminId);

    void logout();
}

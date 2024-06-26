package com.lucky7.parky.features.auth.domain.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface AuthRepository {
     Task<AdminModel> loginWithEmailAndPasswordAdmin(AdminModel adminModel, RepositoryCallback<AdminModel> callback);
     Task<UserModel> loginWithEmailAndPasswordUser(UserModel userModel, RepositoryCallback<UserModel> callback);
     Task<Void> signUpWithEmailAndPasswordUser(UserModel userModel, RepositoryCallback<UserModel> callback);
     Task<UserModel> getUserFromFirestore(String userId, RepositoryCallback<UserModel> callback);
     Task<AdminModel> getAdminFromFirestore(String adminId, RepositoryCallback<AdminModel> callback);
     void logout();
}

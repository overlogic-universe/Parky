package com.lucky7.parky.features.auth.data.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSource;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;

import java.util.Objects;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthRemoteDataSource authRemoteDataSource;

    @Inject
    public AuthRepositoryImpl(AuthRemoteDataSource authRemoteDataSource) {
        this.authRemoteDataSource = authRemoteDataSource;
    }

    @Override
    public Task<AdminModel> loginWithEmailAndPasswordAdmin(AdminModel adminModel, RepositoryCallback<AdminModel> callback) {
        Task<AuthResult> authTask = authRemoteDataSource.loginWithEmailAndPasswordAdmin(adminModel);
        return authTask.continueWithTask(task -> {
            if (task.isSuccessful()) {

                String adminId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                Log.d("WOWOWOWO", "BERHASILLLL".toString());

                return getAdminFromFirestore(adminId, callback);
            } else {

                callback.onError(task.getException());
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public Task<UserModel> loginWithEmailAndPasswordUser(UserModel userModel, RepositoryCallback<UserModel> callback) {
        Task<AuthResult> authTask = authRemoteDataSource.loginWithEmailAndPasswordUser(userModel);
        return authTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                return getUserFromFirestore(userId, callback);
            } else {
                callback.onError(task.getException());
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public Task<UserModel> getUserFromFirestore(String userId, RepositoryCallback<UserModel> callback) {
        Task<QuerySnapshot> userQuery = authRemoteDataSource.getUserFromFirestore(userId);
        return userQuery.continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    return querySnapshot.getDocuments().get(0).toObject(UserModel.class);
                } else {
                    throw new Exception("User not found in Firestore");
                }
            } else {
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public Task<AdminModel> getAdminFromFirestore(String adminId, RepositoryCallback<AdminModel> callback) {
        Task<QuerySnapshot> userQuery = authRemoteDataSource.getAdminFromFirestore(adminId);
        return userQuery.continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();

                if (!querySnapshot.isEmpty()) {
                    AdminModel adminModel=  querySnapshot.getDocuments().get(0).toObject(AdminModel.class);
                    assert adminModel != null;
                    adminModel.setId(adminId);
                    Log.d("WOWOWOWO", adminId + "JDKSJ" + "  jdafkjd: " + adminModel);
                    callback.onSuccess(adminModel);
                    return adminModel;
                } else {
                    callback.onError(task.getException());
                    throw new Exception("User not found in Firestore");
                }
            } else {
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public void logout() {
        authRemoteDataSource.logout();
    }
}

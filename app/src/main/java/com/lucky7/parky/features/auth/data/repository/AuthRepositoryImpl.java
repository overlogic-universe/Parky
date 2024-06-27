package com.lucky7.parky.features.auth.data.repository;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.constant.shared_preference.SharedPreferenceConstant;
import com.lucky7.parky.features.auth.data.data_source.local.AuthLocalDataSource;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSource;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import java.util.Objects;

import javax.inject.Inject;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthRemoteDataSource authRemoteDataSource;
    private final AuthLocalDataSource authLocalDataSource;
    private final UserRepository userRepository;

    @Inject
    public AuthRepositoryImpl(AuthRemoteDataSource authRemoteDataSource, AuthLocalDataSource authLocalDataSource, UserRepository userRepository) {
        this.authRemoteDataSource = authRemoteDataSource;
        this.authLocalDataSource = authLocalDataSource;
        this.userRepository = userRepository;
    }

    @Override
    public void loginWithEmailAndPasswordAdmin(AdminModel adminModel, RepositoryCallback<AdminModel> callback) {
        Task<AuthResult> authTask = authRemoteDataSource.loginWithEmailAndPasswordAdmin(adminModel);
        authTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                String adminId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                authLocalDataSource.saveLoginStatus(true, SharedPreferenceConstant.KEY_ADMIN, adminId);
                return getAdminFromFirestore(adminId, callback);
            } else {

                callback.onError(task.getException());
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public void loginWithEmailAndPasswordUser(UserModel userModel, RepositoryCallback<UserModel> callback) {
        Task<AuthResult> authTask = authRemoteDataSource.loginWithEmailAndPasswordUser(userModel);
        authTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                authLocalDataSource.saveLoginStatus(true, SharedPreferenceConstant.KEY_USER, userId);
                return getUserFromFirestore(userId, callback);
            } else {
                callback.onError(task.getException());
                throw Objects.requireNonNull(task.getException());
            }
        });
    }

    @Override
    public void signUpWithEmailAndPasswordUser(UserModel userModel, RepositoryCallback<UserModel> callback) {
        Task<AuthResult> authTask = authRemoteDataSource.signUpWithEmailAndPasswordUser(userModel);
        authTask.continueWithTask(task -> {
            if (task.isSuccessful()) {
                String userId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                userModel.setId(userId);
                return userRepository.addUser(userModel, callback);
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
                    UserModel userModel = UserModel.fromFirestore(querySnapshot.getDocuments().get(0));
                    if(userModel.getId() == null){
                        userModel.setId(userId);
                    }
                    Log.d("FREEEE", "getUserFromFirestore REPOOO: " + userModel);
                    callback.onSuccess(userModel);
                    return userModel;
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
                    AdminModel adminModel= AdminModel.fromFirestore(querySnapshot.getDocuments().get(0));
                    adminModel.setId(adminId);
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
    public boolean isLoggedIn() {
        return authLocalDataSource.isLoggedIn();
    }

    @Override
    public String getUserType() {
        return authLocalDataSource.getUserType();
    }

    @Override
    public String getUserId() {
        return authLocalDataSource.getUserId();
    }

    @Override
    public void clearLoginStatus() {
        authLocalDataSource.clearLoginStatus();
    }

    @Override
    public void logout() {
        clearLoginStatus();
        authRemoteDataSource.logout();
    }
}

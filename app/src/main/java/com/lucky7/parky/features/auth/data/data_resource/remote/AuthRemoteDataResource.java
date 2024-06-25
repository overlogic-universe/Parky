package com.lucky7.parky.features.auth.data.data_resource.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface AuthRemoteDataResource {
    Task<AuthResult> loginWithEmailAndPasswordAdmin(AdminModel adminModel);
    Task<AuthResult> loginWithEmailAndPasswordUser(UserModel userModel);
    void logout();
}

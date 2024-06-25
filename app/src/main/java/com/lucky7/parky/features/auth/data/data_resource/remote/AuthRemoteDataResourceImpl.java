package com.lucky7.parky.features.auth.data.data_resource.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

import javax.inject.Inject;

public class AuthRemoteDataResourceImpl implements AuthRemoteDataResource{
    private final FirebaseAuth auth;

    @Inject
    public AuthRemoteDataResourceImpl(FirebaseFirestore firestore, FirebaseAuth auth){
        this.auth = auth;
    }

    @Override
    public Task<AuthResult> loginWithEmailAndPasswordAdmin(AdminModel adminModel) {
        return auth.signInWithEmailAndPassword(adminModel.getEmail(), adminModel.getPassword());
    }

    @Override
    public Task<AuthResult> loginWithEmailAndPasswordUser(UserModel userModel) {
        return auth.signInWithEmailAndPassword(userModel.getEmail(), userModel.getPassword());
    }

    @Override
    public void logout() {
        auth.signOut();
    }
}

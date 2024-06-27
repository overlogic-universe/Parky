package com.lucky7.parky.features.auth.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.core.util.firestore.QueryUtil;
import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.Objects;

import javax.inject.Inject;

public class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    @Inject
    public AuthRemoteDataSourceImpl(FirebaseFirestore firestore, FirebaseAuth auth){
        this.firestore = firestore;
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
    public Task<AuthResult> signUpWithEmailAndPasswordUser(UserModel userModel) {
        return auth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword());
    }

    @Override
    public Task<QuerySnapshot> getUserFromFirestore(String userId) {
        CollectionReference usersCollection = CollectionReferenceUtil.getUsersCollection(firestore);
        return QueryUtil.whereIsEqualToUserId(FieldConstant.USER_ID, userId, usersCollection).get();
    }

    @Override
    public Task<QuerySnapshot> getAdminFromFirestore(String adminId) {
        CollectionReference adminCollection = CollectionReferenceUtil.getAdminCollection(firestore);
        return QueryUtil.whereIsEqualToAdminId(FieldConstant.ADMIN_ID, adminId, adminCollection).get();
    }

    @Override
    public void logout() {auth.signOut();}
}

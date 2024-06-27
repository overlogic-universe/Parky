package com.lucky7.parky.features.auth.data.data_source.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.core.util.firestore.DocumentReferenceUtil;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {
    @Inject
    FirebaseFirestore firestore;
    @Inject
    FirebaseAuth auth;

    @Inject
    public UserRemoteDataSourceImpl(FirebaseFirestore firestore, FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }

    @Override
    public Task<Void> addUser(UserModel userModel) {
        return CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId()).set(userModel.toFirestore());
    }

    @Override
    public Task<Void> deleteUser(UserModel userModel) {
        return CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId()).delete();
    }

    @Override
    public Task<Void> updatePassword(UserModel userModel) {
        FirebaseUser user = auth.getCurrentUser();
        Log.d("FREEE FIRE", "updatePassword: "+ userModel);
        assert user != null;
        return user.updatePassword(userModel.getPassword());
    }

    @Override
    public Task<Void> updateParkStatus(UserModel userModel) {
        final DocumentReference docRef = CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId());
        return DocumentReferenceUtil.updateUserParkStatus(docRef, userModel.getId());
    }
}

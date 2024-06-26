package com.lucky7.parky.features.auth.data.data_source.remote;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.core.util.firestore.DocumentReferenceUtil;
import com.lucky7.parky.features.auth.data.model.UserModel;

import javax.inject.Inject;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {
    @Inject
    FirebaseFirestore firestore;

    @Inject
    public UserRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<Void> addUser(UserModel userModel) {
        Log.d("WOWOWOWO", "addUser: " + userModel);
        return CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId()).set(userModel);
    }

    @Override
    public Task<Void> deleteUser(UserModel userModel) {
        return CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId()).delete();
    }

    @Override
    public Task<Void> updatePassword(UserModel userModel) {
        final DocumentReference docRef =  CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId());
        return DocumentReferenceUtil.updateUserPassword(docRef, userModel.getPassword());
    }

    @Override
    public Task<Void> updateParkStatus(UserModel userModel) {
        final DocumentReference docRef = CollectionReferenceUtil.getUsersCollection(firestore).document(userModel.getId());
        return DocumentReferenceUtil.updateUserParkStatus(docRef, userModel.getId());
    }
}

package com.lucky7.parky.features.park.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.core.util.firestore.QueryUtil;
import com.lucky7.parky.features.auth.data.model.UserModel;

import javax.inject.Inject;

public class ParkHistoryRemoteDataSourceImpl implements ParkHistoryRemoteDataSource {

    private final FirebaseFirestore firestore;

    @Inject
    public ParkHistoryRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<QuerySnapshot> getAllHistories() {
        return CollectionReferenceUtil.getParkHistoryCollection(firestore).get();
    }

    @Override
    public Task<QuerySnapshot> getUserParkHistory(UserModel userModel) {
       CollectionReference ref =  CollectionReferenceUtil.getParkHistoryCollection(firestore);
        return QueryUtil.whereIsEqualToUserId(FieldConstant.USER_ID,userModel.getId(), ref).get();
    }
}

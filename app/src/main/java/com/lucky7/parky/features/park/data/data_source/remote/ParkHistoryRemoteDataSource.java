package com.lucky7.parky.features.park.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface ParkHistoryRemoteDataSource {
    Task<QuerySnapshot> getAllHistories();
    Task<QuerySnapshot> getUserParkHistory(UserModel userModel);

}

package com.lucky7.parky.features.park.domain.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Repo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;

import java.util.List;

public interface ParkHistoryRepository {
    void getAllHistories(RepositoryCallback<List<ParkHistoryModel>> callback);
    void getUserModelsForParkHistories(List<ParkHistoryModel> parkHistories, RepositoryCallback<List<ParkHistoryModel>> callback);
    void getUserParkHistory(UserModel userModel, RepositoryCallback<QuerySnapshot> callback);
    void addParkHistory(ParkHistoryModel parkHistoryModel, UserModel userModel, RepositoryCallback<DocumentReference> callback);

}

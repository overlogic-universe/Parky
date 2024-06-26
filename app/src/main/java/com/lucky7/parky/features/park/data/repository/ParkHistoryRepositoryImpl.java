package com.lucky7.parky.features.park.data.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSource;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ParkHistoryRepositoryImpl implements ParkHistoryRepository {
    ParkHistoryRemoteDataSource parkHistoryRemoteDataSource;

    @Inject
    public ParkHistoryRepositoryImpl(ParkHistoryRemoteDataSource parkHistoryRemoteDataSource){
        this.parkHistoryRemoteDataSource = parkHistoryRemoteDataSource;
    }

    public void getAllHistories(RepositoryCallback<List<ParkHistoryModel>> callback) {
        parkHistoryRemoteDataSource.getAllHistories().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<ParkHistoryModel> parkHistories = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ParkHistoryModel parkHistory = document.toObject(ParkHistoryModel.class);
                        parkHistories.add(parkHistory);
                    }
                    callback.onSuccess(parkHistories);
                } else {
                    callback.onError(task.getException());
                }
            }
        });
    }
    @Override
    public void getUserParkHistory(UserModel userModel, RepositoryCallback<QuerySnapshot> callback) {
        parkHistoryRemoteDataSource.getUserParkHistory(userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess(task.getResult());
            } else {
                callback.onError(task.getException());
            }
        });
    }
}

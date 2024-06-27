package com.lucky7.parky.features.park.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSource;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ParkHistoryRepositoryImpl implements ParkHistoryRepository {
    ParkHistoryRemoteDataSource parkHistoryRemoteDataSource;
    AuthRepository authRepository;

    @Inject
    public ParkHistoryRepositoryImpl(ParkHistoryRemoteDataSource parkHistoryRemoteDataSource, AuthRepository authRepository){
        this.parkHistoryRemoteDataSource = parkHistoryRemoteDataSource;
        this.authRepository = authRepository;
    }

    @Override
    public void getAllHistories(RepositoryCallback<List<ParkHistoryModel>> callback) {
        parkHistoryRemoteDataSource.getAllHistories().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<ParkHistoryModel> parkHistories = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ParkHistoryModel parkHistory =ParkHistoryModel.fromFirestore(document);
                        parkHistories.add(parkHistory);
                    }
                    getUserModelsForParkHistories(parkHistories, callback);
                } else {
                    callback.onError(task.getException());
                }
        });
    }

    @Override
    public void getUserModelsForParkHistories(List<ParkHistoryModel> parkHistories, RepositoryCallback<List<ParkHistoryModel>> callback) {
        List<Task<QuerySnapshot>> tasks = new ArrayList<>();

        for (ParkHistoryModel parkHistory : parkHistories) {
            authRepository.getUserFromFirestore(parkHistory.getUserId(), new RepositoryCallback<UserModel>() {
                @Override
                public void onSuccess(UserModel result) {
                    parkHistory.setUserModel(result);
                    callback.onSuccess(parkHistories);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);
                }
            });
        }

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

    @Override
    public void addParkHistory(ParkHistoryModel parkHistoryModel, UserModel userModel, RepositoryCallback<DocumentReference> callback) {
        parkHistoryRemoteDataSource.addParkHistory(parkHistoryModel, userModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentReference documentReference = task.getResult();
                String documentId = documentReference.getId();
                parkHistoryModel.setId(documentId);
                documentReference.update(FieldConstant.PARK_HISTORY_ID, documentId)
                        .continueWith(ignored -> documentReference);
                callback.onSuccess(task.getResult());
            } else {
                callback.onError(task.getException());
            }
        });
    }
}

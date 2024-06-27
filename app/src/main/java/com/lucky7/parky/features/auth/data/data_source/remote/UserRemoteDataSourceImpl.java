package com.lucky7.parky.features.auth.data.data_source.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {
    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    @Inject
    public UserRemoteDataSourceImpl(FirebaseFirestore firestore, FirebaseAuth auth) {
        this.firestore = firestore;
        this.auth = auth;
    }

    @Override
    public Task<List<UserModel>> getAllUsers() {
        final CollectionReference usersRef = CollectionReferenceUtil.getUsersCollection(firestore);
        return usersRef.get().continueWith(task -> {
            List<UserModel> userList = new ArrayList<>();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                    UserModel userModel = UserModel.fromFirestore(documentSnapshot);
                    if (userModel != null) {
                        userList.add(userModel);
                    }
                }
                return userList;
            } else {
                throw Objects.requireNonNull(task.getException());
            }
        });
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
        assert user != null;
        return user.updatePassword(userModel.getPassword());
    }

    @Override
    public Task<Void> updateParkStatus(String userId, String currentDateTime) {
       final DocumentReference docRef =  CollectionReferenceUtil.getUsersCollection(firestore).document(userId);
       return docRef.get().continueWithTask(task -> {
           if (task.isSuccessful()) {
               ParkStatus currentStatus = ParkStatus.NOT_PARKED;
               String status = task.getResult().getString(FieldConstant.PARK_STATUS);
               if (status != null) {
                   currentStatus = ParkStatus.fromString(status);
               }
               ParkStatus newStatus = currentStatus == ParkStatus.PARKED ? ParkStatus.NOT_PARKED : ParkStatus.PARKED;

               Map<String, Object> updates = new HashMap<>();
               updates.put(FieldConstant.PARK_STATUS, newStatus.toString());
               updates.put(FieldConstant.LAST_ACTIVITY, currentDateTime);

               return docRef.update(updates).continueWithTask(updateTask -> {
                   if (updateTask.isSuccessful()) {
                       return Tasks.forResult(null);
                   } else {
                       throw Objects.requireNonNull(updateTask.getException());
                   }
               });
           } else {
               throw Objects.requireNonNull(task.getException());
           }
       });
    }
}

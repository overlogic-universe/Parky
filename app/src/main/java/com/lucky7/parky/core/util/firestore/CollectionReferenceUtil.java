package com.lucky7.parky.core.util.firestore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.core.constant.firestore.CollectionConstant;

public class CollectionReferenceUtil {
    public static CollectionReference getAdminCollection(FirebaseFirestore firestore) {
        return firestore.collection(CollectionConstant.ADMIN);
    }

    public static CollectionReference getUsersCollection(FirebaseFirestore firestore) {
        return firestore.collection(CollectionConstant.USERS);
    }

    public static CollectionReference getParkHistoryCollection(FirebaseFirestore firestore) {
        return firestore.collection(CollectionConstant.PARK_HISTORY);
    }

}

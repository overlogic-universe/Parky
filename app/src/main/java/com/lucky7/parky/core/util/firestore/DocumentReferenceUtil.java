package com.lucky7.parky.core.util.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.lucky7.parky.core.constant.firestore.FieldConstant;

public class DocumentReferenceUtil {
    public static Task<Void> updateUserPassword(DocumentReference docRef, String password) {
        return docRef.update(FieldConstant.PASSWORD, password);
    }
    public static Task<Void> updateUserParkStatus(DocumentReference docRef, String parkStatus) {
        return docRef.update(FieldConstant.PARK_STATUS, parkStatus);
    }
}

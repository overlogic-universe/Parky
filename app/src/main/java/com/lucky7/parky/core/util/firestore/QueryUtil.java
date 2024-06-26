package com.lucky7.parky.core.util.firestore;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class QueryUtil {
    public static Query whereIsEqualToUserId(String userIdField, String userId, CollectionReference collectionRef) {
        return collectionRef.whereEqualTo(userIdField, userId);
    }

    public static Query whereIsEqualToAdminId(String adminIdField, String adminId, CollectionReference collectionRef) {
        return collectionRef.whereEqualTo(adminIdField, adminId);
    }

    public static Query whereIsEqualToStudentId(String studentIdField, String studentId, CollectionReference collectionRef) {
        return collectionRef.whereEqualTo(studentIdField, studentId);
    }
}

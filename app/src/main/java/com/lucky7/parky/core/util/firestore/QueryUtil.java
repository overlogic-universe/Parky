package com.lucky7.parky.core.util.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.lucky7.parky.core.constant.firestore.FieldConstant;

public class QueryUtil {
    public static Query whereIsEqualToUserId(Query query, String userId) {
        return query.equalTo(FieldConstant.USER_ID, userId);
    }
}

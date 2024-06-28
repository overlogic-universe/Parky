package com.lucky7.parky.features.park.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class ParkHistoryModel implements Parcelable {
    private String id;
    private String userId;
    private UserModel userModel;
    private String parkDateTime;
    private String historyStatus;

    public ParkHistoryModel() {
    }

    public ParkHistoryModel(String id, String userId, UserModel userModel, String parkDateTime, String historyStatus) {
        this.id = id;
        this.userId = userId;
        this.userModel = userModel;
        this.parkDateTime = parkDateTime;
        this.historyStatus = historyStatus;
    }

    public ParkHistoryModel(String id, String userId, String parkDateTime, String historyStatus) {
        this.id = id;
        this.userId = userId;
        this.parkDateTime = parkDateTime;
        this.historyStatus = historyStatus;
    }

    protected ParkHistoryModel(Parcel in) {
        id = in.readString();
        userId = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        parkDateTime = in.readString();
        historyStatus = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @ServerTimestamp
    public String getParkDateTime() {
        return parkDateTime;
    }

    public void setParkDateTime(String parkDateTime) {
        this.parkDateTime = parkDateTime;
    }

    public String getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(String historyStatus) {
        this.historyStatus = historyStatus;
    }

    public Map<String, Object> toFirestore() {
        Map<String, Object> result = new HashMap<>();
        result.put(FieldConstant.PARK_HISTORY_ID, getId());
        result.put(FieldConstant.USER_ID, getUserId());
        result.put(FieldConstant.PARK_DATE_TIME, getParkDateTime());
        result.put(FieldConstant.HISTORY_STATUS, getHistoryStatus());
        return result;
    }

    public static ParkHistoryModel fromFirestore(DocumentSnapshot document) {
        ParkHistoryModel parkHistoryModel = new ParkHistoryModel();
        parkHistoryModel.setId(document.getString(FieldConstant.PARK_HISTORY_ID));
        parkHistoryModel.setUserId(document.getString(FieldConstant.USER_ID));
        parkHistoryModel.setParkDateTime(document.getString(FieldConstant.PARK_DATE_TIME));
        parkHistoryModel.setHistoryStatus(document.getString(FieldConstant.HISTORY_STATUS));

        return parkHistoryModel;
    }

    public static ParkHistoryModel fromString(String id, String userId, UserModel userModel, String parkDateTime, String historyStatus) {
        return new ParkHistoryModel(id, userId, userModel, parkDateTime, historyStatus);
    }

    @NonNull
    @Override
    public String toString() {
        return "ParkHistoryModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userModel=" + userModel.toString() +
                ", parkDateTime=" + parkDateTime +
                ", historyStatus=" + historyStatus +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeParcelable(userModel, flags);
        dest.writeString(parkDateTime);
        dest.writeString(historyStatus);
    }

    public static final Creator<ParkHistoryModel> CREATOR = new Creator<ParkHistoryModel>() {
        @Override
        public ParkHistoryModel createFromParcel(Parcel in) {
            return new ParkHistoryModel(in);
        }

        @Override
        public ParkHistoryModel[] newArray(int size) {
            return new ParkHistoryModel[size];
        }
    };
}

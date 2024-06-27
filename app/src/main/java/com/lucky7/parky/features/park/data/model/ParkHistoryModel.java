package com.lucky7.parky.features.park.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.auth.User;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParkHistoryModel implements Parcelable {
    private String id;
    private String userId;
    private UserModel userModel;
    private Date parkDateTime;

    public ParkHistoryModel() {
    }

    public ParkHistoryModel(String id,String userId, UserModel userModel, Date parkDateTime) {
        this.id = id;
        this.userId = userId;
        this.userModel = userModel;
        this.parkDateTime = parkDateTime;
    }
    public ParkHistoryModel(String userId, Date parkDateTime) {
        this.userId = userId;
        this.parkDateTime = parkDateTime;
    }

    protected ParkHistoryModel(Parcel in) {
        id = in.readString();
        userId = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        parkDateTime = (Date) in.readSerializable();
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
    public Date getParkDateTime() {
        return parkDateTime;
    }

    public void setParkDateTime(Date parkDateTime) {
        this.parkDateTime = parkDateTime;
    }

    public Map<String, Object> toFirestore() {
        Map<String, Object> result = new HashMap<>();
        result.put(FieldConstant.PARK_HISTORY_ID, getId());
        result.put(FieldConstant.USER_ID, getId());
        result.put(FieldConstant.PARK_DATE_TIME, getId());

        return result;
    }
    public static ParkHistoryModel fromString(String id, String userId, UserModel userModel, Date parkDateTime) {
        return new ParkHistoryModel(id, userId, userModel, parkDateTime);
    }

    @NonNull
    @Override
    public String toString() {
        return "ParkHistoryModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userModel=" + userModel.toString() +
                ", parkDateTime=" + parkDateTime +
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
        dest.writeSerializable(parkDateTime);
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

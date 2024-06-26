package com.lucky7.parky.features.park.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.auth.User;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.Date;

public class ParkHistoryModel implements Parcelable {
    private String id;
    private String userId;
    private UserModel userModel;
    private Date lastActivity;

    public ParkHistoryModel() {
    }

    public ParkHistoryModel(String id,String userId, UserModel userModel, Date lastActivity) {
        this.id = id;
        this.userId = userId;
        this.userModel = userModel;
        this.lastActivity = lastActivity;
    }
    public ParkHistoryModel(String userId, Date lastActivity) {
        this.userId = userId;
        this.lastActivity = lastActivity;
    }

    protected ParkHistoryModel(Parcel in) {
        id = in.readString();
        userId = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        lastActivity = (Date) in.readSerializable();
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
    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public static ParkHistoryModel fromString(String id, String userId, UserModel userModel, Date lastActivity) {
        return new ParkHistoryModel(id, userId, userModel, lastActivity);
    }
    @NonNull
    @Override
    public String toString() {
        return "ParkHistoryModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userModel=" + userModel.toString() +
                ", lastActivity=" + lastActivity +
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
        dest.writeSerializable(lastActivity);
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

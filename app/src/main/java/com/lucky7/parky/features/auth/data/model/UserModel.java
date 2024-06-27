package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.domain.entity.Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserModel extends Authentication implements Parcelable {
    private String studentId;
    private String plate;
    private String parkStatus;
    private String lastActivity;

    public UserModel() {
    }

    public UserModel(String email, String password) {
        super(email, password);
    }

    public UserModel(Parcel in) {
        super(in.readString(), in.readString(), in.readString());
        studentId = in.readString();
        plate = in.readString();
        parkStatus = in.readString();
        lastActivity =  in.readString();
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, String lastActivity, String email, String password) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.lastActivity = lastActivity;
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, String lastActivity, String email) {
        super(id, name, email);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.lastActivity = lastActivity;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    @NonNull
    public String toString() {
        return "UserModel{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", studentId='" + getStudentId() + '\'' +
                ", plate='" + getPlate() + '\'' +
                ", parkStatus='" + getParkStatus() + '\'' +
                ", lastActivity='" + getLastActivity() + '\'' +
                '}';
    }

    public Map<String, Object> toFirestore() {
        Map<String, Object> result = new HashMap<>();
        result.put(FieldConstant.USER_ID, getId());
        result.put(FieldConstant.NAME, getName());
        result.put(FieldConstant.EMAIL, getEmail());
        result.put(FieldConstant.STUDENT_ID, getStudentId());
        result.put(FieldConstant.PLATE, getPlate());
        result.put(FieldConstant.PARK_STATUS, getParkStatus());
        result.put(FieldConstant.LAST_ACTIVITY, getLastActivity());
        return result;
    }

    public static UserModel fromFirestore(DocumentSnapshot document) {
        if (document == null || !document.exists()) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setId(document.getString(FieldConstant.USER_ID));
        userModel.setName(document.getString(FieldConstant.NAME));
        userModel.setEmail(document.getString(FieldConstant.EMAIL));
        userModel.setStudentId(document.getString(FieldConstant.STUDENT_ID));
        userModel.setPlate(document.getString(FieldConstant.PLATE));
        userModel.setParkStatus(document.getString(FieldConstant.PARK_STATUS));
        userModel.setLastActivity(document.getString(FieldConstant.LAST_ACTIVITY));
        return userModel;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(getName());
        parcel.writeString(getEmail());
        parcel.writeString(getStudentId());
        parcel.writeString(getPlate());
        parcel.writeString(getParkStatus());
        parcel.writeSerializable(getLastActivity());
    }
}

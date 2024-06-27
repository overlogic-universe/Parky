package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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
    private Date lastActivity;

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
        lastActivity = (Date) in.readSerializable();
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, Date lastActivity, String email, String password) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.lastActivity = lastActivity;
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, Date lastActivity, String email) {
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

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
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

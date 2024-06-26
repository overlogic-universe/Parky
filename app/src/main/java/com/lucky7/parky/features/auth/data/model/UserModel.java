package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lucky7.parky.core.constant.firestore.FieldConstant;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.domain.entity.Authentication;

import java.util.HashMap;
import java.util.Map;

public class UserModel extends Authentication implements Parcelable {
    private String studentId;
    private String plate;
    private String parkStatus;
    private String parkDatetime;

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
        parkDatetime = in.readString();
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, String parkDatetime, String email, String password) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.parkDatetime = parkDatetime;
    }

    public UserModel(String id, String name, String studentId, String plate, String parkStatus, String parkDatetime, String email) {
        super(id, name, email);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.parkDatetime = parkDatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        super.setId(id);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
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

    public String getParkDatetime() {
        return parkDatetime;
    }

    public void setParkDatetime(String parkDatetime) {
        this.parkDatetime = parkDatetime;
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
                ", parkDatetime='" + getParkDatetime() + '\'' +
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
        result.put(FieldConstant.PARK_DATETIME, getParkDatetime());
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
        parcel.writeString(getParkDatetime());
    }
}

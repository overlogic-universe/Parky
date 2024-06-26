package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.domain.entity.Authentication;

public class UserModel extends Authentication implements Parcelable {
    private String studentId;
    private String plate;
    private ParkStatus parkStatus;
    private String parkDatetime;

    public UserModel() {
    }

    public UserModel(String email, String password) {
        super(email, password);
    }

    public UserModel(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), in.readString());
        studentId = in.readString();
        plate = in.readString();
        parkStatus = ParkStatus.fromString(in.readString());
        parkDatetime = in.readString();
    }

    public UserModel(String id, String name, String studentId, String plate, ParkStatus parkStatus, String parkDatetime, String email, String password) {
        super(id, name, email, password);
        this.studentId = studentId;
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.parkDatetime = parkDatetime;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ParkStatus getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(ParkStatus parkStatus) {
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
                ", studentId='" + studentId + '\'' +
                ", plate='" + plate + '\'' +
                ", parkStatus='" + parkStatus + '\'' +
                ", parkDatetime='" + parkDatetime + '\'' +
                '}';
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
        parcel.writeString(getPassword());
        parcel.writeString(studentId);
        parcel.writeString(plate);
        parcel.writeString(parkStatus.toString());
        parcel.writeString(parkDatetime);
    }
}

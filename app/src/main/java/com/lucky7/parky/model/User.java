package com.lucky7.parky.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User extends Authentication implements Parcelable {
    private String plate;
    private String studentId;
    private String parkStatus;
    private String barcodeId;

    private String parkingTime;

    public User() {
    }

    public User(String email, String password) {
        super(email, password);
    }

    public User(String name, String studentId, String plate, String parkStatus, String barcodeId, String email, String password) {
        super(name, email, password);
        this.plate = plate;
        this.studentId = studentId;
        this.parkStatus = parkStatus;
        this.barcodeId = barcodeId;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }

    protected User(Parcel in) {
        super(in.readString(), in.readString(), in.readString());
        studentId = in.readString();
        plate = in.readString();
        parkStatus = in.readString();
        barcodeId = in.readString();
        parkingTime = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(studentId);
        parcel.writeString(plate);
        parcel.writeString(parkStatus);
        parcel.writeString(barcodeId);
        parcel.writeString(parkingTime);
    }

}
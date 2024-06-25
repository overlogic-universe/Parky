package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lucky7.parky.features.auth.domain.entity.Authentication;

public class UserModel extends Authentication  implements Parcelable {
    private String plate;
    private String parkStatus;
    private String parkingDate;
    private String parkingTime;

    public UserModel() {
    }

    public UserModel(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), in.readString());
        plate = in.readString();
        parkStatus = in.readString();
        parkingTime = in.readString();
    }

    public UserModel(String id, String name, String Id, String plate, String parkStatus, String parkingDate, String parkingTime,
                     String email, String password) {
        super(id, name, email, password);
        this.plate = plate;
        this.parkStatus = parkStatus;
        this.parkingDate = parkingDate;
        this.parkingTime = parkingTime;
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

    public String getParkingDate() {
        return parkingDate;
    }

    public void setParkingDate(String parkingDate) {
        this.parkingDate = parkingDate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
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
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(plate);
        parcel.writeString(parkStatus);
        parcel.writeString(parkingTime);
    }

}
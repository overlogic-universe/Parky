package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lucky7.parky.features.auth.domain.entity.Authentication;

public class AdminModel extends Authentication implements Parcelable {

    public AdminModel(){}

    public AdminModel(String email, String password) {
        super(email, password);
    }

    public AdminModel(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), in.readString());
    }

    public AdminModel(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @NonNull
    @Override
    public String toString() {
        return "AdminModel{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(getId());
        parcel.writeString(getName());
        parcel.writeString(getEmail());
        parcel.writeString(getPassword());
    }

    public static final Creator<AdminModel> CREATOR = new Creator<AdminModel>() {
        @Override
        public AdminModel createFromParcel(Parcel in) {
            return new AdminModel(in);
        }

        @Override
        public AdminModel[] newArray(int size) {
            return new AdminModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}

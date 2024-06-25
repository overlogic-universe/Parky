package com.lucky7.parky.features.auth.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.lucky7.parky.features.auth.domain.entity.Authentication;

public class AdminModel extends Authentication implements Parcelable {
    public AdminModel(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), in.readString());
    }
    public AdminModel(String id, String name,String email, String password) {
        super(id, name, email, password);

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

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}

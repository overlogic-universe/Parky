package com.lucky7.parky.model;

import android.os.Parcel;

public class Authentication {
    protected String name;
    protected String email;
    protected String password;

    public Authentication(){};

    public Authentication(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Authentication(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
package com.lucky7.parky.model;

public class Admin extends Authentication {


    public Admin(){};

    public Admin(String email, String password) {
        super(email, password);

    }

    public void dologin() {

    }

    public void deleteUser() {
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

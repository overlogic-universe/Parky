package com.lucky7.parky.model;

public class User extends  Authentication {
    String parkStatus;
    String nim;
    String plat;
    String barcodeId;

    public User(String name,String email ,String password){
        super(name, email, password);
    }

public void dologin(){

    }

public void changePass(){

    }


}
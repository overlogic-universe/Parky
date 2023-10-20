package com.lucky7.parky.model;

public abstract class Auth{
    String name;
    String email;
    String password;
    String id;

    public abstract void dologin();
}
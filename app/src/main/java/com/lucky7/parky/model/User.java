package com.lucky7.parky.model;

public class User extends Authentication {
    private String parkStatus;
    private String nim;
    private String plate;
    private String barcodeId;

    public User(){};
    public User(String email, String password) {
        super(email, password);
    }
    public User(String name, String nim, String plate,String email, String password) {
        super(name, email, password);
        this.nim = nim;
        this.plate = plate;
        this.email = email;
        this.password = password;
    }

    public void changePass() {

    }

    public void dologin() {

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

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
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


}
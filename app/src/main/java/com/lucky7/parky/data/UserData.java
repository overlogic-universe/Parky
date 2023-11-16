package com.lucky7.parky.data;

import com.lucky7.parky.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    static List<User> listUser = new ArrayList<User>(){{
        new User("Rafli", "rfli@gmail.com" ,"fli");
        new User("Silehu", "silehu@gmail.com" ,"flo");
        new User("flora", "flora@gmal.com" ,"flora");
    }};
}

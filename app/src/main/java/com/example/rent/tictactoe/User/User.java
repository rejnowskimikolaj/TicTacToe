package com.example.rent.tictactoe.User;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RENT on 2017-02-04.
 */

public class User {

    @SerializedName("username")
    String userName;
    String password;
    @SerializedName("appcode")
    final public String appCode = "1234567890";

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public  String getAppCode() {
        return appCode;
    }
}

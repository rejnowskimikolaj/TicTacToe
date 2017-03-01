package com.example.rent.tictactoe.models.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RENT on 2017-02-04.
 */
public class LoginResponseData {

    @SerializedName("X-BB-SESSION")
    String sessionToken;

    public String getSessionToken() {
        return sessionToken;
    }
}

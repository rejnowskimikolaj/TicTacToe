package com.example.rent.tictactoe.User;

/**
 * Created by RENT on 2017-02-04.
 */
public class UserService {

    private  String token;

    private static UserService ourInstance = new UserService();

    public static UserService getInstance() {
        return ourInstance;
    }

    private UserService() {
    }

    public  String getToken() {
        return token;
    }

    public  void setToken(String token) {
        token = token;
    }


    public void register(String token) {
            this.token=token;
    }

    public void logIn(String token) {
        this.token=token;
    }


}

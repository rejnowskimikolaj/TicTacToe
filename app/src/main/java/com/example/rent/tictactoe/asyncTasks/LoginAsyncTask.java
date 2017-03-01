package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.User;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.model.LoginResponse;
import com.example.rent.tictactoe.models.model.LoginResponseData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-04.
 */

public class LoginAsyncTask extends AsyncTask<User,Void,Boolean>{

    TttApiClient client;
    OnLoggedInListener listener;

    public LoginAsyncTask(OnLoggedInListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
    }


    @Override
    protected Boolean doInBackground(User... users) {
        try {
            LoginResponse response = logIn(users[0]);
            if(response==null)return false;

            LoginResponseData data = response.getData();
            String token =data.getSessionToken();
            UserService.getInstance().logIn(token);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LoginResponse logIn(User user) throws IOException {
        Call<LoginResponse> call = client.login(user);
        Response<LoginResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            LoginResponse loginResponse = response.body();
            return loginResponse;
        }
        return null;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onLoggedIn(aBoolean);
    }

    public interface OnLoggedInListener{
        void onLoggedIn(Boolean result);
    }
}

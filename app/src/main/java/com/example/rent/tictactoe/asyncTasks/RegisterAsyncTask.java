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

public class RegisterAsyncTask extends AsyncTask<User,Void,Boolean> {

    TttApiClient client;
    OnRegisterListener listener;

    public RegisterAsyncTask(OnRegisterListener listener) {
        this.listener = listener;
        client=new TttApiClientFactory().create();
    }

    @Override
    protected Boolean doInBackground(User... users) {
        try {
            LoginResponse response = register(users[0]);

            if(response==null)return false;

            LoginResponseData data = response.getData();
            String token =data.getSessionToken();
            UserService.getInstance().register(token);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public LoginResponse register(User user) throws IOException {
        Call<LoginResponse> call = client.register(user);
        Response<LoginResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            LoginResponse loginResponse = response.body();
            return loginResponse;
        }
        return null;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onRegistered(aBoolean);
    }

    public interface OnRegisterListener{
        public void onRegistered(Boolean result);
    }


}

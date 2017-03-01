package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.joinGame.model.JoinGameRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by RENT on 2017-02-07.
 */

public class JoinGameAsyncTask extends AsyncTask<String,Void,Boolean> {

    private OnGameJoinedListener listener;
    private TttApiClient client;

    public JoinGameAsyncTask(OnGameJoinedListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            return  joinGame(strings[0]);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;    }


    public Boolean joinGame(String name) throws IOException {
        Call<ResponseBody> call = client.joinGame(UserService.getInstance().getToken(),new JoinGameRequest(name));
        Response<ResponseBody> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            return true;
        }
        return false;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onGameJoined(aBoolean);
    }

    public interface OnGameJoinedListener{
        public void onGameJoined(Boolean result);
    }
}

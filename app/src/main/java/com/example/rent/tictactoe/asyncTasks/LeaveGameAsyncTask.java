package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.myGame.model.MyGameResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-07.
 */

public class LeaveGameAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private TttApiClient client;
    private OnGameLeftListener listener;

    public LeaveGameAsyncTask(OnGameLeftListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        Boolean response = false;

        try {
             response = leaveGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onGameLeft(aBoolean);
    }

    private Boolean leaveGame() throws IOException {
        String token = UserService.getInstance().getToken();
        Call<ResponseBody> call = client.leaveGame(token);

        Response<ResponseBody> response = call.execute();
        if(response.isSuccessful()) { //http 200+

            return true;
        }
        return false;
    }

    public interface OnGameLeftListener{
        public void onGameLeft(Boolean result);
    }
}

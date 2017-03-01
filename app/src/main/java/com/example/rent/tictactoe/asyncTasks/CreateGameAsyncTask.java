package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.createGame.model.CreateGameRequest;
import com.example.rent.tictactoe.models.createGame.model.CreateGameResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-06.
 */

public class CreateGameAsyncTask extends AsyncTask<String,Void,String> {

    private OnGameCreatedListener listener;
    private TttApiClient client;

    public CreateGameAsyncTask(OnGameCreatedListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            CreateGameResponse response = createGame(strings[0]);
            if(response!=null){
                return response.getData();

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String gameId) {
        listener.onGameCreated(gameId);
    }

    private CreateGameResponse createGame(String name) throws IOException {
        Call<CreateGameResponse> call = client.createGame(UserService.getInstance().getToken(),new CreateGameRequest(name));
        Response<CreateGameResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            CreateGameResponse createGameResponse = response.body();
            return createGameResponse;
        }
        return null;

    }

    public interface OnGameCreatedListener{
        public void onGameCreated(String gameId);

    }

}

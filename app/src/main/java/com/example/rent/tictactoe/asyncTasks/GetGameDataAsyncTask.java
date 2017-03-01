package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.GameData.model.GameData;
import com.example.rent.tictactoe.models.GameData.model.GameDataResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-07.
 */

public class GetGameDataAsyncTask extends AsyncTask<String,Void,GameData> {

    private GameDataGotListener listener;
    private TttApiClient client;

    public GetGameDataAsyncTask(GameDataGotListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
    }

    @Override
    protected GameData doInBackground(String... strings) {
        String token = UserService.getInstance().getToken();
        GameData result = null;
        try {
            result=getGameData(token,strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    private GameData getGameData(String token,String gameId) throws IOException {

        Call<GameDataResponse> call = client.getGameData(token,gameId);
        Response<GameDataResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            GameDataResponse gameDataResponse = response.body();

            return gameDataResponse.getData().get(0);
        }
        return null;
    }

    @Override
    protected void onPostExecute(GameData gameData) {
        listener.onDataGot(gameData);
    }

    public interface GameDataGotListener{
         void onDataGot(GameData gameData);
    }
}

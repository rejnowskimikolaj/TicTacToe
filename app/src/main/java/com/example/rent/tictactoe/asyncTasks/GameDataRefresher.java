package com.example.rent.tictactoe.asyncTasks;

import android.os.Handler;
import android.os.Looper;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.GameData.model.GameData;
import com.example.rent.tictactoe.models.GameData.model.GameDataResponse;
import com.example.rent.tictactoe.models.gameList.model.GameListItem;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-07.
 */

public class GameDataRefresher {

    private GetGameDataAsyncTask.GameDataGotListener listener;
    private TttApiClient client;
    String token = UserService.getInstance().getToken();
    private final ScheduledExecutorService executor;
    String gameId;


    public GameDataRefresher(GetGameDataAsyncTask.GameDataGotListener listener,String gameId) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
        executor = Executors.newSingleThreadScheduledExecutor();
        this.gameId=gameId;

    }

    private void getGameData(String token, String gameId) throws IOException {

        Call<GameDataResponse> call = client.getGameData(token,gameId);
        Response<GameDataResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            GameDataResponse gameDataResponse = response.body();

            postDataDownloaded(gameDataResponse.getData().get(0));
        }
            postDataDownloaded(null);
    }

    public void close() {
        executor.shutdown();
    }

    private void postDataDownloaded(final GameData gameData) {
        //przechodze do wątku UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //ta metoda będzie wykonana w wątku głównym, tak jak onPostExecute
                listener.onDataGot(gameData);
            }
        });
    }

    public void startRefreshing() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //ta metoda wykonana w wątku w tle co 5 sekund, patrz parametry dalej
                try {
                    getGameData(token, gameId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3, TimeUnit.SECONDS); //to mówi jak często ma się wykonywać
    }
}

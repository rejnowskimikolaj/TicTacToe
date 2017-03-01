package com.example.rent.tictactoe.asyncTasks;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.gameList.model.GameListItem;
import com.example.rent.tictactoe.models.gameList.model.GameListResponse;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-04.
 */

public class GameListRefresher {


    private final TttApiClient client;
    private final GameListDownloadListener listener;
    private final ScheduledExecutorService executor;
    String token = UserService.getInstance().getToken();

    public GameListRefresher(GameListDownloadListener gameListDownloadListener) {
        this.listener = gameListDownloadListener;
        client = new TttApiClientFactory().create();
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startRefreshing() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //ta metoda wykonana w wątku w tle co 5 sekund, patrz parametry dalej
                getGameList();
            }
        }, 0, 5, TimeUnit.SECONDS); //to mówi jak często ma się wykonywać
    }

    private void getGameList() {
        try {
            Log.e("REFRESHER", "REFRESHING DATA!");
            //to leci w tle, tak jak doInBackground w asyncTasksu
            Call<GameListResponse> call = client.getGameList(token);
            Response<GameListResponse> response = call.execute();
            if (response.isSuccessful()) { //http 200+

                GameListResponse gameListResponse = response.body();
                postDataDownloaded(gameListResponse.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postDataDownloaded(final List<GameListItem> list) {
        //przechodze do wątku UI
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //ta metoda będzie wykonana w wątku głównym, tak jak onPostExecute
                listener.onGameListDownloaded(list);
            }
        });
    }

    public void close() {
        executor.shutdown();
    }

    public interface GameListDownloadListener{
         void onGameListDownloaded(List<GameListItem> list);
    }
}

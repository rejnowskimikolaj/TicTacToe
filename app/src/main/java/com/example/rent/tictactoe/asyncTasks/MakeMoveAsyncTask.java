package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.GameData.model.Move;
import com.example.rent.tictactoe.models.makeMove.model.MakeMoveRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-07.
 */

public class MakeMoveAsyncTask extends AsyncTask<Move,Void,Boolean> {

    TttApiClient client;
    OnMoveMadeListener listener;
    String gameId;

    public MakeMoveAsyncTask(OnMoveMadeListener listener,String gameId) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
        this.gameId = gameId;

    }

    @Override
    protected Boolean doInBackground(Move... moves) {

        Boolean response = false;

        try {
            response = makeMove(moves[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    private Boolean makeMove(Move move) throws IOException {
        String token = UserService.getInstance().getToken();

        Call<ResponseBody> call = client.makeMove(token,new MakeMoveRequest(gameId,move.getX(),move.getY()));

        Response<ResponseBody> response = call.execute();
        if(response.isSuccessful()) { //http 200+

            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onMoveMade(aBoolean);
    }

    public interface OnMoveMadeListener{
        void onMoveMade(Boolean bool);
    }
}

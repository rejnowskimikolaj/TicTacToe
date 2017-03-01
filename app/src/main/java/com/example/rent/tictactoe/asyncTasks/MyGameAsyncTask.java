package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;
import com.example.rent.tictactoe.RetrofitUtils.TttApiClientFactory;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.models.model.LoginResponse;
import com.example.rent.tictactoe.models.model.LoginResponseData;
import com.example.rent.tictactoe.models.myGame.model.MyGameResponse;
import com.example.rent.tictactoe.models.myGame.model.MyGameResponseData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by RENT on 2017-02-07.
 */

public class MyGameAsyncTask extends AsyncTask<Void,Void,MyGameResponseData> {

    private OnMyGameCheckedListener listener;
    private TttApiClient client;

    public MyGameAsyncTask(OnMyGameCheckedListener listener) {
        this.listener = listener;
        client = new TttApiClientFactory().create();
    }

    @Override
    protected MyGameResponseData doInBackground(Void... voids) {
        String token = UserService.getInstance().getToken();

        MyGameResponse response = null;
        try {
            response = getMyGame(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response==null)return null;

        return response.getData();

    }

    @Override
    protected void onPostExecute(MyGameResponseData myGameResponseData) {
        listener.onMyGameChecked(myGameResponseData);
    }

    private MyGameResponse getMyGame(String token) throws IOException {
        Call<MyGameResponse> call = client.getMyGame(token);
        Response<MyGameResponse> response = call.execute();
        if(response.isSuccessful()) { //http 200+
            MyGameResponse myGameResponse = response.body();
            return myGameResponse;
        }
        return null;
    }



    public interface OnMyGameCheckedListener{
        public void onMyGameChecked(MyGameResponseData myGameResponseData);
    }
}

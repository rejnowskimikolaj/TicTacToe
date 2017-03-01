package com.example.rent.tictactoe.asyncTasks;

import android.os.AsyncTask;

import com.example.rent.tictactoe.RetrofitUtils.TttApiClient;

/**
 * Created by RENT on 2017-02-04.
 */

public class LogoutAsyncTask extends AsyncTask<String,Void,Boolean> {

    TttApiClient client;


    @Override
    protected Boolean doInBackground(String... strings) {
        return null;
    }

    public interface onLogout{

    }
}

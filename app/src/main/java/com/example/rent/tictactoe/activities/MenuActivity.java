package com.example.rent.tictactoe.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rent.tictactoe.R;
import com.example.rent.tictactoe.asyncTasks.CreateGameAsyncTask;
import com.example.rent.tictactoe.asyncTasks.GameListRefresher;
import com.example.rent.tictactoe.asyncTasks.JoinGameAsyncTask;
import com.example.rent.tictactoe.asyncTasks.MyGameAsyncTask;
import com.example.rent.tictactoe.models.gameList.model.GameListItem;
import com.example.rent.tictactoe.models.gameList.model.GameListResponse;
import com.example.rent.tictactoe.models.myGame.model.MyGameResponseData;

import java.util.List;

public class MenuActivity extends AppCompatActivity implements JoinGameAsyncTask.OnGameJoinedListener,
                                                                GameListRefresher.GameListDownloadListener,
                                                                GameListAdapter.OnGameListItemClickedListener,
                                                                MyGameAsyncTask.OnMyGameCheckedListener,
                                                                CreateGameAsyncTask.OnGameCreatedListener{

    List<GameListItem> gameListItems;
    GameListRefresher gameListRefresher;
    Button createGameButton;
    public final static String GAME_ID = "gameId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        createGameButton = (Button) findViewById(R.id.activity_menu_newGame_button);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateGameButtonClicked();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new MyGameAsyncTask(this).execute();
    }

    private void onCreateGameButtonClicked() {


        AlertDialog dialog=null;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Create game");
        final EditText input = new EditText(this);
        input.setHint("your game name");
        ab.setView(input);


        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String gameName = input.getText().toString();

                if(!(gameName.equals("")||gameName.equals(null))){
                    createGame(gameName);
                }


            }
        });


        final AlertDialog finalDialog = dialog;
        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalDialog.dismiss();
            }
        });

        dialog=ab.create();
        dialog.show();
    }

    public void createGame(String gameName){
        CreateGameAsyncTask createTask = new CreateGameAsyncTask(this);
        createTask.execute(gameName);


    }


    @Override
    protected void onResume() {
        super.onResume();
        gameListRefresher= new GameListRefresher(this);
        gameListRefresher.startRefreshing();
    }

    @Override
    public void onGameListDownloaded(List<GameListItem> list) {

        if(list.isEmpty()) return;

        this.gameListItems = list;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_menu_recyclerView);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        GameListAdapter adapter = new GameListAdapter(list,this);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onGameListItemClicked(GameListItem item) {

        if(canJoinGame(item)){
            Toast.makeText(this,"Can join game",Toast.LENGTH_SHORT).show();

            new JoinGameAsyncTask(this).execute(item.getName());
            startGame(item.getId());


        }

        else{
            Toast.makeText(this,"You can't join this game",Toast.LENGTH_SHORT).show();

        }
    }

    private boolean canJoinGame(GameListItem item){

        String player = item.getPlayer();
        if(player.equals("")|| player.equals(null)) {
            return true;
        }

        return false;

    }

    @Override
    protected void onPause() {
        gameListRefresher.close();
        super.onPause();
    }

    @Override
    public void onGameCreated(String gameId) {

        if(gameId!=null){
            Toast.makeText(this,"Game created",Toast.LENGTH_SHORT).show();
            startGame(gameId);

        }
        else Toast.makeText(this,"Oops",Toast.LENGTH_SHORT).show();
    }

    public void startGame(String gameId){
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra(GAME_ID,gameId);
        startActivity(intent);
    }

    @Override
    public void onMyGameChecked(MyGameResponseData myGameResponseData) {
        if(myGameResponseData==null) {
            Toast.makeText(this,"Couldn't check game",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(myGameResponseData.getGameId().equals("")||myGameResponseData.getGameId().equals(null))){
            Toast.makeText(this,"Your game: "+myGameResponseData.getName(),Toast.LENGTH_SHORT).show();
            startGame(myGameResponseData.getGameId());

        }
    }

    @Override
    public void onGameJoined(Boolean result) {
    }
}

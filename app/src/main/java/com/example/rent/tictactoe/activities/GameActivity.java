package com.example.rent.tictactoe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rent.tictactoe.R;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.asyncTasks.GameDataRefresher;
import com.example.rent.tictactoe.asyncTasks.GetGameDataAsyncTask;
import com.example.rent.tictactoe.asyncTasks.LeaveGameAsyncTask;
import com.example.rent.tictactoe.asyncTasks.MakeMoveAsyncTask;
import com.example.rent.tictactoe.models.GameData.model.GameData;
import com.example.rent.tictactoe.models.GameData.model.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements LeaveGameAsyncTask.OnGameLeftListener,
                                                                MakeMoveAsyncTask.OnMoveMadeListener,
                                                                GetGameDataAsyncTask.GameDataGotListener {
    String gameId;
    Button exitButton;
    TextView textView;
    GameDataRefresher gameDataRefresher;
    Map<Move,Button> buttonMap;
    List<Move> movesOwner;
    List<Move> movesPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameId = getIntent().getStringExtra(MenuActivity.GAME_ID);

        exitButton= (Button) findViewById(R.id.activity_game_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveGame();
            }
        });

        initMoveButtons();

        textView = (TextView) findViewById(R.id.activity_game_textView);


    }

    private void initMoveButtons(){


        Button button00 = (Button) findViewById(R.id.button00);
        button00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(0,0));
            }
        });
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(0,1));
            }
        });
        Button button02 = (Button) findViewById(R.id.button02);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(0,2));
            }
        });
        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(1,0));
            }
        });
        Button button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(1,1));
            }
        });
        Button button12 = (Button) findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(1,2));
            }
        });
        Button button20 = (Button) findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(2,0));
            }
        });
        Button button21 = (Button) findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(2,1));
            }
        });
        Button button22 = (Button) findViewById(R.id.button00);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMoveButtonClicked(new Move(2,2));
            }
        });

        buttonMap = new HashMap<>();
        buttonMap.put( new Move(0,0),button00);
        buttonMap.put( new Move(0,1),button01);
        buttonMap.put( new Move(0,2),button02);
        buttonMap.put( new Move(1,0),button10);
        buttonMap.put( new Move(1,1),button11);
        buttonMap.put( new Move(1,2),button12);
        buttonMap.put( new Move(2,0),button20);
        buttonMap.put( new Move(2,1),button21);
        buttonMap.put( new Move(2,2),button22);



    }

    private void onMoveButtonClicked(Move move){

        makeMove(move);

    }

    public void makeMove(Move move){
        new MakeMoveAsyncTask(this,gameId).execute(move);
    }
    @Override
    protected void onResume() {
        super.onResume();
        gameDataRefresher= new GameDataRefresher(this,gameId);
        gameDataRefresher.startRefreshing();
    }

    @Override
    protected void onPause() {
        gameDataRefresher.close();
        super.onPause();
    }

    public void leaveGame(){
        new LeaveGameAsyncTask(this).execute();
    }

    @Override
    public void onGameLeft(Boolean result) {
        if(result){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this,"Oops",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataGot(GameData gameData) {
        if(gameData!=null){

            updateButtons();
        }
    }

    private void updateButtons(){

        if(!(movesOwner==null||movesOwner.isEmpty())){
            for(Move move: movesOwner){
                Button button =buttonMap.get(move);
                button.setText("X");
                button.setEnabled(false);
            }
        }
        if(!(movesPlayer==null||movesPlayer.isEmpty())){
            for(Move move: movesPlayer){
                Button button =buttonMap.get(move);
                button.setText("O");
                button.setEnabled(false);
            }
        }
    }

    @Override
    public void onMoveMade(Boolean result) {

        if(result){
            Toast.makeText(this,"Move made.",Toast.LENGTH_SHORT).show();
            new GetGameDataAsyncTask(this).execute(UserService.getInstance().getToken(),gameId);
        }

        else{
            Toast.makeText(this,"Oops",Toast.LENGTH_SHORT).show();

        }
    }
}

package com.example.rent.tictactoe.models.makeMove.model;

/**
 * Created by RENT on 2017-02-07.
 */

public class MakeMoveRequest {

    String gameId;
    int x;
    int y;

    public MakeMoveRequest(String gameId, int x, int y) {
        this.gameId = gameId;
        this.x = x;
        this.y = y;
    }
}

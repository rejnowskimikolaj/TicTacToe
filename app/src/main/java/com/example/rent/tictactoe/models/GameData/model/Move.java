package com.example.rent.tictactoe.models.GameData.model;

/**
 * Created by RENT on 2017-02-07.
 */
public class Move {

    int x;
    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

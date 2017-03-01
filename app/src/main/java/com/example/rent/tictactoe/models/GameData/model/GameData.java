package com.example.rent.tictactoe.models.GameData.model;

import java.util.List;

/**
 * Created by RENT on 2017-02-07.
 */
public class GameData {

    String id;
    String name;
    String creator;
    String player;

    List<Move> movesOwner;
    List<Move> movesPlayer;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getPlayer() {
        return player;
    }

    public List<Move> getMovesOwner() {
        return movesOwner;
    }

    public List<Move> getMovesPlayer() {
        return movesPlayer;
    }
}

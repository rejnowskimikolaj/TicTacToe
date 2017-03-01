package com.example.rent.tictactoe.models.GameData.model;

import java.util.List;

/**
 * Created by RENT on 2017-02-07.
 */

public class GameDataResponse {

    String result;
    List<GameData> data;

    public String getResult() {
        return result;
    }

    public List<GameData> getData() {
        return data;
    }
}

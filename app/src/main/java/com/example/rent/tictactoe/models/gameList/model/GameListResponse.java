package com.example.rent.tictactoe.models.gameList.model;

import java.util.List;

/**
 * Created by RENT on 2017-02-04.
 */

public class GameListResponse {

    String result;
    List<GameListItem> data;

    public String getResult() {
        return result;
    }

    public List<GameListItem> getData() {
        return data;
    }
}

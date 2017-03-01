package com.example.rent.tictactoe.RetrofitUtils;

import com.example.rent.tictactoe.User.User;
import com.example.rent.tictactoe.models.GameData.model.GameDataResponse;
import com.example.rent.tictactoe.models.createGame.model.CreateGameRequest;
import com.example.rent.tictactoe.models.createGame.model.CreateGameResponse;
import com.example.rent.tictactoe.models.gameList.model.GameListResponse;
import com.example.rent.tictactoe.models.joinGame.model.JoinGameRequest;
import com.example.rent.tictactoe.models.makeMove.model.MakeMoveRequest;
import com.example.rent.tictactoe.models.login.model.LoginResponse;
import com.example.rent.tictactoe.models.myGame.model.MyGameResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-02-04.
 */

public interface TttApiClient {

    @POST("/user")
    @Headers({

            "X-BAASBOX-APPCODE: 1234567890",
            "Content-Type: application/json"
    })

    Call<LoginResponse> register(@Body User user);


    @POST("/login")
    @Headers({
            "X-BAASBOX-APPCODE: 1234567890",
            "Content-Type: application/json"
    })

    Call<LoginResponse> login(@Body User user);

    @POST("/logout")
    Call<LoginResponse> logout(@Header("X-BB-SESSION") String token);

    @GET("/plugin/ttt.game")
    Call<GameListResponse> getGameList(@Header("X-BB-SESSION") String token);

    @POST("/plugin/ttt.game")
    @Headers({
            "Content-Type: application/json"
    })
    Call<CreateGameResponse> createGame(@Header("X-BB-SESSION") String token, @Body CreateGameRequest request);

    @POST("/plugin/ttt.joinGame")
    @Headers({
            "Content-Type: application/json"
    })
    Call<ResponseBody> joinGame(@Header("X-BB-SESSION") String token, @Body JoinGameRequest request);

    @GET("/plugin/ttt.myGame")
    Call<MyGameResponse> getMyGame(@Header("X-BB-SESSION") String token);

    @DELETE("/plugin/ttt.leaveGame")
    Call<ResponseBody> leaveGame(@Header("X-BB-SESSION") String token);

    @GET("/plugin/ttt.gameData")
    Call<GameDataResponse> getGameData(@Header("X-BB-SESSION") String token,@Query("gameId") String gameId);

    @POST("/plugin/ttt.makeMove")
    @Headers({
            "Content-Type: application/json"
    })
    Call<ResponseBody> makeMove(@Header("X-BB-SESSION") String token, @Body MakeMoveRequest request);

}

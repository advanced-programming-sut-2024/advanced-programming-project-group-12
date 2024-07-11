package com.mygdx.game.controller.local;

import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.JoinPublicGame;

import java.util.ArrayList;

public class LiveStreamMenuController {
    private static ArrayList<String> games;
    public static void setGames(ArrayList<String> gameList) {
        games = new ArrayList<>(gameList);
    }
    public static ArrayList<String> getGames() {
        return games;
    }
    public static void joinGame(String game) {
        Client.getInstance().sendMassage(new JoinPublicGame(game));
    }
}

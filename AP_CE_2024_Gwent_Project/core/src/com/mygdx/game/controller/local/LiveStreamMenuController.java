package com.mygdx.game.controller.local;

import java.util.ArrayList;

public class LiveStreamMenuController {
    private static ArrayList<String> games;
    private static void setGames(ArrayList<String> gameList) {
        games = new ArrayList<>(gameList);
    }
    public static ArrayList<String> getGames() {
        return games;
    }
    public static void joinGame(String game) {
        //TODO : complete it
    }
}

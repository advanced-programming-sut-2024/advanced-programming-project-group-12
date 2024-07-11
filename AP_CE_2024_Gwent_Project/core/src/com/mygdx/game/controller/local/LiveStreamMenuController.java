package com.mygdx.game.controller.local;

import java.util.ArrayList;

public class LiveStreamMenuController {
    private static ArrayList<String> games;
    static {
        games = new ArrayList<>();
        games.add("game 1");
        games.add("Game 2");
        games.add("Game 3");
        games.add("Game 4");
        games.add("Game 5");
    }
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

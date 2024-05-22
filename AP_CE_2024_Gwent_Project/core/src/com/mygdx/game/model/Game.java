package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private ArrayList<User> players;
    private Date date;
    private ArrayList<Round> rounds;
    private GameBoard gameBoard;

    public Game() {
        players = new ArrayList<User>();
        date = new Date();
        rounds = new ArrayList<Round>();
        gameBoard = new GameBoard();
    }
}

package com.mygdx.game.model;

import com.mygdx.game.model.gameBoard.GameBoard;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private static Game currentGame;
    private ArrayList<User> players;
    private Date date;
    private ArrayList<Round> rounds;
    private GameBoard gameBoard;
    private Player currentPlayer;
    private Player opposition;

    public Game(Player player, Player opposition) {
        players = new ArrayList<User>();
        date = new Date();
        rounds = new ArrayList<Round>();
        gameBoard = new GameBoard();
        currentGame = this;
        this.currentPlayer = player;
        this.opposition = opposition;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        Game.currentGame = currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpposition() {
        return opposition;
    }
}

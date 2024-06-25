package com.mygdx.game.model;

import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.gameBoard.GameBoard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Game {
    private static Game currentGame;
    private final List<Player> players;
//    private final LocalDate date;
    private final ArrayList<Round> rounds;
    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Player opposition;

    public Game(Player player, Player opposition) {
        players = Arrays.asList(player, opposition);

//        date = LocalDate.now();
        rounds = new ArrayList<>();
        gameBoard = new GameBoard(player, opposition);

        currentGame = this;
        this.currentPlayer = player;
        this.opposition = opposition;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getOpposition() {
        return opposition;
    }

    public void setOpposition(Player opposition) {
        this.opposition = opposition;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}

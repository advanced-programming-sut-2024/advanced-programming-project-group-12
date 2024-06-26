package com.mygdx.game.model;

import com.mygdx.game.model.gameBoard.GameBoard;

public class Round {
    Player player1;
    Player player2;
    private final int roundNumber;
    GameBoard gameBoardCopy;

    public Round(int roundNumber, Player player1, Player player2) {
        this.roundNumber = roundNumber;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getRoundNumber() {
        return roundNumber;
    }


    public void startRound() {}

    public void endRound(GameBoard gameBoard) {
        if(gameBoard.getPlayerStrength(player1) > gameBoard.getPlayerStrength(player2)) {
            player1.setWon(true);
            player2.loseRound();
        }
        else if(gameBoard.getPlayerStrength(player1) < gameBoard.getPlayerStrength(player2)) {
            player1.setWon(true);
            player1.loseRound();
        } else {
            player1.loseRound();
            player2.loseRound();
        }

        gameBoardCopy = gameBoard.copy();

        if(roundNumber == 3) {
            Game.getCurrentGame().isOver();
        }
    }
}

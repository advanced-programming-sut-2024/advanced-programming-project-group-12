package com.mygdx.game.model.game;

import com.mygdx.game.model.user.Player;

public class Round {
    private Player player1;
    private Player player2;
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

    public Player endRound(GameBoard gameBoard) {
        gameBoardCopy = gameBoard.copy();

        if(player1.getHealth() == 0 || player2.getHealth() == 0 ) {
            player1.getGame().isOver();
        }


        if (gameBoard.getPlayerStrength(player1) > gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player1, player2);
            return player1;
        } else if (gameBoard.getPlayerStrength(player1) < gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player2, player1);
            return player2;
        } else {
            if (player1.getFaction().equals(Faction.NILFGAARD) && player2.getFaction().equals(Faction.NILFGAARD)) {
                handleWinCase(player1, player2);
                return player1;
            }
            else if (player2.getFaction().equals(Faction.NILFGAARD) && player1.getFaction().equals(Faction.NILFGAARD)) {
                handleWinCase(player2, player1);
                return player2;
            }
            else {
                player1.loseRound();
                player2.loseRound();
                return player2;
            }
        }
    }

    private void handleWinCase(Player winner, Player loser) {
        winner.setWon(true);
        loser.loseRound();
        if (winner.getFaction().equals(Faction.NORTHERN_REALMS)) {
            winner.drawCard();
        }
    }
}

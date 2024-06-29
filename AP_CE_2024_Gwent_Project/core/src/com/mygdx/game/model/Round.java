package com.mygdx.game.model;


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

    public void endRound(GameBoard gameBoard) {
        if (gameBoard.getPlayerStrength(player1) > gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player1, player2);
        } else if (gameBoard.getPlayerStrength(player1) < gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player2, player1);
        } else {
            if (player1.getFaction().equals(Faction.NILFGAARD) && player2.getFaction().equals(Faction.NILFGAARD)) {
                handleWinCase(player1, player2);
            }
            else if (player2.getFaction().equals(Faction.NILFGAARD) && player1.getFaction().equals(Faction.NILFGAARD)) {
                handleWinCase(player2, player1);
            }
            else {
                player1.loseRound();
                player2.loseRound();
            }
        }

        gameBoardCopy = gameBoard.copy();

        if(roundNumber == 3) {
            Game.getCurrentGame().isOver();
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

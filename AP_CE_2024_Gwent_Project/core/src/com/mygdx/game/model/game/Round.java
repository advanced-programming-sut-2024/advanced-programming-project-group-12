package com.mygdx.game.model.game;

import com.google.gson.annotations.Expose;
import com.mygdx.game.model.user.Player;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;

public class Round {
    @Expose
    private transient Player player1;
    private String player1Name;
    private int player1Score;
    @Expose
    private transient Player player2;
    private String player2Name;
    private int player2Score;
    private final int roundNumber;
    transient GameBoard gameBoardCopy;

    String winnerName;

    public Round(int roundNumber, Player player1, Player player2) {
        this.roundNumber = roundNumber;
        this.player1 = player1;
        player1Name = player1.getUsername();
        this.player2 = player2;
        player2Name = player2.getUsername();
    }

    public int getRoundNumber() {
        return roundNumber;
    }


    public void startRound() {}

    public Player endRound(GameBoard gameBoard) {
        gameBoardCopy = gameBoard.copy();
        Player winner =null;


        player1Score = gameBoard.getPlayerStrength(player1);
        player2Score = gameBoard.getPlayerStrength(player2);


        if (gameBoard.getPlayerStrength(player1) > gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player1, player2);
            winner= player1;
        } else if (gameBoard.getPlayerStrength(player1) < gameBoard.getPlayerStrength(player2)) {
            handleWinCase(player2, player1);
            winner = player2;
        } else {
            if (player1.getFaction().equals(Faction.NILFGAARD) && !player2.getFaction().equals(Faction.NILFGAARD)) {
                System.out.println("nilfgaard action triggered");
                handleWinCase(player1, player2);
                winner = player1;
            }
            else if (player2.getFaction().equals(Faction.NILFGAARD) && !player1.getFaction().equals(Faction.NILFGAARD)) {
                System.out.println("nilfgaard action triggered");
                handleWinCase(player2, player1);
                winner = player2;
            }
            else {
                player1.loseRound();
                player2.loseRound();
            }
        }
        if(player1.getHealth() == 0 || player2.getHealth() == 0 ) {
            player1.getGame().isOver();
        }
        return winner;
    }

    private void handleWinCase(Player winner, Player loser) {
        winnerName = winner.getUsername();
        winner.setWon(true);
        loser.loseRound();
        if (winner.getFaction().equals(Faction.NORTHERN_REALMS)) {
            winner.drawCard();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public String getWinnerName() {
        return winnerName;
    }
    public boolean isWinner(String usernmame) {
        if(player1.getUsername().equals(usernmame)) {
            return player1Score > player2Score;
        } else {
            return player2Score > player1Score;
        }
    }
    public int getScoreByUsername(String username) {
        if(player1.getUsername().equals(username)) {
            return player1Score;
        } else return player2Score;
    }
}

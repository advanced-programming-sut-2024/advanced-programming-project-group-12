package com.mygdx.game.model;

public class Round {
    private final int roundNumber;
    private boolean isOver;


    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.isOver = false;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isOver() {
        return isOver;
    }

    public void startRound() {}
    public void endRound() {}
}

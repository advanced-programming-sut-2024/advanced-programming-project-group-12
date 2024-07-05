package com.mygdx.game.model.user;

import com.mygdx.game.model.game.Faction;

public class UserInfo {
    private int highestScore;
    private int totalScore;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDrawn;
    private Faction faction;


    public UserInfo() {
        highestScore = 0;
        totalScore = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesDrawn = 0;
        gamesLost = 0;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesDrawn() {
        return gamesDrawn;
    }

    public void setGamesDrawn(int gamesDrawn) {
        this.gamesDrawn = gamesDrawn;
    }

    public String getWins() {
        return String.valueOf(gamesWon);
    }

    public String getLosses() {
        return String.valueOf(gamesLost);
    }
}

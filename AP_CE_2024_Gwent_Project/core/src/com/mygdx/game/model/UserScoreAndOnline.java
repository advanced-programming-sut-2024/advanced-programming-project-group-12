package com.mygdx.game.model;

public class UserScoreAndOnline implements Comparable<UserScoreAndOnline> {
    String username;
    boolean online;
    int score;

    public UserScoreAndOnline(boolean online, int score, String username) {
        this.online = online;
        this.score = score;
        this.username = username;
    }

    @Override
    public int compareTo(UserScoreAndOnline o) {
        if(o.score > score) return -1;
        else if(o.score == score) return 0;
        return 1;
    }

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public int getScore() {
        return score;
    }
}

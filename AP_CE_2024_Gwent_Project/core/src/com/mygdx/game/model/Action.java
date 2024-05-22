package com.mygdx.game.model;

public enum Action {

    ;
    private Runnable action;

    Action(Runnable action) {
        this.action = action;
    }
}

package com.mygdx.game.model.gameBoard;

public enum Rows {
    CLOSE_COMBAT(0),
    RANGED_WEAPON(1),
    SIEGE_WEAPON(2)
    ;
    private final int position;

    Rows(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}

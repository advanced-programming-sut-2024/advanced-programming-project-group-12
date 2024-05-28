package com.mygdx.game.model;

public class PlayableCard extends AbstractCard {
    private int power;
    public PlayableCard(String name, String description) {
        super(name, description);
    }

    public int getPower() {
        return power;
    }
}

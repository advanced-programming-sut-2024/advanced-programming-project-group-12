package com.mygdx.game.model;

public class PlayableCard extends AbstractCard {
    private int power;
    public PlayableCard(String name, String description, Action action, int power) {
        super(name, description, action);
    }

    public int getPower() {
        return power;
    }

    @Override
    public void kill() {

    }
}

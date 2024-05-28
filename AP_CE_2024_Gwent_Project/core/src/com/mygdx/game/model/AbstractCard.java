package com.mygdx.game.model;

public abstract class AbstractCard {
    private String name;
    private String description;

    public AbstractCard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void kill();
}

package com.mygdx.game.model;


public enum Faction {
    MONSTERS("Monsters"),
    NORTHERN_REALMS("Northern Realms"),
    SCOIATAEL("Scoiatael"),
    EMPIRE_NILFGAARD("Empire Nilfgaard"),
    SKELLIGE("Skellige")
    ;
    private String name;
    private Runnable Action;

    private Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

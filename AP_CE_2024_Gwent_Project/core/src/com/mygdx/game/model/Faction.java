package com.mygdx.game.model;


public enum Faction {
    MONSTERS("monsters"),
    NORTHERN_REALMS("realms"),
    SCOIATAEL("scoiatael"),
    EMPIRE_NILFGAARD("nilfgaard"),
    SKELLIGE("skellige"),
    NEUTRAL("neutral"),
    SPECIAL("special"),
    WEATHER("weather")
    ;
    private String name;
    private Runnable Action;

    private Faction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getAssetFileName() {
        return "faction_" + name;
    }
}

package com.mygdx.game.model;

import java.util.HashMap;

public enum Faction {

    NORTHERN_REALMS("realms"),

    SCOIATAEL("scoiatael"),

    NILFGAARD("nilfgaard"),

    SKELLIGE("skellige"),

    NEUTRAL("neutral"),

    SPECIAL("special"),

    WEATHER("weather"),
    MONSTERS("Monsters"),

    NORTHERN_REALMS("Northern Realms"),

    SCOIATAEL("Scoiatael"),

    EMPIRE_NILFGAARD("Empire Nilfgaard"),

    SKELLIGE("Skellige"),

    NEUTRAL("Neutral");


    private String name;

    private static final HashMap<String, Faction> allFactions = new HashMap<>();

    private Faction(String name) {
        this.name = name;
    }

    static {
        for (Faction faction : Faction.values()) {
            allFactions.put(faction.getName(), faction);
        }
    }

    public String getName() {
        return name;
    }

    public String getAssetFileName() {
        return "faction_" + name;
    }

    public static Faction getFactionByName(String name) {
        return allFactions.get(name);
    }
}

package com.mygdx.game.model.game;

import java.util.HashMap;

public enum Faction {
    MONSTERS("monsters"),
    NORTHERN_REALMS("realms"),
    SCOIATAEL("scoiatael"),

    NILFGAARD("nilfgaard"),

    SKELLIGE("skellige"),

    NEUTRAL("neutral"),

    SPECIAL("special"),

    WEATHER("weather"),
    UNUSABLE("revenge");

    private String name;

    private static final HashMap<String, Faction> allFactions = new HashMap<>();

    Faction(String name) {
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
        return "cards/" + "faction_" + name + ".jpg";
    }

    public static Faction getFactionByName(String name) {
        return allFactions.get(name);
    }
}

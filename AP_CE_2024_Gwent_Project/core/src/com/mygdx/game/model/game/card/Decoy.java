package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;

import java.util.List;

public class Decoy extends SpellCard{
    public Decoy(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
    }
}

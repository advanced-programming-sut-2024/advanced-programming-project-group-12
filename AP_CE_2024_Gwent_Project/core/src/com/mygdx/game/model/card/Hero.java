package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;

import java.util.List;

public class Hero extends PlayableCard{
    public Hero(String name, String description, Action action, List<Integer> rows, int power, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, power, typeNumber, faction);
    }
}

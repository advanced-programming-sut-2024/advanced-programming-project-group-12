package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;

import java.util.List;

public class Hero extends PlayableCard{
    public Hero(String name, String description, Action action, List<Integer> rows, int power) {
        super(name, description, action, rows, power);
    }
}

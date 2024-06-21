package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;

import java.util.List;

public class CommanderCard extends AbstractCard{

    public CommanderCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        super(name, description, action, rows, typeNumber, faction);
    }

    @Override
    public void kill() {

    }

    @Override
    public void place(int row) {

    }

}

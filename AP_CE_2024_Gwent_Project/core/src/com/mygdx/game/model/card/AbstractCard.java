package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;
import com.mygdx.game.model.Game;
import com.mygdx.game.model.Player;

import java.util.List;

public abstract class AbstractCard implements Cloneable{
    protected List<Integer> allowableRows;
    private String name;
    private String description;
    Action action;
    protected int row;
    protected String  typeNumber;
    private Faction faction;

    public AbstractCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        allowableRows = rows;
        this.name = name;
        this.description = description;
        this.action = action;
        this.typeNumber = typeNumber == null? "" :"_"+ typeNumber;
        this.faction = faction;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Faction getFaction() {
        return faction;
    }

    public List<Integer> getAllowableRows() {
        return allowableRows;
    }
    public int getDefaultRow() {
        return allowableRows.get(0);
    }

    public Action getAction() {
        return action;
    }

    public int getRow() {
        return row;
    }

    public abstract void kill();
    public void place(int row) {
        Player player = Game.getCurrentGame().getCurrentPlayer();
        player.getHand().remove(this);
        this.row = row;
    }
    public void doAction() {
        action.execute(this);
    }

    @Override
    public AbstractCard clone() {
        try {
            AbstractCard clone = (AbstractCard) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AbstractCard)) return false;
        return name.equals(((AbstractCard) obj).name);
    }

    public String getAssetName() {
        return "cards/" + faction.getName()+ "_" + name + typeNumber + ".jpg";
    }
}

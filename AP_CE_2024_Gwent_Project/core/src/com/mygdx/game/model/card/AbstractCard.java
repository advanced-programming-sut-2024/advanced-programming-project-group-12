package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;
import com.mygdx.game.model.Faction;

import java.util.List;

public abstract class AbstractCard implements Cloneable{
    protected List<Integer> allowableRows;
    private String name;
    private String description;
    Action action;
    private String  typeNumber;
    private Faction faction;

    public AbstractCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        allowableRows = rows;
        this.name = name;
        this.description = description;
        this.action = action;
        this.typeNumber = typeNumber == null? "" : typeNumber.toString();
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

    public abstract void kill();
    public abstract void place(int row);
    public void doAction() {
        action.execute();
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

package com.mygdx.game.model.card;

import com.mygdx.game.model.Action;

import java.util.List;

public abstract class AbstractCard implements Cloneable{
    protected List<Integer> allowableRows;
    private String name;
    private String description;
    Action action;
    private Integer typeNumber;

    public AbstractCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber) {
        allowableRows = rows;
        this.name = name;
        this.description = description;
        this.action = action;
        this.typeNumber = typeNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
            // TODO: copy mutable state here, so the clone can't change the internals of the original
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
}

package com.mygdx.game.model.game.card;

import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ActionResponse;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.PlayCardResponse;

import java.util.List;
import java.util.Objects;

public abstract class AbstractCard implements Cloneable{
    protected List<Integer> allowableRows;
    private String name;
    private String description;
    Action action;
    protected int row;
    protected String  typeNumber;
    private Faction faction;

    protected transient Player player;

    public AbstractCard(String name, String description, Action action, List<Integer> rows, Integer typeNumber, Faction faction) {
        allowableRows = rows;
        this.name = name;
        this.description = description;
        this.action = action;
        this.typeNumber = typeNumber == null? "" :"_"+ typeNumber;
        this.faction = faction;
    }

    public String getName() {
        return name+typeNumber;
    }

    public String getAbsName() {
        return name;
    }

    public Player getPlayer() {
        return player;
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
    public void discard() {
        player.getHandAsCards().remove(this);
    }

    public PlayCardResponse place(int row, Player player) {
        this.player = player;
        player.getHand().remove(this.getName());
        this.row = row;
        return null;
    }
    public ActionResponse doAction() {
        return action.execute(this);
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
        if (Objects.equals(faction.getName(), "northern realms")) {
            return "cards/realms" + "_" + name + typeNumber + ".jpg";
        }
        return "cards/" + faction.getName()+ "_" + name + typeNumber + ".jpg";
    }
    public String getInGameAssetName() {
        if (Objects.equals(faction.getName(), "northern realms")) {
            return "gameCards/realms" + "_" + name + typeNumber + ".jpg";
        }
        return "gameCards/" + faction.getName()+ "_" + name + typeNumber + ".jpg";
    }

}

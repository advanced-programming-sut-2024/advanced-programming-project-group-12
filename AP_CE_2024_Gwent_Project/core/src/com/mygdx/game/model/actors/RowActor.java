package com.mygdx.game.model.actors;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.PlayableCard;

import java.util.ArrayList;

public class RowActor extends Actor {
    private ArrayList<CardActor> playableCards;
    private ArrayList<CardActor> spellCards;
    private Container<Table> tableContainer;
    private Table table;
    private int rowNumber;

    public RowActor(int rowNumber) {
        this.rowNumber = rowNumber;
        table = new Table();
        table.setSize(660, 110);
        table.center();
        this.playableCards = new ArrayList<>();
        this.spellCards = new ArrayList<>();
        tableContainer = new Container<>(table);
        tableContainer.setSize(660, 110);
        tableContainer.setTouchable(Touchable.enabled);

    }

    public Container<Table> getTableContainer() {
        return tableContainer;
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<CardActor> getPlayableCards() {
        return playableCards;
    }

    public ArrayList<CardActor> getSpellCards() {
        return spellCards;
    }

    public void addPlayableCard(PlayableCard card) {
        CardActor cardActor = new CardActor(card);
        cardActor.setTouchable(Touchable.disabled);
        cardActor.setSize(70, 110);
        playableCards.add(cardActor);
        table.add(cardActor).pad(5);
        table.pack();
    }

    public void addSpellCard(AbstractCard card) {
        CardActor cardActor = new CardActor(card);
        cardActor.setTouchable(Touchable.disabled);
        cardActor.setSize(70, 110);
        spellCards.add(cardActor);
        table.add(cardActor).pad(5);
        table.pack();
    }

    public void setPosition(Stage stage) {
        if(rowNumber != 1) {
            addPlayableCard(((PlayableCard) AllCards.BROKVA_ARCHER.getAbstractCard()));
            addPlayableCard(((PlayableCard) AllCards.BARCLAY.getAbstractCard()));
            addPlayableCard(((PlayableCard) AllCards.AVALLACH.getAbstractCard()));
            addSpellCard(AllCards.SCORCH.getAbstractCard());
        }
        table.setClip(true);
        table.center();

        // Ensure the table is laid out before getting its width
        table.pack();
        float stageCenterX = stage.getWidth() / 2;

        // Calculate the position of the table so that it is centered in the stage
        float tableX = stageCenterX - table.getWidth() / 2;

        if (rowNumber == 0) {
            table.setPosition(tableX, 220);
        } else if (rowNumber == 1) {
            table.setPosition(tableX, 350);
        } else if (rowNumber == 2) {
            table.setPosition(tableX, 480);
        }
        tableContainer.fill();
        tableContainer.setPosition(640, table.getY());

    }
    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
            return this;
        }
        return null;
    }
}
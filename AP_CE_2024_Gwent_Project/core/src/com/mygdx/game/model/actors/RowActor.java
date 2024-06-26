package com.mygdx.game.model.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.PlayableCard;

import java.util.ArrayList;

public class RowActor extends Actor {
    private ArrayList<CardActor> playableCards;
    private ArrayList<CardActor> spellCards;
    private Table table;
    private int rowNumber;

    public RowActor(int rowNumber, ArrayList<CardActor> playableCards, ArrayList<CardActor> spellCards) {
        this.rowNumber = rowNumber;
        table = new Table();
        table.setSize(800, 200);
        this.playableCards = new ArrayList<>();
        this.spellCards = new ArrayList<>();
        for (CardActor card : playableCards) {
            this.playableCards.add(card);
            table.add(card).pad(5);
        }
        for (CardActor card : spellCards) {
            this.spellCards.add(card);
            table.add(card).pad(5);
        }

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
        playableCards.add(cardActor);
        table.add(cardActor).pad(5);
    }
    public void addSpellCard(AbstractCard card) {
        CardActor cardActor = new CardActor(card);
        spellCards.add(cardActor);
        table.add(cardActor).pad(5);
    }
    public void setPosition(Stage stage) {
        addPlayableCard(((PlayableCard)AllCards.BROKVA_ARCHER.getAbstractCard()));
        addPlayableCard(((PlayableCard)AllCards.BARCLAY.getAbstractCard()));
        addPlayableCard(((PlayableCard)AllCards.AVALLACH.getAbstractCard()));
        addSpellCard(AllCards.SCORCH.getAbstractCard());
        table.setClip(true);
        table.center();

        // Ensure the table is laid out before getting its width
        table.pack();
        float stageCenterX = stage.getWidth() / 2;

        // Calculate the position of the table so that it is centered in the stage
        float tableX = stageCenterX - table.getWidth() / 2 + 60;

        if (rowNumber == 0) {
            table.setPosition(tableX, 240);
        } else if (rowNumber == 1) {
            table.setPosition(tableX, 400);
        } else if (rowNumber == 2) {
            table.setPosition(tableX, 560);
        }

    }
}

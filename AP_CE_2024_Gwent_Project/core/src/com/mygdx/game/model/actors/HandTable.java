package com.mygdx.game.model.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.model.game.card.AbstractCard;

import java.util.ArrayList;
import java.util.LinkedList;

public class HandTable extends Table {
    ArrayList<CardActor> cards;

    public HandTable(LinkedList<AbstractCard> hand) {
        this.setSize(750, 110);
        cards = new ArrayList<>();
        this.setPosition(500, 110);
        for (AbstractCard card : hand) {
            CardActor cardActor = new CardActor(card);
            cards.add(cardActor);
        }

    }
    public ArrayList<CardActor> getCards() {
        return cards;
    }
    public void clear() {
        this.clearChildren();
    }
    public void addToStageAndAddListener(Stage stage) {
        float padding = 7; // adjust this value to add space between cards

        // Clear the table before adding new cards
        this.clear();

        for (CardActor cardActor : cards) {
            // Add the card actor to the table with padding on the right
            this.add(cardActor.getCardTable()).size(85, 130).padRight(padding);

            // Add a listener to the card actor
            cardActor.getCardTable().addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    cardActor.getCardTable().addAction(Actions.moveBy(0, 20, 0.35f));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    cardActor.getCardTable().addAction(Actions.moveBy(0, -20, 0.35f));
                }
            });
        }

        // Add the table to the stage
        stage.addActor(this);

        // Center the table in the stage
        this.center();
    }


}

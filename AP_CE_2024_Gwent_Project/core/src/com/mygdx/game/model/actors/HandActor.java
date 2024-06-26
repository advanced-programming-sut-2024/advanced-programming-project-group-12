package com.mygdx.game.model.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.model.card.AbstractCard;


import java.util.ArrayList;
import java.util.LinkedList;

public class HandActor extends Actor {
    private Table table;
    private ArrayList<CardActor> cards;
    private CardActor selectedCard;

    public HandActor(LinkedList<AbstractCard> cards) {
        table = new Table();
        table.setSize(800, 200);
        this.cards = new ArrayList<>();
        for (AbstractCard card : cards) {
            CardActor cardActor = new CardActor(card);
            this.cards.add(cardActor);
            table.add(cardActor).pad(5);

            // Add a click listener to the card actor
            cardActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Display a larger version of the card on the screen
                    cardActor.setScale(2f); // Adjust the scale factor as needed

                    // Set the card as the selected card
                    selectedCard = cardActor;
                }
            });
        }
    }
    public Table getTable() {
        return table;
    }
    public ArrayList<CardActor> getCards() {
        return cards;
    }
    public CardActor getSelectedCard() {
        return selectedCard;
    }
    public void addCard(AbstractCard card) {
        CardActor cardActor = new CardActor(card);
        cards.add(cardActor);
        table.add(cardActor).pad(10);
    }
    public void removeCard(AbstractCard card) {
        for (CardActor cardActor : cards) {
            if (cardActor.getCard().equals(card)) {
                cards.remove(cardActor);
                table.removeActor(cardActor);
                break;
            }
        }
    }
    public void setSelectedCard(CardActor card) {
        selectedCard = card;
    }
    public void clear() {
        cards.clear();
        table.clear();
    }
    public void setPosition(Stage stage) {
        table.setClip(true);
        table.center();

        // Ensure the table is laid out before getting its width
        table.pack();

        // Calculate the center position of the stage
        float stageCenterX = stage.getWidth() / 2;

        // Calculate the position of the table so that it is centered in the stage
        float tableX = stageCenterX - table.getWidth() / 2 + 60;

        // Set the position of the table
        table.setPosition(tableX, 85);
    }
}

package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.model.game.card.AbstractCard;

public class CardActor extends Actor {
    private AbstractCard card;
    private CardTable cardTable;

    public CardActor(AbstractCard card) {
        this.card = card;

        cardTable = new CardTable(card);

        // Set the size of the card
        setSize(85, 130);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image

        cardTable.draw(batch, parentAlpha);
    }

    public AbstractCard getCard() {
        return card;
    }
    public CardTable getCardTable() {
        return cardTable;
    }
    @Override
    public float getWidth() {
        return cardTable.getWidth();
    }
    @Override
    public void setPosition(float x, float y) {
        cardTable.setPosition(x, y);
    }
    @Override
    public void setSize(float width , float height) {
        super.setSize(width, height);
        cardTable.setSize(width, height);
    }
    public void updatePower(int newPower) {
        cardTable.updatePowerNumber(newPower);
    }
}


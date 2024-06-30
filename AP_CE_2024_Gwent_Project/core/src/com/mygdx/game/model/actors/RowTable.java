package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class RowTable extends Table {
    private List<CardActor> cardActors;
    private final int rowNumber;
    private final boolean side;
    /*
    true : player
    false : enemy
     */
    public RowTable(int rowNumber, boolean side) {
        this.setSize(800, 110);
        this.cardActors = new ArrayList<>();
        this.rowNumber = rowNumber;
        this.side = side;
        this.setTouchable(Touchable.enabled);
        setPosition();
        setDebug(true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw each card in the row
        for (CardActor cardActor : cardActors) {
            cardActor.draw(batch, parentAlpha);
        }
    }

    public List<CardActor> getCards() {
        return cardActors;
    }

    private void setPosition() {
        if (side) {
            this.setPosition(465, 480 - ((rowNumber) * 125));
        } else {
            this.setPosition(465, 485 + ((rowNumber + 1) * 125));
        }
    }
    public boolean getSide() {
        return side;
    }
    public int getRowNumber() {
        return rowNumber;
    }
}
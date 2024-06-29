package com.mygdx.game.model.actors;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Row;
import com.mygdx.game.model.card.AbstractCard;

public class RowActor extends Actor {
    private Row row;
    private GameController controller;

    public RowActor(Row row, GameController controller) {
        this.row = row;
        this.controller = controller;

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractCard selectedCard = controller.getSelectedCard();
                if (selectedCard != null) {
                    controller.playCard(selectedCard, row);
                    controller.setSelectedCard(null); // Unselect the card
                }
            }
        });
    }

    public Row getRow() {
        return row;
    }

}

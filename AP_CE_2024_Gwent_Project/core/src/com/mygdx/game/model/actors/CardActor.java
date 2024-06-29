package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.view.screen.GameScreen;

public class CardActor extends Actor {
    private AbstractCard card;
    private Image image;
    private GameController controller;

    public CardActor(AbstractCard card, GameController controller) {
        this.card = card;
        this.controller = controller;
        image = new Image(new Texture(card.getAssetName()));

        // Set the size of the card
        image.setSize(100, 150);
        //Add click listener to the card
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractCard selectedCard = controller.getSelectedCard();
                if (selectedCard == card) {
                    // If the clicked card is already the selected card, unselect it
                    controller.setSelectedCard(null);
                } else {
                    // Otherwise, select the clicked card
                    controller.setSelectedCard(card);
                }
            }
        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image
        image.draw(batch, parentAlpha);
    }

    public AbstractCard getCard() {
        return card;
    }
}


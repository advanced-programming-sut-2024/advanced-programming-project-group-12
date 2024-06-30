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

    public CardActor(AbstractCard card) {
        this.card = card;
        image = new Image(new Texture(card.getAssetName()));

        // Set the size of the card
        image.setSize(100, 150);
        //Add click listener to the card


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the image

        image.draw(batch, parentAlpha);
    }

    public AbstractCard getCard() {
        return card;
    }
    public Image getImage() {
        return image;
    }
    @Override
    public float getWidth() {
        return image.getWidth();
    }
    @Override
    public void setPosition(float x, float y) {
        image.setPosition(x, y);
    }
}


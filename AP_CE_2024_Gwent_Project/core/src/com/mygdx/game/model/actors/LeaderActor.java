package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.model.game.card.AbstractCard;

import java.awt.*;

public class LeaderActor extends Actor {
    private AbstractCard card;
    private Image image;

    public LeaderActor(AbstractCard card) {
        this.card = card;

        image = new Image(new Texture(card.getAssetName()));
        this.setSize(100, 150);
        image.setSize(100 ,150);
        // Set the size of the card

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
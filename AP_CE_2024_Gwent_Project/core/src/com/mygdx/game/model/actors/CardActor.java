package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.ScreenManager;
import com.mygdx.game.model.card.AbstractCard;
import com.mygdx.game.view.screen.GameScreen;

public class CardActor extends Actor {
    private AbstractCard card;
    private Texture texture;
    private float initialX, initialY;
    private boolean isSelected = false;

    public CardActor(AbstractCard card) {
        this.card = card;
        this.texture = new Texture(card.getAssetName());
        setSize((float) texture.getWidth() / 6, (float) texture.getHeight() / 6);
        setOrigin(getWidth() / 2, getHeight() / 2);

        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                addAction(Actions.scaleTo(1.2f, 1.2f, 0.25f)); // scale up when mouse enters
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                addAction(Actions.scaleTo(1f, 1f, 0.25f)); // scale down when mouse exits
            }
        });
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameController controller = ((GameScreen) ScreenManager.getOnScreen()).getController();
                // Check if the clicked card is the same as the currently selected card
                System.out.println(card.equals(controller.getSelectedCard()));
                if (card.equals(controller.getSelectedCard())) {
                    // The clicked card is the currently selected card, so unselect it
                    controller.setSelectedCard(null);
                } else {
                    // The clicked card is not the currently selected card, so select it
                    controller.setSelectedCard(card);
                }
            }
        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginX(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public AbstractCard getCard() {
        return card;
    }

    public boolean touchDown(float x, float y, int pointer, int button) {
        if (!isSelected) {
            initialX = getX();
            initialY = getY();
            isSelected = true;
        }
        return true;
    }

    public void touchDragged(float x, float y, int pointer) {
        if (isSelected) {
            setPosition(getX() + x - getWidth() / 2, getY() + y - getHeight() / 2);
        }
    }

    public void touchUp(float x, float y, int pointer, int button) {
        isSelected = false;
    }
}
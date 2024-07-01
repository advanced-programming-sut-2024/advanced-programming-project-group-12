package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


import java.util.ArrayList;
import java.util.List;

public class RowTable extends Table {
    private List<CardActor> cardActors;
    private HornArea hornArea;
    private final int rowNumber;
    private final boolean side;
    private Image background;
    private RepeatAction blinkAction;
    /*
    true : player
    false : enemy
     */
    public RowTable(int rowNumber, boolean side) {
        this.setSize(680, 110);
        this.cardActors = new ArrayList<>();
        // Create the hornArea and set its position
        this.hornArea = new HornArea();
        hornArea.setPosition(this.getX() - hornArea.getWidth(), this.getY());

        // Add the hornArea to the RowTable
        this.addActor(hornArea);
        this.rowNumber = rowNumber;
        this.side = side;
        this.setTouchable(Touchable.enabled);
        // Create the background image and set its size and position
        background = new Image(new Texture("bg/empty.png"));
        background.setSize(this.getWidth(), this.getHeight());
        background.setPosition(0, 0);
        background.setColor(Color.CLEAR);
        // Create a semi-transparent yellow color
        Color highlightColor = new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, 0.5f);

        // Create the blinking action
        blinkAction = Actions.forever(Actions.sequence(Actions.color(highlightColor, 1.5f), Actions.color(Color.CLEAR, 1.5f)));
        this.addActor(background);
        setPosition();
        this.center();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Call the super.draw method to draw all actors
        super.draw(batch, parentAlpha);

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
            this.setPosition(590, 480 - ((rowNumber) * 125));
        } else {
            this.setPosition(590, 485 + ((rowNumber + 1) * 125));
        }
    }

    public boolean getSide() {
        return side;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public HornArea getHornArea() {
        return hornArea;
    }
    public void highlight() {
        if (blinkAction != null && !background.hasActions()) {
            background.addAction(blinkAction);
        }
    }
    public void unhighlight() {
        // Check if blinkAction is not null before removing it from the background
        if (blinkAction != null && background.hasActions()) {
            background.removeAction(blinkAction);
        }
        Color highlightColor = new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, 0.5f);
        blinkAction = Actions.forever(Actions.sequence(Actions.color(highlightColor, 1.5f),
                Actions.color(Color.CLEAR, 1.5f)));

        background.setColor(Color.CLEAR);
        hornArea.unhighlight();
    }
}
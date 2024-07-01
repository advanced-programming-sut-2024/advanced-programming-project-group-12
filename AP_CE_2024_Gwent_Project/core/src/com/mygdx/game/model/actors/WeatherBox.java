package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WeatherBox extends Table {
    private RepeatAction blinkAction;
    private Image background;
    public WeatherBox() {
        // Initialize blinkAction like in RowTable
        setSize(255, 160);
        setPosition(110, 425);
        background = new Image(new Texture("bg/empty.png"));
        background.setSize(this.getWidth(), this.getHeight());
        background.setPosition(0, 0);
        background.setColor(Color.CLEAR);
        this.addActor(background);
        Color highlightColor = new Color(Color.MAROON.r, Color.MAROON.g, Color.MAROON.b, 0.5f);
        blinkAction = Actions.forever(Actions.sequence(Actions.color(highlightColor, 1.5f), Actions.color(Color.CLEAR, 1.5f)));
        this.setTouchable(Touchable.enabled);
    }
    public void highlight() {
        if (blinkAction != null && !background.hasActions()) {
            background.addAction(blinkAction);
        }
    }
    public void unhighlight() {
        if (blinkAction != null && background.hasActions()) {
            background.removeAction(blinkAction);
        }
        blinkAction = Actions.forever(Actions.sequence(Actions.color(Color.MAROON, 1.5f), Actions.color(Color.CLEAR, 1.5f)));
        background.setColor(Color.CLEAR);
    }
}
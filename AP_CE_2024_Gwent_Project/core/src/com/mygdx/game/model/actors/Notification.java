package com.mygdx.game.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Gwent;
import com.mygdx.game.view.screen.GameScreen;

public class Notification {

    public String message;
    public String pathToAsset;

    public Notification(String message, String pathToAsset) {
        this.message = message;
        this.pathToAsset = pathToAsset;
    }
}
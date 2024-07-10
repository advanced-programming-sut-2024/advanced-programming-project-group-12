package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Texture;

public enum Emoji {
    LAUGH("laugh"),
    CRY("cry"),
    THUMBS_UP("thumbs-up"),
    KISS("kiss"),
    HEART("heart"),
    ;
    String path;
    Emoji(String name) {
        this.path = "emoji/" + name + ".png";
    }
    public Texture getTexture() {
        return new Texture(path);
    }
}


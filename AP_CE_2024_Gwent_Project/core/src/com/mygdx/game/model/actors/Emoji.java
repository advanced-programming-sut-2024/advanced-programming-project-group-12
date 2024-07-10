package com.mygdx.game.model.actors;

import com.badlogic.gdx.graphics.Texture;

public enum Emoji {
    LAUGH("laugh"),
    CRY("cry"),
    TOMB_UP("tombs-up"),
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

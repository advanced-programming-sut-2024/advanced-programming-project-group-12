package com.mygdx.game.controller.local;

import com.badlogic.gdx.graphics.Texture;

public enum AssetManager {
    REGISTER_BACKGROUND_PICTURE("register_background.jpg"),
    LOGIN_BACKGROUND_PICTURE("register_background.jpg"),
    ;
    final String path;

    AssetManager(String path) {
        this.path = path;
    }
    public Texture getTexture() {
        try {
            return new Texture(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

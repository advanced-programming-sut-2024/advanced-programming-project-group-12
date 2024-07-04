package com.mygdx.game.model;

import com.badlogic.gdx.Screen;
import com.mygdx.game.view.screen.LoginMenuScreen;
import com.mygdx.game.view.screen.MainMenuScreen;

public enum Screens {
    LOGIN_SCREEN,
    MAIN_MENU_SCREEN;
    Screens() {}
    public Screen createScreen() {
        Screen screen = null;
        switch (this) {
            case LOGIN_SCREEN :
                screen = new LoginMenuScreen();
            break;
            case MAIN_MENU_SCREEN :
                screen = new MainMenuScreen();
            break;
        }
        return screen;
    }
}

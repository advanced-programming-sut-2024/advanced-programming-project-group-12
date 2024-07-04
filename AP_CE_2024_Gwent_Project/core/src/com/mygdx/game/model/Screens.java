package com.mygdx.game.model;

import com.badlogic.gdx.Screen;
import com.mygdx.game.view.screen.LoginMenuScreen;
import com.mygdx.game.view.screen.MainMenuScreen;

public enum Screens {
    LOGIN,
    MAIN_MENU,
    PROFILE_MENU,
    GAME,
    LEADER_MENU,
    PRE_GAME_MENU,
    FRIENDS,
    REGISTER,
    CHOOSE_SECURITY_QUESTION,
    FORGOT_PASSWORD;
    ;
    Screens() {}
    public Screen createScreen() {
        Screen screen = null;
        switch (this) {
            case LOGIN:
                screen = new LoginMenuScreen();
            break;
            case MAIN_MENU :
                screen = new MainMenuScreen();
            break;
        }
        return screen;
    }
}

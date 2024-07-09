package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.mygdx.game.view.screen.*;

public enum Screens {
    LOGIN,
    MAIN_MENU,
    PROFILE_MENU,
    GAME,
    LEADER_BOARD_MENU,
    PRE_GAME_MENU,
    FRIENDS,
    REGISTER,
    CHOOSE_SECURITY_QUESTION,
    FORGET_PASSWORD,
    GAME_REQUEST,
    BROADCAST;
    ;
    public Screen createScreen() {
        Screen screen = null;
        switch (this) {
            case LOGIN:
                screen = new LoginMenuScreen();
            break;
            case MAIN_MENU :
                screen = new MainMenuScreen();
            break;
            case PRE_GAME_MENU:
                screen = new PreGameMenuScreen();
                break;
            case PROFILE_MENU:
                screen = new ProfileMenuScreen();
                break;
            case GAME:
                screen = new GameScreen();
                break;
            case LEADER_BOARD_MENU:
                screen = new LeaderBoardMenuScreen();
                break;
            case FRIENDS:
                screen = new FriendsScreen();
                break;
            case REGISTER:
                screen = new RegisterMenuScreen();
                break;
            case CHOOSE_SECURITY_QUESTION:
                screen = new ChooseSecurityQuestionScreen();
                break;
            case FORGET_PASSWORD:
                screen = new ForgetPasswordScreen();
                break;
            case GAME_REQUEST:
                screen = new GameRequestScreen();
                break;
            case BROADCAST:
                screen = new BroadCastScreen();
        }
        return screen;
    }
}

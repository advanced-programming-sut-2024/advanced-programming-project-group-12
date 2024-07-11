package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.mygdx.game.view.screen.*;

import java.util.Objects;

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
    TOURNAMENT,
    PRE_TOURNAMENT,
    GAME_REQUEST,
    BROAD_CAST,
    LIVE_STREAM_MENU;

    public static final Object lock = new Object();

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
            case TOURNAMENT:
                screen = new TournamentScreen();
                break;
            case PRE_TOURNAMENT:
                screen = new PreTournamentScreen();
                break;
            case LIVE_STREAM_MENU:
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                screen = new LiveStreamMenuScreen();
                break;
            case BROAD_CAST:
                screen = new BroadCastScreen();
                break;
        }
        return screen;
    }
}

package com.mygdx.game.controller;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Gwent;
import com.mygdx.game.model.User;
import com.mygdx.game.view.screen.*;

public class ScreenManager {
    private static Screen onScreen;
    public static void setRegisterScreen() {
        // Set the register screen
        onScreen = new RegisterMenuScreen();
        updateScreen();
    }
    public static void setLoginScreen() {
        // Set the login screen
        onScreen = new LoginMenuScreen();
        updateScreen();
    }
    public static void setMainMenuScreen() {
        // Set the main menu screen
        onScreen = new MainMenuScreen();
        updateScreen();
    }
    public static void setChooseSecurityQuestionScreen() {
        // Set the choose security question screen
        onScreen = new ChooseSecurityQuestionScreen();
        updateScreen();
    }
    public static void setForgotPasswordScreen() {
        // Set the forgot password screen
        onScreen = new ForgetPasswordScreen();
        updateScreen();
    }
    public static void setProfileMenuScreen() {
        // Set the profile menu screen
        onScreen = new ProfileMenuScreen();
        updateScreen();
    }
    public static void setPreGameMenuScreen(){
        onScreen = new PreGameMenuScreen();
        updateScreen();
    }

    public static void setGameScreen() {
        // Set the game menu screen
        onScreen = new GameScreen();
        updateScreen();
    }
    public static Screen getOnScreen() {
        return onScreen;
    }
    public static void updateScreen() {
        Gwent.singleton.setScreen(onScreen);
    }
    public static void setOnScreen(Screen screen) {
        onScreen = screen;
    }
    public static void setLeaderBoardScreen() {
        // Set the leaderboard screen
        Gwent.singleton.setScreen(new LeaderBoardMenuScreen());
    }

    public static void setFriendsScreen() {
        // Set the friends screen
        Gwent.singleton.setScreen(new FriendsScreen());
    }

    public static void setGameRequestScreen() {
        // Set the game request screen
        Gwent.singleton.setScreen(new GameRequestScreen());
    }
}

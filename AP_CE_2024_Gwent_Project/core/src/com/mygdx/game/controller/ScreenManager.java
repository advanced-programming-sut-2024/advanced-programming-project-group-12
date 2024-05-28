package com.mygdx.game.controller;

import com.mygdx.game.Gwent;
import com.mygdx.game.view.screen.*;

public class ScreenManager {
    public static void setRegisterScreen() {
        // Set the register screen
        Gwent.singleton.setScreen(new RegisterMenuScreen());
    }
    public static void setLoginScreen() {
        // Set the login screen
        Gwent.singleton.setScreen(new LoginMenuScreen());
    }
    public static void setMainMenuScreen() {
        // Set the main menu screen
        Gwent.singleton.setScreen(new MainMenuScreen());
    }
    public static void setChooseSecurityQuestionScreen() {
        // Set the choose security question screen
        Gwent.singleton.setScreen(new ChooseSecurityQuestionScreen());
    }
    public static void setForgotPasswordScreen() {
        // Set the forgot password screen
        Gwent.singleton.setScreen(new ForgetPasswordScreen());
    }
    public static void setProfileMenuScreen() {
        // Set the profile menu screen
        Gwent.singleton.setScreen(new ProfileMenuScreen());
    }

    public static void setUserInfoScreen() {
        Gwent.singleton.setScreen(new UserInfoScreen());
    }
}

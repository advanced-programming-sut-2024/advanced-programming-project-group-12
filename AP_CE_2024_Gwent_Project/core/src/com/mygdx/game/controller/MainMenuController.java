package com.mygdx.game.controller;


import com.mygdx.game.Gwent;
import com.mygdx.game.view.screen.GameScreen;
import com.mygdx.game.view.screen.ProfileMenuScreen;

public class MainMenuController {



    public void startGame() {
        ScreenManager.setPreGameMenuScreen();
    }


    public void showProfile() {
        ScreenManager.setProfileMenuScreen();
    }

    public void exit() { System.exit(0); }

    public void showLeaderBoard() {
        ScreenManager.setLeaderBoardScreen();}
}

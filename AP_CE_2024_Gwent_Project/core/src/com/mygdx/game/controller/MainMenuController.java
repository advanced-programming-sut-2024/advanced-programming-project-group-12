package com.mygdx.game.controller;


import com.mygdx.game.Gwent;
import com.mygdx.game.view.Screens;
import com.mygdx.game.view.screen.GameScreen;
import com.mygdx.game.view.screen.ProfileMenuScreen;

public class MainMenuController {



    public void startGame() {
        Gwent.singleton.changeScreen(Screens.PRE_GAME_MENU);
    }


    public void showProfile() {
        Gwent.singleton.changeScreen(Screens.PROFILE_MENU);
    }

    public void exit() { System.exit(0); }

    public void showLeaderBoard() {
        Gwent.singleton.changeScreen(Screens.LEADER_BOARD_MENU);
    }
}

package com.mygdx.game.controller;


import com.mygdx.game.Gwent;
import com.mygdx.game.view.screen.GameScreen;
import com.mygdx.game.view.screen.ProfileMenuScreen;

public class MainMenuController {



    public void startGame() {
        Gwent.singleton.setScreen(new GameScreen());
    }


    public void showProfile() {
        Gwent.singleton.setScreen(new ProfileMenuScreen());
    }

}

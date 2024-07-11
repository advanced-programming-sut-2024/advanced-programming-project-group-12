package com.mygdx.game.controller.local;


import com.mygdx.game.Gwent;
import com.mygdx.game.view.Screens;

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

    public void showLiveStream() {
        //TODO : first set games for user here after that change screen
        Gwent.singleton.changeScreen(Screens.LIVE_STREAM_MENU);
    }
}

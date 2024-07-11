package com.mygdx.game.controller.local;


import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetPublicGamesRequest;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;

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
        Client.getInstance().sendMassage(new GetPublicGamesRequest());
        Gwent.singleton.changeScreen(Screens.LIVE_STREAM_MENU);
    }
}

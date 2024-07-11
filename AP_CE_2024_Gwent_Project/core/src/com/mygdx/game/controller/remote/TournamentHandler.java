package com.mygdx.game.controller.remote;

import com.mygdx.game.model.game.Tournament;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartTournamentGameRequest;
import com.mygdx.game.model.user.User;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class TournamentHandler {
    private static Tournament currentTournament;

    public static Tournament getCurrentTournament() {
        return currentTournament;
    }

    public static void startGame(StartTournamentGameRequest startTournamentGameRequest, RequestHandler requestHandler) {
        User user1 = startTournamentGameRequest.getUser1();
        GameHandler gameHandler = new GameHandler(user1, false, true);
        requestHandler.setGameHandler(gameHandler);

        User user2 = startTournamentGameRequest.getUser2();
        RequestHandler targetHandler = RequestHandler.allUsers.get(user2.getUsername());
        targetHandler.setGameHandler(gameHandler);
        gameHandler.addUserAndStart(user2);
    }
}

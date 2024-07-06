package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class InviteHandler {
    private static GameHandler queGameHandler = null;
    private String request;
    private Gson gson;

    public InviteHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handle(RequestHandler requestHandler, User user) {
        StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);

        //updating user in remote

        user.setDeck(new ArrayList<>(startGameRequest.getDeck()));
        user.setLeader(startGameRequest.getCommanderCard());
        user.setFaction(startGameRequest.getFaction());

        if(startGameRequest.isRandomOpponent()) {
            if(queGameHandler == null) {
                queGameHandler = new GameHandler(user);
                requestHandler.setGameHandler(queGameHandler);
            }
            else {
                queGameHandler.addUserAndStart(user);
                queGameHandler = null;
            }
            return;
        }

        requestHandler.setGameHandler(new GameHandler(user));

        if(!RequestHandler.allUsers.containsKey(startGameRequest.getUserToBeInvited())) {
            //handle the case
        }

        RequestHandler targetHandler = RequestHandler.allUsers.get(startGameRequest.getUserToBeInvited());
        targetHandler.sendMassage(new ServerPlayInvite(startGameRequest));
    }
}

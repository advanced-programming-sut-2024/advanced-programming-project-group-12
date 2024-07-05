package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.user.User;

public class InviteHandler {
    private String request;
    private Gson gson;

    public InviteHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handle() {
        StartGameRequest startGameRequest = gson.fromJson(request, StartGameRequest.class);
        User toBeInvited = User.getUserByUsername(startGameRequest.getUserToBeInvited());
        if(!RequestHandler.allUsers.containsKey(toBeInvited)) {
            //handle the case
        }
        RequestHandler targetHandler = RequestHandler.allUsers.get(toBeInvited);
        targetHandler.sendMassage(new ServerPlayInvite(startGameRequest));
    }
}

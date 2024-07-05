package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.user.User;

public class InviteResponseHandler {
    private String request;
    private Gson gson;

    public InviteResponseHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handle() {
        ClientInviteResponse clientInviteResponse = gson.fromJson(request, ClientInviteResponse.class);
        User invitor = User.getUserByUsername(clientInviteResponse.getInvitor());
        if(!RequestHandler.allUsers.containsKey(invitor)) {
            //handle the case
        }
        RequestHandler targetHandler = RequestHandler.allUsers.get(invitor);
        targetHandler.sendMassage(new ServerInviteResponse(clientInviteResponse));
    }
}

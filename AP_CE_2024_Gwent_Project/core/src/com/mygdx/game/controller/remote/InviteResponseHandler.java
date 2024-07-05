package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientInviteResponse;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.StartGameRequest;
import com.mygdx.game.model.network.massage.serverResponse.ChangeMenuResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerInviteResponse;
import com.mygdx.game.model.network.massage.serverResponse.gameResponse.ServerPlayInvite;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;

public class InviteResponseHandler {
    private String request;
    private Gson gson;

    public InviteResponseHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handle(RequestHandler requestHandler, User user) {
        ClientInviteResponse clientInviteResponse = gson.fromJson(request, ClientInviteResponse.class);
        User invitor = User.getUserByUsername(clientInviteResponse.getInvitor());
        if(!RequestHandler.allUsers.containsKey(invitor.getUsername())) {
            //handle the case
        }
        RequestHandler targetHandler = RequestHandler.allUsers.get(invitor.getUsername());
        targetHandler.sendMassage(new ServerInviteResponse(clientInviteResponse));

        if(clientInviteResponse.getResponse().equals("accept")) {
            user.setFaction(clientInviteResponse.getFaction());
            user.setLeader(clientInviteResponse.getCommanderCard());
            user.setDeck(new ArrayList<>(clientInviteResponse.getDeck()));

            requestHandler.setGameHandler(targetHandler.getGameHandler());
            requestHandler.getGameHandler().addUser(user);

            requestHandler.sendMassage(new ChangeMenuResponse(Screens.GAME));
            targetHandler.sendMassage(new ChangeMenuResponse(Screens.GAME));
        }
        else {
            targetHandler.setGameHandler(null);
        }
    }
}

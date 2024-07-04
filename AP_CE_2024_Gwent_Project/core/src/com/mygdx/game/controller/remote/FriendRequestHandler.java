package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerFriendRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class FriendRequestHandler {
    private String request;
    private Gson gson;

    public FriendRequestHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public ServerFriendRequest handle() {
        ClientFriendRequest friendRequest = gson.fromJson(request, ClientFriendRequest.class);
        if(friendRequest.getFriendRequest().getStatus().equals("pending")) {
            friendRequest.getFriendRequest().getToUser().addFriendRequest();
        }
        else if(friendRequest.getFriendRequest().getStatus().equals("accepted")){
            friendRequest.getFriendRequest().getToUser().requestAccepted(friendRequest);
        }
        return null;
    }
}

package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerFriendRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

public class FriendRequestHandler {
    private String request;
    private Gson gson;

    public FriendRequestHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handleSendingRequest() {
        ClientFriendRequest friendRequest = gson.fromJson(request, ClientFriendRequest.class);
        if(friendRequest.getFriendRequest().getStatus().equals("pending")) {
            friendRequest.getFriendRequest().getToUser().addFriendRequest(friendRequest.getFriendRequest());
        }
        else if(friendRequest.getFriendRequest().getStatus().equals("accepted")){
            //toUser is the user who has initially sent the request
            friendRequest.getFriendRequest().getFromUser().requestAccepted(friendRequest.getFriendRequest());
        }
        else if(friendRequest.getFriendRequest().getStatus().equals("rejected")){
            friendRequest.getFriendRequest().getFromUser().requestRejected(friendRequest.getFriendRequest());
        }
    }

    public ServerFriendRequest getPendingRequests(User user) {
        User.getUserByUsername(user.getUsername());
        return new ServerFriendRequest(user.getReceivedFriendRequests());
    }
}

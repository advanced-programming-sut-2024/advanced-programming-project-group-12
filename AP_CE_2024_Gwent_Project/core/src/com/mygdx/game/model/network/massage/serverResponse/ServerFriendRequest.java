package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.session.Session;

import java.util.ArrayList;

public class ServerFriendRequest extends ServerResponse{
    private ArrayList<FriendRequest> requests;

    public ServerFriendRequest(ArrayList<FriendRequest> requests) {
        super(ServerResponseType.FRIEND_REQUEST, null);
        this.requests = requests;
    }

    public ArrayList<FriendRequest> getRequests() {
        return requests;
    }
}

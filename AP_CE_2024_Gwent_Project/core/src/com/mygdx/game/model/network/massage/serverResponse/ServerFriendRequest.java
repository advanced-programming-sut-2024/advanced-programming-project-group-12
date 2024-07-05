package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.session.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerFriendRequest extends ServerResponse{
    private HashMap<String , HashMap<String , FriendRequest>> requests;

    public ServerFriendRequest(HashMap<String , HashMap<String , FriendRequest>> requests) {
        super(ServerResponseType.FRIEND_REQUEST, null);
        this.requests = requests;
    }

    public HashMap<String , HashMap<String , FriendRequest>> getRequests() {
        return requests;
    }
}

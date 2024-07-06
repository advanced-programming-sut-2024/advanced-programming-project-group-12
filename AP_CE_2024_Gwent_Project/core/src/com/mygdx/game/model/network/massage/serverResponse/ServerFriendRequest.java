package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.FriendRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerFriendRequest extends ServerResponse{
    private Map<String , Map<String , FriendRequest>> requests;

    public ServerFriendRequest(Map<String, Map<String, FriendRequest>> requests) {
        super(ServerResponseType.FRIEND_REQUEST, null);
        this.requests = requests;
    }

    public Map<String , Map<String , FriendRequest>> getRequests() {
        return requests;
    }
}

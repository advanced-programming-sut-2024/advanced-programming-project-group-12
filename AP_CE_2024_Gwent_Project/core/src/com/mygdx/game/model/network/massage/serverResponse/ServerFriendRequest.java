package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.FriendRequest;
import com.mygdx.game.model.network.session.Session;

public class ServerFriendRequest extends ServerResponse{
    private FriendRequest friendRequest;

    public ServerFriendRequest(ServerResponseType type, Session session) {
        super(type, session);
    }
}

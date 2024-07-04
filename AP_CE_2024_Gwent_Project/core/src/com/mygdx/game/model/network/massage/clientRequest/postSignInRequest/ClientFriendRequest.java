package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class ClientFriendRequest extends PostSignInRequest {
    private FriendRequest friendRequest;
    public ClientFriendRequest(FriendRequest friendRequest) {
        super(ClientRequestType.FRIEND_REQUEST, null);
        this.friendRequest = friendRequest;
    }
}

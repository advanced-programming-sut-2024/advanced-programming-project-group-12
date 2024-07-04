package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class ClientFriendRequest extends ClientRequest {
    private FriendRequest friendRequest;
    public ClientFriendRequest(FriendRequest friendRequest) {
        super(ClientRequestType.FRIEND_REQUEST);
        this.friendRequest = friendRequest;
    }

    public FriendRequest getFriendRequest() {
        return friendRequest;
    }
}

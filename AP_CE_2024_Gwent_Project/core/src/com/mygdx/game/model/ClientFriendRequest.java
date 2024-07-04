package com.mygdx.game.model;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.PostSignInRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;

public class ClientFriendRequest extends PostSignInRequest {
    private FriendRequest friendRequest;
    public ClientFriendRequest() {
        super(ClientRequestType.FRIEND_REQUEST, null);
    }
}

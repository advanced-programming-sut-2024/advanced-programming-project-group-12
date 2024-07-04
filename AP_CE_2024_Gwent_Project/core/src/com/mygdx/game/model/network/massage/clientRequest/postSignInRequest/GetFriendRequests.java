package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class GetFriendRequests extends ClientRequest {
    public GetFriendRequests() {
        super(ClientRequestType.GET_FRIEND_REQUESTS);
    }
}

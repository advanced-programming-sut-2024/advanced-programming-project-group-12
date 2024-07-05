package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class GetFriends extends ClientRequest {
    public GetFriends() {
        super(ClientRequestType.GET_FRIENDS, null);
    }
}

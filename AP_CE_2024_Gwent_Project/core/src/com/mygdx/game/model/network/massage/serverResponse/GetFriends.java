package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.network.session.Session;

public class GetFriends extends ClientRequest {
    public GetFriends() {
        super(ClientRequestType.GET_FRIENDS, null);
    }
}

package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class GetPublicGamesRequest extends ClientRequest {
    public GetPublicGamesRequest() {
        super(ClientRequestType.GET_PUBLIC_GAMES);
    }
}

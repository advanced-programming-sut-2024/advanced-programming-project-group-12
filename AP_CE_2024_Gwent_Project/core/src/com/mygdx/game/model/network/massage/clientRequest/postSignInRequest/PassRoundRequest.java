package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class PassRoundRequest extends ClientRequest {
    public PassRoundRequest() {
        super(ClientRequestType.PASS_ROUND);
    }
}

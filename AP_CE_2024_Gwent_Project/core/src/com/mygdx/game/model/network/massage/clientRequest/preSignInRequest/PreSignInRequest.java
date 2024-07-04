package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public abstract class PreSignInRequest extends ClientRequest {
    public PreSignInRequest(ClientRequestType type) {
        super(type);
    }
}

package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequestType;

public abstract class PreSignInRequest extends ClientRequest {
    public PreSignInRequest(ClientRequestType type) {
        super(type);
    }
}

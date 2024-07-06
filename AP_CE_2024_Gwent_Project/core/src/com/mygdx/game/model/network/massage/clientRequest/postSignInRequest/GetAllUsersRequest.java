package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class GetAllUsersRequest extends ClientRequest {
    public GetAllUsersRequest() {
        super(ClientRequestType.GET_ALL_USERS);
    }
}

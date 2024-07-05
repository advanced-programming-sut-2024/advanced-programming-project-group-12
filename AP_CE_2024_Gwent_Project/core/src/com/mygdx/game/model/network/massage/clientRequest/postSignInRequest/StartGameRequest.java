package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class StartGameRequest extends ClientRequest {
    private String invitor;
    private String toBeInvited;

    public StartGameRequest(String toBeInvited) {
        super(ClientRequestType.START_GAME, null);
        this.toBeInvited = toBeInvited;
    }

    public String getUserToBeInvited() {
        return toBeInvited;
    }
}

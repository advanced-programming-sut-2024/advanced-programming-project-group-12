package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class StartGameRequest extends ClientRequest {
    private User toBeInvited;

    public StartGameRequest(Session session, User toBeInvited) {
        super(ClientRequestType.START_GAME, session);
        this.toBeInvited = toBeInvited;
    }

    public User getUserToBeInvited() {
        return toBeInvited;
    }
}

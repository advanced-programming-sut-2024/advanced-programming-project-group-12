package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class StartGameRequest extends PostSignInRequest{
    private User toBeInvited;

    public StartGameRequest(ClientRequestType type, Session session, User toBeInvited) {
        super(type, session);
        this.toBeInvited = toBeInvited;
    }

    public User getUserToBeInvited() {
        return toBeInvited;
    }
}

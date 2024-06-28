package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.User;
import com.mygdx.game.model.network.massage.Session;

public class StartGameInviteRequest extends ClientRequest{
    private User toBeInvited;

    public StartGameInviteRequest(ClientRequestType type, Session session, User toBeInvited) {
        super(type, session);
        this.toBeInvited = toBeInvited;
    }
}

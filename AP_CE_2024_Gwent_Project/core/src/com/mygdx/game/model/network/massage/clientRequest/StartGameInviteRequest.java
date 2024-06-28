package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.User;

public class StartGameInviteRequest extends ClientRequest{
    private User toBeInvited;

    public StartGameInviteRequest(ClientRequestType type, String token, User toBeInvited) {
        super(type, token);
        this.toBeInvited = toBeInvited;
    }
}

package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.User;

public class SignInRequest extends ClientRequest {
    private final User user;

    public SignInRequest(ClientRequestType type, String token, User user) {
        super(type, null);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

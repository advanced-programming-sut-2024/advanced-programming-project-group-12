package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientRequestType;
import com.mygdx.game.model.user.User;

public class SignInRequest extends PreSignInRequest {
    private final User user;

    public SignInRequest(ClientRequestType type, String token, User user) {
        super(type);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

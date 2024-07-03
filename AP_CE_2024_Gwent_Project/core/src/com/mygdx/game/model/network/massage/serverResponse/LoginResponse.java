package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class LoginResponse extends ServerResponse {
    User user;
    String error;
    public LoginResponse(ServerResponseType type ,User user) {
        super(type);
        super.session = new Session(user);
        this.user = user;
    }
    public LoginResponse(ServerResponseType type , String error) {
        super(type);
        this.error = error;
        super.session = new Session(user);
    }

    public User getUser() {
        return user;
    }

    public String getError() {
        return error;
    }
}

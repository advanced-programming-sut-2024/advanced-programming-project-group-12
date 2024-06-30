package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.user.User;
import com.mygdx.game.model.network.session.Session;

public class LoginResponse extends ServerResponse {
    public LoginResponse(User user) {
        super(ServerResponseType.LOGIN_CONFIRM);
        super.session = new Session(user);
    }
}

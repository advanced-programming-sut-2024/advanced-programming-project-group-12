package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class SignUpRequest extends ClientRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;

    public SignUpRequest(String username, String password, String nickname, String email) {
        super(ClientRequestType.SIGN_IN);
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}

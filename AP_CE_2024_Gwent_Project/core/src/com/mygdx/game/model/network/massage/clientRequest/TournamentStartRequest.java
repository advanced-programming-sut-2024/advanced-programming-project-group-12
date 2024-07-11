package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class TournamentStartRequest extends ClientRequest{

    public TournamentStartRequest() {
        super(ClientRequestType.START_TOURNAMENT);
    }

}

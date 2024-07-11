package com.mygdx.game.model.network.massage.clientRequest;

import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class TournamentStartRequest extends ClientRequest{
    ArrayList<User> users;

    public TournamentStartRequest(ArrayList<User> users) {
        super(ClientRequestType.START_TOURNAMENT);
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}

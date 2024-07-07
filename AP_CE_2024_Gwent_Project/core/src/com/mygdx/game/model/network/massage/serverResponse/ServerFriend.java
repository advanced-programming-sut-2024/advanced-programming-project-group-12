package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class ServerFriend extends ServerResponse{
    private ArrayList<String> friends;
    public ServerFriend( ArrayList<String> friends) {
        super(ServerResponseType.GET_FRIENDS, null);
        this.friends = friends;
    }
    public ArrayList<String> getFriends() {
        return friends;
    }
}

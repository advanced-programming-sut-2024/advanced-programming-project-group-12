package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;

public class ServerFriend extends ServerResponse{
    private ArrayList<User> friends;
    public ServerFriend( ArrayList<User> friends) {
        super(ServerResponseType.GET_FRIENDS, null);
        this.friends = friends;
    }
    public ArrayList<User> getFriends() {
        return friends;
    }
}

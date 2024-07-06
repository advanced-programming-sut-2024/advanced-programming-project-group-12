package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAllUsersResponse extends ServerResponse{
    private ArrayList<HashMap<String, UserScoreAndOnline>> allUsers = new ArrayList<>();

    public GetAllUsersResponse(ArrayList<User> users) {
        super(ServerResponseType.GET_ALL_USERS, null);

        for(User u: users) {
            HashMap<String, UserScoreAndOnline> h = new HashMap<>();
            h.put(u.getUsername(), new UserScoreAndOnline(RequestHandler.allUsers.containsKey(u.getUsername()), u.getScore()));
            allUsers.add(h);
        }
    }
}

class UserScoreAndOnline {
    boolean online;
    int score;

    public UserScoreAndOnline(boolean online, int score) {
        this.online = online;
        this.score = score;
    }
}

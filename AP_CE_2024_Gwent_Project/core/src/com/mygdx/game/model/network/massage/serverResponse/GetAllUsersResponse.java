package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.UserScoreAndOnline;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Collections;

public class GetAllUsersResponse extends ServerResponse{
    private ArrayList<UserScoreAndOnline> allUsers = new ArrayList<>();

    public GetAllUsersResponse(ArrayList<User> users) {
        super(ServerResponseType.GET_ALL_USERS, null);

        for(User u: users) {
            allUsers.add(new UserScoreAndOnline(RequestHandler.allUsers.containsKey(u.getUsername()), u.getScore(), u.getUsername()));
        }
    }

    public ArrayList<UserScoreAndOnline> getAllUsers() {
        Collections.sort(allUsers);
        return allUsers;
    }
}


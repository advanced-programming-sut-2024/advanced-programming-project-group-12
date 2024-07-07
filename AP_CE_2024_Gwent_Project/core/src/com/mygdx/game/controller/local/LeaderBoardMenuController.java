package com.mygdx.game.controller.local;

import com.mygdx.game.model.UserScoreAndOnline;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetAllUsersRequest;
import com.mygdx.game.model.network.massage.serverResponse.GetAllUsersResponse;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoardMenuController {

    public LeaderBoardMenuController() {
    }

    public int getUserWinCount(User user) {
        return user.getUserInfo().getGamesWon();
    }

    public ArrayList<UserScoreAndOnline> getSortedUsers() {
        Client.getInstance().sendMassage(new GetAllUsersRequest());
        ArrayList<UserScoreAndOnline> users = GetAllUsersResponse.getAllUsers();
        for (UserScoreAndOnline user : users) {
            System.out.println(user.getUsername() + " " + user.getScore() + user.isOnline());
        }

        return users;
    }
}

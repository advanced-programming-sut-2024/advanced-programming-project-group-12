package com.mygdx.game.controller.local;

import com.mygdx.game.model.UserScoreAndOnline;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetAllUsersRequest;
import com.mygdx.game.model.network.massage.serverResponse.GetAllUsersResponse;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoardMenuController {
    private static ArrayList<UserScoreAndOnline> users ;

    public LeaderBoardMenuController() {
    }

    public static void setUsers(ArrayList<UserScoreAndOnline> users) {
        LeaderBoardMenuController.users = users;
    }

    public int getUserWinCount(User user) {
        return user.getUserInfo().getGamesWon();
    }

    public ArrayList<UserScoreAndOnline> getSortedUsers() {
        Client.getInstance().sendMassage(new GetAllUsersRequest());

        for (UserScoreAndOnline user : users) {
            System.out.println(user.getUsername() + " " + user.getScore() + user.isOnline());
        }

        return users;
    }


}

package com.mygdx.game.controller.local;

import com.mygdx.game.model.UserScoreAndOnline;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetAllUsersRequest;
import com.mygdx.game.model.network.massage.serverResponse.GetAllUsersResponse;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class LeaderBoardMenuController {
    private static ArrayList<UserScoreAndOnline> users ;
    private final static Object lock = new Object();

    public LeaderBoardMenuController() {
    }

    public static void setUsers(ArrayList<UserScoreAndOnline> users) {
        synchronized (lock){
            LeaderBoardMenuController.users = users;
            lock.notify();
        }
    }

    public int getUserWinCount(User user) {
        return user.getUserInfo().getGamesWon();
    }

    public ArrayList<UserScoreAndOnline> getSortedUsers() {
        Client.getInstance().sendMassage(new GetAllUsersRequest());
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println("interruption in leaderBoard menu");;
            }
        }

        for (UserScoreAndOnline user : users) {
            System.out.println(user.getUsername() + " " + user.getScore() + user.isOnline());
        }

        return users;
    }
}

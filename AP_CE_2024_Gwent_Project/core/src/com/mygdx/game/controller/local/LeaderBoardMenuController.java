package com.mygdx.game.controller.local;

import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoardMenuController {


    public LeaderBoardMenuController() {

    }

    public int getUserWinCount(User user) {
        return user.getUserInfo().getGamesWon();
    }

    public ArrayList<User> sortedUsers() {
        ArrayList<User> users = User.getAllUsers();
        users.sort(Comparator.comparingInt(this::getUserWinCount).reversed());
        return users;
    }
}

package com.mygdx.game.controller;

import com.mygdx.game.model.user.User;
import com.mygdx.game.model.user.UserInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

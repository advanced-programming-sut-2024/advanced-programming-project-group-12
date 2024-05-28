package com.mygdx.game.controller;

import com.mygdx.game.model.User;
import com.mygdx.game.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfoController {
    public HashMap<String , Integer> getUserInfo () {
        User loggedInUser = User.getLoggedInUser();
        UserInfo userInfo = loggedInUser.getUserInfo();
        HashMap<String , Integer> userInfoMap = new HashMap<>();
        userInfoMap.put("highest score" , userInfo.getHighestScore());
        userInfoMap.put("total score" , userInfo.getTotalScore());
        userInfoMap.put("games played" , userInfo.getGamesPlayed());
        userInfoMap.put("games won" , userInfo.getGamesWon());
        userInfoMap.put("games lost" , userInfo.getGamesLost());
        userInfoMap.put("games drawn" , userInfo.getGamesDrawn());
        return userInfoMap;
    }
}

package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static User loggedInUser;
    private static ArrayList<User> users;


    private String username;
    private String password;
    private String email;
    private HashMap<Integer, String> securityQuestion;
    private ArrayList<Game> allGamePlayed;
    private UserInfo userInfo;



    public User(String username, String password, String email, int questionNumber, String answer) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.securityQuestion = new HashMap<>();
        this.securityQuestion.put(questionNumber, answer);
        this.allGamePlayed = new ArrayList<>();
        this.userInfo = new UserInfo();
    }

    public static void updateRanking() {

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public void setUsername(String newUsername) {

    }
    public void setEmail(String newEmail) {

    }
    public void setPassword(String newPassword) {

    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}

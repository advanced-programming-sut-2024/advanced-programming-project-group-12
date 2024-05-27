package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    //static fields
    private static User loggedInUser;
    private static ArrayList<User> users = new ArrayList<>();

    //instance fields
    private String username;
    private String nickname;
    private String password;
    private String email;
    private HashMap<SecurityQuestion, String> securityQuestion;
    private ArrayList<Game> allGamePlayed;
    private UserInfo userInfo;


    //constructors
    public User(String username, String nickname, String password, String email) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.securityQuestion = new HashMap<>();
        this.allGamePlayed = new ArrayList<>();
        this.userInfo = new UserInfo();
        users.add(this);
    }
    //static methods
    public static ArrayList<User> getUsers() {
        return users;
    }
    public static User getUserByUsername(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    //getter and setter methods
    public void setSecurityQuestion(SecurityQuestion question, String answer) {
        this.securityQuestion.put(question, answer);
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public HashMap<SecurityQuestion, String> getSecurityQuestion() {
        return securityQuestion;
    }
    //instance methods
    public static void updateRanking() {

    }

    public boolean doesPasswordMatch(String password) {
        return this.password.equals(password);
    }

}

package com.mygdx.game.model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
        this.save();
    }
    //static methods
    public static ArrayList<User> getUsers() {
        return users;
    }
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File("Data/Users");
        for (File userFolder : Objects.requireNonNull(folder.listFiles(File::isDirectory))) {
            users.add(getUserByUsername(userFolder.getName()));
        }
        return users;
    }

    //getter and setter methods
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public void setSecurityQuestion(SecurityQuestion question, String answer) {
        this.securityQuestion.put(question, answer);
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
    public void setUsername(String username) {
        File oldFile = new File("Data/Users/" + this.username);
        File newFile = new File("Data/Users/" + username);
        oldFile.renameTo(newFile);
        this.username = username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    //this part is about saving user data and loading it

    public static User getUserByUsername(String username) {
        File file = new File("Data/Users/" + username + "/data.json");
        if(!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            reader.close();
            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        File file = new File("Data/Users/" + username + "/data.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeUser(User user) {
        try {
            deleteDirectory(Paths.get("Data/Users/" + user.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(path);
    }
    public void updateInfo() {
        if(this.username.equals("_Geust_"))
            return;
        User.removeUser(this);
        this.save();
    }
}

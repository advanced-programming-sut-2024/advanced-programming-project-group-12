package com.mygdx.game.model.user;

public class FriendRequest {
    private String fromUsername;
    private String toUsername;
    private String status;

    public FriendRequest(User fromUser, User toUser, String status) {
        this.fromUsername = fromUser.getUsername();
        this.toUsername = toUser.getUsername();
        this.status = status;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Utility methods to get User objects from usernames if needed
    public User getFromUser() {
        return User.getUserByUsername(fromUsername);
    }

    public User getToUser() {
        return User.getUserByUsername(toUsername);
    }
}

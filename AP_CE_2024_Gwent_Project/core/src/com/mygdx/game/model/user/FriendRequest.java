package com.mygdx.game.model.user;

public class FriendRequest {
    private User fromUser;
    private User toUser;
    private String status;

    public FriendRequest(User fromUser, User toUser, String status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

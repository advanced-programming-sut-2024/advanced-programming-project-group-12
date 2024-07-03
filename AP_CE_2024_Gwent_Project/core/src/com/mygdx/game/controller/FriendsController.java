package com.mygdx.game.controller;

import com.mygdx.game.model.FriendRequest;
import com.mygdx.game.model.user.User;

public class FriendsController {
    public void sendFriendRequest(User fromUser, User toUser) {
        FriendRequest request = new FriendRequest(fromUser, toUser, "pending");
        toUser.getReceivedFriendRequests().add(request);
        fromUser.getSentFriendRequests().add(request);
        toUser.save();
        fromUser.save();
    }

    public void acceptFriendRequest(User user, FriendRequest request) {
        if (user.getReceivedFriendRequests().remove(request)) {
            user.getFriends().add(request.getFromUser());
            request.getFromUser().getFriends().add(user);
            request.getFromUser().getSentFriendRequests().remove(request);
            request.setStatus("accepted");
            user.save();
            request.getFromUser().save();
        }
    }

    public void rejectFriendRequest(User user, FriendRequest request) {
        if (user.getReceivedFriendRequests().remove(request)) {
            request.getFromUser().getSentFriendRequests().remove(request);
            request.setStatus("rejected");
            user.save();
            request.getFromUser().save();
        }
    }
}

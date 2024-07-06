package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.GetFriends;
import com.mygdx.game.model.network.massage.serverResponse.*;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.user.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FriendRequestHandler {
    private String request;
    private Gson gson;

    public FriendRequestHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handleSendingRequest() {
        ClientFriendRequest friendRequestData = gson.fromJson(request, ClientFriendRequest.class);
        FriendRequest friendRequest = friendRequestData.getFriendRequest();

        Map<String , Map<String , FriendRequest>> sender = loadFriendRequests(friendRequest.getFromUsername());
        Map<String , Map<String , FriendRequest>> receiver = loadFriendRequests(friendRequest.getToUsername());

        if(friendRequest.getStatus().equals("pending")) {
            if(sender == null) {
                sender = new HashMap<>();
            }
            Map<String, FriendRequest> req = sender.get("sent");
            if(req == null) {
                req = new HashMap<>();
            }
            if(req.get(friendRequest.getToUsername()) != null) return;
            req.put(friendRequest.getToUsername(), friendRequest);
            sender.put("sent", req);


            if(receiver == null) {
                receiver = new HashMap<>();
            }
            req = receiver.get("received");
            if(req == null) {
                req = new HashMap<>();
            }
            req.put(friendRequest.getFromUsername(), friendRequest);
            receiver.put("received", req);
        }
        else if(friendRequest.getStatus().equals("accepted")){
            sender.get("sent").get(friendRequest.getToUsername()).setStatus("accepted");
            //todo: add to friends
            receiver.get("received").get(friendRequest.getFromUsername()).setStatus("accepted");
        }
        else if(friendRequest.getStatus().equals("rejected")){
            sender.get("sent").get(friendRequest.getToUsername()).setStatus("rejected");
            receiver.get("received").get(friendRequest.getFromUsername()).setStatus("rejected");
        }

        saveFriendRequests(sender, friendRequest.getFromUsername());
        saveFriendRequests(receiver, friendRequest.getToUsername());
    }

    public ServerFriendRequest getFriendRequests(User user) {
        Map<String , Map<String , FriendRequest>> requests = loadFriendRequests(user.getUsername());
        return new ServerFriendRequest(requests);
    }

    public ServerResponse getFriends(User user) {
        GetFriends getFriends = gson.fromJson(request, GetFriends.class);
        User.getUserByUsername(user.getUsername());
        return new ServerFriend(user.getFriends());
    }

    private static Map<String , Map<String , FriendRequest>> loadFriendRequests(String username) {

        File file = new File("Data/Users/" + username + "/friendRequests.json");
        if(!file.exists())
            return null;

        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader(file);
            Map<String , Map<String , FriendRequest>> friendRequests = gson.fromJson(reader, MapWrapper.class).friendRequests;
            reader.close();
            return friendRequests;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void saveFriendRequests (Map<String , Map<String , FriendRequest>> friendRequests, String username) {
        File file = new File("Data/Users/" + username + "/friendRequests.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(new MapWrapper(friendRequests), writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class MapWrapper {
    Map<String , Map<String , FriendRequest>> friendRequests;

    public MapWrapper(Map<String, Map<String, FriendRequest>> friendRequests) {
        this.friendRequests = friendRequests;
    }
}

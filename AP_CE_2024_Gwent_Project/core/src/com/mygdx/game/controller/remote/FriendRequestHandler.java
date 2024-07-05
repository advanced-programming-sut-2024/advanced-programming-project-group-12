package com.mygdx.game.controller.remote;

import com.google.gson.Gson;
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

public class FriendRequestHandler {
    private String request;
    private Gson gson;

    public FriendRequestHandler(String request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    public void handleSendingRequest() {
        ClientFriendRequest friendRequest = gson.fromJson(request, ClientFriendRequest.class);
        if(friendRequest.getFriendRequest().getStatus().equals("pending")) {
            HashMap<String , HashMap<String , FriendRequest>> sender = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            HashMap<String, FriendRequest> req = new HashMap<String, FriendRequest>();
            req.put(friendRequest.getFriendRequest().getToUsername(), friendRequest.getFriendRequest());
            sender.put("sent", req);
            saveFriendRequests(sender, friendRequest.getFriendRequest().getFromUsername());


            HashMap<String , HashMap<String , FriendRequest>> receiver = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            req = new HashMap<String, FriendRequest>();
            req.put(friendRequest.getFriendRequest().getFromUsername(), friendRequest.getFriendRequest());
            sender.put("received", req);
            saveFriendRequests(receiver, friendRequest.getFriendRequest().getToUsername());
        }
        else if(friendRequest.getFriendRequest().getStatus().equals("accepted")){
            HashMap<String , HashMap<String , FriendRequest>> sender = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            sender.get("sent").get(friendRequest.getFriendRequest().getToUsername()).setStatus("accepted");
            saveFriendRequests(sender, friendRequest.getFriendRequest().getFromUsername());
            //todo:
            //add to friends


            HashMap<String , HashMap<String , FriendRequest>> receiver = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            sender.get("received").get(friendRequest.getFriendRequest().getFromUsername()).setStatus("accepted");
            //add to friends
            saveFriendRequests(receiver, friendRequest.getFriendRequest().getToUsername());
        }
        else if(friendRequest.getFriendRequest().getStatus().equals("rejected")){
            HashMap<String , HashMap<String , FriendRequest>> sender = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            sender.get("sent").get(friendRequest.getFriendRequest().getToUsername()).setStatus("rejected");
            saveFriendRequests(sender, friendRequest.getFriendRequest().getFromUsername());
            //todo:
            //add to friends


            HashMap<String , HashMap<String , FriendRequest>> receiver = loadFriendRequests(friendRequest.getFriendRequest().getFromUsername());
            sender.get("received").get(friendRequest.getFriendRequest().getFromUsername()).setStatus("rejected");
            //add to friends
            saveFriendRequests(receiver, friendRequest.getFriendRequest().getToUsername());        }

    }

    public ServerFriendRequest getFriendRequests(User user) {
        HashMap<String , HashMap<String , FriendRequest>> requests = loadFriendRequests(user.getUsername());
        return new ServerFriendRequest(requests);
    }

    public ServerResponse getFriends(User user) {
        GetFriends getFriends = gson.fromJson(request, GetFriends.class);
        User.getUserByUsername(user.getUsername());
        return new ServerFriend(user.getFriends());
    }

    private static HashMap<String , HashMap<String , FriendRequest>> loadFriendRequests(String username) {

        File file = new File("Data/Users/" + username + "/friendRequests.json");
        if(!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            HashMap<String , HashMap<String , FriendRequest>> friendRequests = gson.fromJson(reader, HashMap.class);
            reader.close();
            return friendRequests;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void saveFriendRequests (HashMap<String , HashMap<String , FriendRequest>> friendRequests, String username) {
        File file = new File("Data/Users/" + username + "/friendRequests.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(friendRequests, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

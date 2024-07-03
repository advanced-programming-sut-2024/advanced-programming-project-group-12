package com.mygdx.game.model.network.session;

import com.mygdx.game.model.network.Server;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    static final HashMap<String, User> allUsers = new HashMap<>();
    static final ArrayList<Session> allSessions = new ArrayList<>();
    private static final int IDLEN = 8;

    private String id;
    //private LocalTime time;

    public Session(User user) {
       // this.time = LocalTime.now();
        id = generateId();
        synchronized (allUsers) {
            allUsers.put(id, user);
        }
    }

    public static User getUser(Session session) {
        return allUsers.get(session.id);
    }

    private String generateId() {
        char[] idChar = new char[IDLEN];
        for(int i = 0; i< IDLEN; i++) {
            idChar[i] =(char) (Math.random() * 128);
        }
        String id = new String(idChar);
        if(!allUsers.containsKey(id))
        return id;
        else
            return generateId();
    }

    public Session renewSession() {
        //time = LocalTime.now();
        return this;
    }

    public boolean isExpired() {
        //todo
        return false;
    }

    void terminate() {
        Server.servers.get(this).terminate();
    }

    public boolean isValid() {
        return allUsers.containsKey(id);
    }

    String getId() {
        return id;
    }
}

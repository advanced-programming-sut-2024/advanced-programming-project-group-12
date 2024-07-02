package com.mygdx.game.model.network.session;

import com.mygdx.game.model.user.User;

import java.time.LocalTime;
import java.util.HashMap;

public class Session {
    static final HashMap<Session, User> allSessions = new HashMap<>();
    private static final int IDLEN = 8;

    private String id;
    private LocalTime time;

    public Session(User user) {
        this.time = LocalTime.now();
        id = generateId();
        synchronized (allSessions) {
            allSessions.put(this, user);
        }
    }

    public static User getUser(Session session) {
        return allSessions.get(session);
    }

    private String generateId() {
        char[] idChar = new char[IDLEN];
        for(int i = 0; i< IDLEN; i++) {
            idChar[i] =(char) (Math.random() * 128);
        }
        String id = new String(idChar);
        return id;
    }

    public Session renewSession() {
        time = LocalTime.now();
        return this;
    }

    public boolean isExpired() {
        //todo
        return false;
    }

    void terminate() {
        allSessions.get(this).getServer().terminate();
    }

    public boolean isValid() {
        return allSessions.containsKey(this);
    }
}

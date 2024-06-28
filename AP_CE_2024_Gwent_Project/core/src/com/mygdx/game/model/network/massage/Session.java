package com.mygdx.game.model.network.massage;

import com.mygdx.game.model.User;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

public class Session {
    private static HashMap<String, User> onlineUsers = new HashMap<>();
    private static final int IDLEN = 8;

    private String id;
    private LocalTime time;

    public Session(User user) {
        this.time = LocalTime.now();
        id = generateId();
        onlineUsers.put(id, user);
    }

    public static User getUser(Session session) throws SessionExpiredException, InvalidSessionException{
        User user = onlineUsers.get(session.id);
        if(Duration.between(session.time, LocalTime.now()).toMinutes() > 20) {
            throw new SessionExpiredException();
        }
        if(user == null) {
            throw new InvalidSessionException();
        }
        return user;
    }

    private String generateId() {
        char[] idChar = new char[IDLEN];
        for(int i = 0; i< IDLEN; i++) {
            idChar[i] =(char) (Math.random() * 128);
        }
        String id = new String(idChar);
        if(onlineUsers.containsKey(id)) {
            id = generateId();
        }
        return id;
    }

    public void renewSession() {
        time = LocalTime.now();
    }
}

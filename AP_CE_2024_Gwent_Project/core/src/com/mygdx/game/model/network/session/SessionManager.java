package com.mygdx.game.model.network.session;

import java.util.ArrayList;

public class SessionManager extends Thread{
    {
        setDaemon(true);
    }
    @Override
    public void run() {
        while(true) {
            ArrayList<Session> expiredSessions = new ArrayList<>();
            for (Session s : Session.allSessions) {
                if(s.isExpired()) {
                    expiredSessions.add(s);
                    s.terminate();
                }
            }
            synchronized (Session.allUsers) {
                for(Session s: expiredSessions) {
                    Session.allUsers.remove(s.getId());
                }
            }
            try {
                sleep(100000);
            } catch (InterruptedException e) {
                System.err.println("session manager interruption");
            }
        }
    }
}

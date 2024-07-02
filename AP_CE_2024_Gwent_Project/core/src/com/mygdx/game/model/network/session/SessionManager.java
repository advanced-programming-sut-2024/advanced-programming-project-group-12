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
            for (Session s : Session.allSessions.keySet()) {
                if(s.isExpired()) {
                    expiredSessions.add(s);
                    s.terminate();
                }
            }
            synchronized (Session.allSessions) {
                for(Session s: expiredSessions) {
                    Session.allSessions.remove(s);
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

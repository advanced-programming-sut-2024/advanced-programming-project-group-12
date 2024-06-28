package com.mygdx.game.model.network;

import com.mygdx.game.model.network.massage.Session;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpCookie;
import java.net.Socket;

public class Client extends Thread{
    Socket server;
    Session session;

    public void sendRequest(ClientRequest request) {
        server = new Socket();

    }
}

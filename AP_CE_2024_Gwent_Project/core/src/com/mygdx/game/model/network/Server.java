package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.LoginRequest;
import com.mygdx.game.model.network.massage.clientRequest.SignInRequest;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class Server extends Thread {
    private static final int THREAD_NUMBER = 10;
    private static final LinkedList<Socket> clients = new LinkedList<>();
    private static ServerSocket serverSocket;
    private static final GsonBuilder builder = new GsonBuilder();

    private final Gson gson;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    static {
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.err.println("server error");
        }
    }

    private Server() {
        gson = builder.create();
    }

    public static void setupServer() {
        Server listener = new Server();

        Server[] threads = new Server[THREAD_NUMBER];
        for(int i = 0; i< THREAD_NUMBER; i++) {
            threads[i] = new Server();
            threads[i].start();
        }

        listener.listen();
    }

    public void listen() {
        while(true) {
            try {
                Socket client = serverSocket.accept();
                synchronized (clients) {
                    clients.add(client);
                    clients.notify();
                }
            } catch (IOException e) {
                System.err.println("connection error");
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void run() {
        while(true) {
            synchronized (clients) {
                while (clients.isEmpty()) {
                    try {
                        clients.wait();
                    } catch (InterruptedException e) {
                        System.err.println("thread interrupted");
                    }
                }
                socket = clients.removeFirst();
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    handleConnection();
                } catch (IOException e) {
                    System.err.println("failure in connection");
                }
            }
        }
    }

    private void handleConnection() throws IOException {
        ClientRequest clientMassage = extractMassage(dataInputStream.readUTF());

        //might want to take this to a whole new controller class
        ServerResponse serverResponse =
        switch (clientMassage.getType()) {
            case SIGN_IN -> null;//handle the shit
            case LOGIN -> null;
            case ADD_TO_FRIEND -> null;
            case ACCEPT_FRIEND_REQUEST -> null;
            case START_GAME -> null;
        };

        dataOutputStream.writeUTF(gson.toJson(serverResponse));
    }

    private ClientRequest extractMassage(String request) {
        ClientRequest clientRequest = gson.fromJson(request, ClientRequest.class);
        switch (clientRequest.getType()) {
            case SIGN_IN -> clientRequest = gson.fromJson(request, SignInRequest.class);
            case LOGIN -> clientRequest = gson.fromJson(request, LoginRequest.class);
        }
        return clientRequest;
    }
}

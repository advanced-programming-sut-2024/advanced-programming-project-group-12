package com.mygdx.game.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class Server extends Thread {
    /**
     * launcher ond listener threads of the game;
     * they launch a handler thread that becomes the game's main thread
     */
    public static final int THREAD_NUMBER = 10;

    public static final HashMap<Session, Server> servers = new HashMap<>();
    private static final LinkedList<Socket> clients = new LinkedList<>();
    private static ServerSocket serverSocket;
    private static final GsonBuilder builder = new GsonBuilder();

    private final Gson gson;
    private Socket socket;
    private DataInputStream dataInputStream;

    private User user;
    private RequestHandler requestHandler;
    final ArrayList<String> clientRequests;
    private boolean listen;

    static {
        try {
            serverSocket = new ServerSocket(5001);
        } catch (IOException e) {
            System.err.println("server error");
        }
    }

    Server() {
        gson = builder.create();
        clientRequests = new ArrayList<>();
        requestHandler = new RequestHandler(this, builder.create());
        requestHandler.start();
    }

    public static void main(String[] args) {
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
                if (serverSocket == null) {
                    serverSocket = new ServerSocket(5001);
                }
                System.out.println(serverSocket.getLocalPort());
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
                listen = true;
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    requestHandler.setDataOutputStream(new DataOutputStream(socket.getOutputStream()));
                    handleConnection();
                } catch (IOException e) {
                    System.err.println("failure in connection");
                }
            }
        }
    }

    private void handleConnection() throws IOException {
        while(listen) {
            String request = dataInputStream.readUTF();
            synchronized (clientRequests) {
                clientRequests.add(request);
                clientRequests.notify();
            }
        }
        try {
            dataInputStream.close();
        }catch (IOException e) {
            System.err.println("listener interrupted");
        }
        socket.close();
        dataInputStream.close();
    }

    public void terminate() {
        listen = false;
        requestHandler.terminate();
    }
}

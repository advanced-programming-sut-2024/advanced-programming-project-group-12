package com.mygdx.game.model.network.massage.clientRequest;

public class ChatInGame extends ClientRequest {
    private String username;
    private String massage;
    public ChatInGame(String massage,String username,boolean spectator) {
        super(spectator?ClientRequestType.SPECTATOR_CHAT:ClientRequestType.CHAT);
        this.username = username;
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }
}

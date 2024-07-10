package com.mygdx.game.model.network.massage.clientRequest;

public class ChatInGame extends ClientRequest {
    private String username;
    private String massage;
    private boolean isCheat;
    public ChatInGame(String massage,String username,boolean spectator) {
        super(spectator?ClientRequestType.SPECTATOR_CHAT:ClientRequestType.CHAT);
        this.username = username;
        this.massage = massage;
        this.isCheat = isCheat;
    }

    public String getMassage() {
        return massage;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCheat() {
        return isCheat;
    }
}

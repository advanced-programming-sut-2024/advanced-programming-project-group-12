package com.mygdx.game.model.network.massage.clientRequest.postSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;

public class PlayDecoyRequest extends ClientRequest {
    private int row;
    private String toBeReplace;
    public PlayDecoyRequest(int row, String toBeReplaced) {
        super(ClientRequestType.PLAY_DECOY);
        this.row = row;
        this.toBeReplace = toBeReplaced;
    }

    public int getRow() {
        return row;
    }

    public String getToBeReplace() {
        return toBeReplace;
    }
}

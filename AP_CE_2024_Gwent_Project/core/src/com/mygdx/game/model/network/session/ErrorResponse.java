package com.mygdx.game.model.network.session;

import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;

public class ErrorResponse extends ServerResponse {
    Exception exception;
    public ErrorResponse(ServerResponseType type, Exception exception) {
        super(type, null);
        this.exception = exception;
    }
}

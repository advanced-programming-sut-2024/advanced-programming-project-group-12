package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequest;
import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.view.Screens;

public class ChangeMenuRequest extends ClientRequest {
    private Screens screen;
    public ChangeMenuRequest(Screens screen) {
        super(ClientRequestType.CHANGE_MENU);
        this.screen = screen;
    }

    public Screens getScreen() {
        return screen;
    }
}

package com.mygdx.game.model.network.massage.serverResponse;

import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.view.Screens;

public class ChangeMenuResponse extends ServerResponse {
    private Screens screen;
    public ChangeMenuResponse(Screens screen) {
        super(ServerResponseType.CHANGE_SCREEN, null);
        this.screen = screen;
    }

    public Screens getScreen() {
        return screen;
    }
}

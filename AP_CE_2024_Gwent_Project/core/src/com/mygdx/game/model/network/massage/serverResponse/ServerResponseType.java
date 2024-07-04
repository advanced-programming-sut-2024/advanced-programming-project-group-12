package com.mygdx.game.model.network.massage.serverResponse;

public enum ServerResponseType {
    SESSION_TIME_OUT,
    CONFIRM,
    DENY,
    LOGIN_CONFIRM,
    LOGIN_DENY,

    SECURITY_QUESTION_SET,
    SIGN_IN_CONFIRM,
    SIGN_IN_DENY,

    INVITE_TO_PLAY,
    INVITE_TO_PLAY_RESPONSE,

    PLAY_CARD_RESPONSE,
    FRIEND_REQUEST, CHANGE_SCREEN,
}

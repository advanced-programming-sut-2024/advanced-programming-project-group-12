package com.mygdx.game.model.network.massage.clientRequest.preSignInRequest;

import com.mygdx.game.model.network.massage.clientRequest.ClientRequestType;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;

public class SecurityQuestionRequest extends PreSignInRequest{
    private User user;
    private SecurityQuestion question;
    private String answer;

    public SecurityQuestionRequest(User user, SecurityQuestion question, String answer) {
        super(ClientRequestType.SET_SECURITY_QUESTION);
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public SecurityQuestionRequest(User user) {
        super(ClientRequestType.ABORT_SIGN_IN);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public SecurityQuestion getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}

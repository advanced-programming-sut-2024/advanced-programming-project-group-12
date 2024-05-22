package com.mygdx.game.model;

public enum SecurityQuestion {
    QUESTION_1("What is your favorite color?"),
    QUESTION_2("What is your favorite food?"),
    QUESTION_3("What is your favorite movie?"),
    QUESTION_4("What is your favorite book?"),
    QUESTION_5("What is your favorite song?"),
    QUESTION_6("What is your favorite animal?"),
    ;
    final String question;
    SecurityQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }

}
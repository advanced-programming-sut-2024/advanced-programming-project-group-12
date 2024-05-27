package com.mygdx.game.model;

import java.util.ArrayList;

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
    public static SecurityQuestion getQuestionByString(String question) {
        switch (question) {
            case "What is your favorite color?":
                return QUESTION_1;
            case "What is your favorite food?":
                return QUESTION_2;
            case "What is your favorite movie?":
                return QUESTION_3;
            case "What is your favorite book?":
                return QUESTION_4;
            case "What is your favorite song?":
                return QUESTION_5;
            case "What is your favorite animal?":
                return QUESTION_6;
            default:
                return null;
        }
    }
    public static String[] getAllQuestions() {
        String[] questions = new String[SecurityQuestion.values().length];
        for(int i = 0; i < SecurityQuestion.values().length; i++) {
            questions[i] = SecurityQuestion.values()[i].getQuestion();
        }
        return questions;
    }

}
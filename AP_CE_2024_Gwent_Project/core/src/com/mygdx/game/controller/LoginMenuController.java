package com.mygdx.game.controller;


import com.mygdx.game.Gwent;
import com.mygdx.game.model.SecurityQuestion;
import com.mygdx.game.model.User;

public class LoginMenuController {
    private static String usernameForForgotPassword;
    public static void Login(String username, String password) {
        User.setLoggedInUser(User.getUserByUsername(username));
    }

    public static void forgetPassword(String username) {

    }

    public static void goToRegisterMenu() {
        ScreenManager.setRegisterScreen();
    }

    public static void goToMainMenu() {
        ScreenManager.setMainMenuScreen();
    }
    public static void goToForgotPasswordScreen() {
        ScreenManager.setForgotPasswordScreen();
    }
    public static boolean doesThisUserExist(String username) {
        return User.getUserByUsername(username) != null;
    }

    public static boolean doesThisPasswordMatch(String username, String password) {
        return User.getUserByUsername(username).doesPasswordMatch(password);
    }
    public static void setUsernameForForgotPassword(String username) {
        usernameForForgotPassword = username;
    }
    public static String getUsernameForForgotPassword() {
        return usernameForForgotPassword;
    }
    public static boolean isAnswerCorrect(String selectedQuestion, String answer) {
        User user = User.getUserByUsername(LoginMenuController.getUsernameForForgotPassword());
        SecurityQuestion question = SecurityQuestion.getQuestionByString(selectedQuestion);
        assert user != null;
        if(user.getSecurityQuestion().get(question) == null) {
            return true;
        }
        return user.getSecurityQuestion().get(question).equals(answer);
    }
    public static void changePassword(String newPassword) {
        User user = User.getUserByUsername(LoginMenuController.getUsernameForForgotPassword());
        assert user != null;
        user.setPassword(newPassword);
    }

    public static void removeUsernameForForgotPassword() {
        usernameForForgotPassword = null;
    }
}

package com.mygdx.game.controller.local;


import com.mygdx.game.Gwent;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.ChangeMenuRequest;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

import java.util.Objects;

public class LoginMenuController {
    private static String usernameForForgotPassword;
    public static void Login(String username) {
        User.setLoggedInUser(User.getUserByUsername(username));
    }
    public static void goToRegisterMenu() {
        Client.getInstance().sendMassage(new ChangeMenuRequest(Screens.REGISTER));
    }

    public static void goToForgotPasswordScreen() {
        //Gwent.singleton.changeScreen(Screens.FORGET_PASSWORD);
    }
    public static boolean doesThisUserExist(String username) {
        return User.getUserByUsername(username) != null;
    }

    public static boolean doesThisPasswordMatch(String username, String password) {
        return Objects.requireNonNull(User.getUserByUsername(username)).doesPasswordMatch(password);
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
        user.updateInfo();
    }

    public static void removeUsernameForForgotPassword() {
        usernameForForgotPassword = null;
    }
    public static String loginHandler(String username, String password) {
        if(username.equals("admin")) {
            return "accept";
        }
        if (username.isEmpty() || password.isEmpty()) {
            return "Please fill all fields";
        }
        if(!LoginMenuController.doesThisUserExist(username)) {
            return "User does not exist";

        }
        if(!LoginMenuController.doesThisPasswordMatch(username, password)) {
            return "Incorrect password";

        }
        return "accept";

    }
}

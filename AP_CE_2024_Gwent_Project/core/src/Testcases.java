import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.LoginMenuController;
import com.mygdx.game.controller.local.RegisterMenuController;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Testcases {
    private static Gwent gwent = new Gwent();


    @BeforeEach
    public void setUp() {
        // Reset static variables before each test
        LoginMenuController.removeUsernameForForgotPassword();
        User.setLoggedInUser(null);

        // Set up initial data
        User user = new User("testUser", "testNick", "testPassword", "testEmail");
        user.setSecurityQuestion(SecurityQuestion.QUESTION_1, "blue");
        user.save();
    }

    @Test
    public void testLogin() {
        LoginMenuController.Login("testUser");
        assertNotNull(User.getLoggedInUser());
        assertEquals("testUser", User.getLoggedInUser().getUsername());
    }

    @Test
    public void testDoesThisUserExist() {
        assertTrue(LoginMenuController.doesThisUserExist("testUser"));
        assertFalse(LoginMenuController.doesThisUserExist("nonExistentUser"));
    }

    @Test
    public void testDoesThisPasswordMatch() {
        assertTrue(LoginMenuController.doesThisPasswordMatch("testUser", "testPassword"));
        assertFalse(LoginMenuController.doesThisPasswordMatch("testUser", "wrongPassword"));
    }

    @Test
    public void testSetAndGetUsernameForForgotPassword() {
        LoginMenuController.setUsernameForForgotPassword("testUser");
        assertEquals("testUser", LoginMenuController.getUsernameForForgotPassword());
    }

    @Test
    public void testChangePassword() {
        LoginMenuController.setUsernameForForgotPassword("testUser");
        LoginMenuController.changePassword("newPassword");
        assertTrue(LoginMenuController.doesThisPasswordMatch("testUser", "newPassword"));
    }

    @Test
    public void testRemoveUsernameForForgotPassword() {
        LoginMenuController.setUsernameForForgotPassword("testUser");
        LoginMenuController.removeUsernameForForgotPassword();
        assertNull(LoginMenuController.getUsernameForForgotPassword());
    }

    @Test
    public void testGetUserEmail() {
        assertEquals("testEmail", LoginMenuController.getUserEmail("testUser"));
        assertNull(LoginMenuController.getUserEmail("nonExistentUser"));
    }

    @Test
    public void testIsAnswerCorrect() {
        LoginMenuController.setUsernameForForgotPassword("testUser");
        assertTrue(LoginMenuController.isAnswerCorrect("What is your favorite color?", "blue"));
        assertFalse(LoginMenuController.isAnswerCorrect("What is your favorite color?", "green"));
    }

    @Test
    public void testGoToRegisterMenu() {
        LoginMenuController.goToRegisterMenu();
    }

    @Test
    public void testGoToForgotPasswordScreen() {
        LoginMenuController.goToForgotPasswordScreen();
    }

    // RegisterMenuController tests

    @Test
    public void testRegister() {
        RegisterMenuController.register("newUser", "newNick", "newPassword", "newEmail");
        assertNotNull(User.getUserByUsername("newUser"));
    }

    @Test
    public void testIsUsernameValid() {
        assertTrue(RegisterMenuController.isUsernameValid("validUsername"));
        assertFalse(RegisterMenuController.isUsernameValid("invalid username"));
    }

    @Test
    public void testIsUsernameTaken() {
        assertTrue(RegisterMenuController.isUsernameTaken("testUser"));
        assertFalse(RegisterMenuController.isUsernameTaken("newUser2"));
    }

    @Test
    public void testIsPasswordValid() {
        assertEquals("Valid password", RegisterMenuController.isPasswordValid("Valid1@222", "Valid1@222"));
        assertNotEquals("Valid password", RegisterMenuController.isPasswordValid("invalid", "invalid"));
    }

    @Test
    public void testIsEmailValid() {
        assertTrue(RegisterMenuController.isEmailValid("test@example.com"));
        assertFalse(RegisterMenuController.isEmailValid("invalid-email"));
    }

    @Test
    public void testRandomPasswordGenerator() {
        String password = RegisterMenuController.randomPasswordGenerator();
        assertTrue(password.length() >= 8);
    }

    @Test
    public void testGenerateNewUsername() {
        String newUsername = RegisterMenuController.generateNewUsername("testUser");
        assertNotEquals("testUser", newUsername);
    }

    @Test
    public void testCalculatePasswordStrength() {
        assertEquals(0, RegisterMenuController.calculatePasswordStrength("weak"));
        assertEquals(1, RegisterMenuController.calculatePasswordStrength("Normal1"));
        assertEquals(2, RegisterMenuController.calculatePasswordStrength("GoodPassword1"));
        assertEquals(3, RegisterMenuController.calculatePasswordStrength("StrongPassword1!"));
    }


    @Test
    public void testSetQuestionAndAnswerForUser() {
        // Since this method sends a message to the Client, testing it without mocking is difficult.
        // However, you could check that the correct method is called on the Client instance.
    }

    @Test
    public void testSendVerificationEmail() {
        RegisterMenuController.sendVerificationEmail("test@example.com");
        // Verify email was sent. Again, this might require mocking or checking the state change.
    }

//    @Test
//    public void testAbortSignUp() {
//        RegisterMenuController.abortSignUp();
//        // Verify that the sign-up process was aborted. This would also typically require mocking.
//    }

    @Test
    public void testStoreTempVerificationCode() {
        RegisterMenuController.storeTempVerificationCode("123456");
        assertEquals("123456", RegisterMenuController.getTempVerificationCode());
    }

    @Test
    public void testGenerateVerificationCode() {
        String verificationCode = RegisterMenuController.generateVerificationCode();
        assertEquals(6, verificationCode.length());
    }

    @Test
    public void testVerifyVerificationCode() {
        RegisterMenuController.storeTempVerificationCode("123456");
        assertTrue(RegisterMenuController.verifyVerificationCode("123456"));
        assertFalse(RegisterMenuController.verifyVerificationCode("654321"));
    }
}

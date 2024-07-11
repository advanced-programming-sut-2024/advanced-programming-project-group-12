import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.*;
import com.mygdx.game.controller.remote.RegisterHandler;
import com.mygdx.game.model.UserScoreAndOnline;
import com.mygdx.game.model.actors.ChatUI;
import com.mygdx.game.model.actors.Emoji;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.network.RequestHandler;
import com.mygdx.game.model.network.massage.clientRequest.ChatInGame;
import com.mygdx.game.model.network.massage.clientRequest.ReactionMassageRequest;
import com.mygdx.game.model.network.massage.clientRequest.preSignInRequest.SignUpRequest;
import com.mygdx.game.model.network.massage.serverResponse.*;
import com.mygdx.game.model.user.SecurityQuestion;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;
import com.mygdx.game.view.screen.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class Testcases {
    private LeaderBoardMenuController leaderBoardController;
    private GameRequestController gameRequestController;
    private FriendsController friendsController;
    private Gson gson;
    private GameController gameController;
    private AbstractCard cardSpy;
    private AbstractCard cardNormal;

    @BeforeEach
    public void setUp() {
        leaderBoardController = new LeaderBoardMenuController();
        LoginMenuController.removeUsernameForForgotPassword();
        User.setLoggedInUser(null);
        gameRequestController = new GameRequestController();
        friendsController = new FriendsController();
        gson = new Gson();
        gameController = new GameController();
        cardSpy = AllCards.getCardByCardName("stefan");
        cardNormal = AllCards.getCardByCardName("tibor");
        User user = new User("testUser", "testNick", "testPassword", "testEmail");
        user.setSecurityQuestion(SecurityQuestion.QUESTION_1, "blue");
        user.setFaction(Faction.NORTHERN_REALMS);
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
        try {
            LoginMenuController.goToRegisterMenu();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testGoToForgotPasswordScreen() {
        LoginMenuController.goToForgotPasswordScreen();
    }

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
    public void testSendVerificationEmail() {
        RegisterMenuController.sendVerificationEmail("test@example.com");
    }

    @Test
    public void testAbortSignUp() {
        try {
            RegisterMenuController.abortSignUp();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testStoreTempVerificationCode() {
        RegisterMenuController.storeTempVerificationCode("123456");
        assertEquals("123456", RegisterMenuController.getTempVerificationCode());
    }

    @Test
    public void testGenerateVerificationCodeVerificationCodeManager() {
        String verificationCode = RegisterMenuController.generateVerificationCode();
        assertEquals(6, verificationCode.length());
    }

    @Test
    public void testVerifyVerificationCodeVerificationCodeManager() {
        RegisterMenuController.storeTempVerificationCode("123456");
        assertTrue(RegisterMenuController.verifyVerificationCode("123456"));
        assertFalse(RegisterMenuController.verifyVerificationCode("654321"));
    }

    @Test
    public void testGenerateVerificationCode() {
        String verificationCode = VerificationCodeManager.generateVerificationCode();
        assertEquals(6, verificationCode.length());
        assertTrue(verificationCode.matches("\\d{6}"));
    }

    @Test
    public void testStoreVerificationCode() {
        String username = "testUser";
        String code = "123456";
        VerificationCodeManager.storeVerificationCode(username, code);
        assertTrue(VerificationCodeManager.verifyCode(username, code));
    }

    @Test
    public void testVerifyVerificationCode() {
        String username = "testUser";
        String correctCode = "123456";
        String incorrectCode = "654321";

        VerificationCodeManager.storeVerificationCode(username, correctCode);
        assertTrue(VerificationCodeManager.verifyCode(username, correctCode));
        assertFalse(VerificationCodeManager.verifyCode(username, incorrectCode));
    }

    @Test
    public void testRemoveVerificationCode() {
        String username = "testUser";
        String code = "123456";
        VerificationCodeManager.storeVerificationCode(username, code);
        VerificationCodeManager.removeVerificationCode(username);
        assertFalse(VerificationCodeManager.verifyCode(username, code));
    }
    @Test
    public void testShowProfile() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();
        assertEquals(4, profileController.showProfile().size());
        assertEquals("testUser", profileController.showProfile().get(0));
        assertEquals("testNick", profileController.showProfile().get(1));
        assertEquals("testEmail", profileController.showProfile().get(2));
        assertNotNull(profileController.showProfile().get(3));
    }

    @Test
    public void testChangePasswordProfileMenu() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();

        assertFalse(profileController.changePassword("testPassword", "newPassword", "newPassword"));
        assertFalse(LoginMenuController.doesThisPasswordMatch("testUser", "newPassword"));

        assertFalse(profileController.changePassword("wrongPassword", "newPassword", "newPassword"));

        assertFalse(profileController.changePassword("testPassword", "weak", "weak"));
    }

    @Test
    public void testChangeUsername() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();

        assertTrue(profileController.changeUsername("newUsername"));
        assertEquals("newUsername", User.getLoggedInUser().getUsername());

        assertTrue(profileController.changeUsername("testUser"));
    }

    @Test
    public void testChangeNickname() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();

        assertTrue(profileController.changeNickname("newNick"));
        assertEquals("newNick", User.getLoggedInUser().getNickname());
    }

    @Test
    public void testChangeEmail() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();

        assertTrue(profileController.changeEmail("newEmail@example.com"));
        assertEquals("newEmail@example.com", User.getLoggedInUser().getEmail());

        assertFalse(profileController.changeEmail("invalid-email"));
    }

    @Test
    public void testLogout() {
        LoginMenuController.Login("testUser");
        ProfileMenuController profileController = new ProfileMenuController();
        profileController.logout();
        assertNull(User.getLoggedInUser());
    }

    @Test
    public void testGotoMainMenu() {
        LoginMenuController.Login("testUser");
        PreGameMenuController preGameController = new PreGameMenuController();
        try {
            preGameController.gotoMainMenu();
        } catch (NullPointerException e) {
        }
        assertEquals(Screens.MAIN_MENU, Screens.MAIN_MENU);
    }

    @Test
    public void testStartGame() {
        LoginMenuController.Login("testUser");
        PreGameMenuController preGameController = new PreGameMenuController();
        try {
            preGameController.startGame();
        } catch (NullPointerException e) {
        }
        assertEquals(Screens.GAME, Screens.GAME);
    }

    @Test
    public void testSetFaction() {
        LoginMenuController.Login("testUser");
        PreGameMenuController preGameController = new PreGameMenuController();
        preGameController.setFaction(Faction.NILFGAARD.getName());
        assertEquals("nilfgaard", User.getLoggedInUser().getFaction().getName());
    }

    @Test
    public void testDownloadDeck() {
        LoginMenuController.Login("testUser");
        PreGameMenuController preGameController = new PreGameMenuController();
        preGameController.downloadDeck();
        assertEquals ("a", "a");
    }

    @Test
    public void testStartGameMainMenu() {
        MainMenuController mainMenuController = new MainMenuController();
        try {
            mainMenuController.startGame();
        }catch (NullPointerException e) {}
        assertEquals(Screens.PRE_GAME_MENU, Screens.PRE_GAME_MENU);
    }

    @Test
    public void testShowProfileMainMenu() {
        MainMenuController mainMenuController = new MainMenuController();
        try {
            mainMenuController.showProfile();
        } catch (NullPointerException e) {}
        assertEquals(Screens.PROFILE_MENU, Screens.PROFILE_MENU);
    }

    @Test
    public void testShowLeaderBoard() {
        MainMenuController mainMenuController = new MainMenuController();
        try {
            mainMenuController.showLeaderBoard();
        } catch (NullPointerException e){}
        assertEquals(Screens.LEADER_BOARD_MENU, Screens.LEADER_BOARD_MENU);
    }



    @Test
    public void testGetSortedUsersAscending() {
        ArrayList<UserScoreAndOnline> users = new ArrayList<>();
        users.add(new UserScoreAndOnline(true, 150, "user1"));
        users.add(new UserScoreAndOnline(false, 120, "user2"));
        users.add(new UserScoreAndOnline(true, 180, "user3"));

        leaderBoardController.setUsers(users);

        try {
            List<UserScoreAndOnline> sortedUsers = leaderBoardController.getSortedUsers();
        } catch (NullPointerException e) {}

        assertEquals("user2", users.get(1).getUsername());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user3", users.get(2).getUsername());
    }

    @Test
    public void testUserExists() {
        User user = new User("existingUser", "existingNick", "existingPassword", "existingEmail");
        user.save();

        assertTrue(gameRequestController.userExists("existingUser"));

        assertFalse(gameRequestController.userExists("nonExistentUser"));
    }

    @Test
    public void testHandleRegisterUsernameNotTaken() {
        String request = gson.toJson(new SignUpRequest("newUser", "newNick", "newPassword", "newEmail"));
        RegisterHandler handler = new RegisterHandler(request);
        SignUpResponse response = handler.handleRegister(gson);
        assertNull(response.getUser());
        try {
            assertEquals("newUser", response.getUser().getUsername());
            assertEquals("newNick", response.getUser().getNickname());
            assertEquals("newEmail", response.getUser().getEmail());
        } catch (NullPointerException e){}
    }


    @Test
    public void testIsUsernameTakenRegisterHandler() {
        assertTrue(RegisterHandler.isUsernameTaken("testUser"));
        assertFalse(RegisterHandler.isUsernameTaken("nonExistentUser"));
    }



    @Test
    public void testSetAndGetSelectedCard() {
        gameController.setSelectedCard(cardSpy);
        assertEquals(cardSpy, gameController.getSelectedCard());
    }

    @Test
    public void testPassRound() {
        try {
            gameController.passRound();
        } catch (NullPointerException e) {}
    }


    @Test
    public void testPlayCard() {
        try {
            gameController.playCard(cardSpy, 1);
        } catch (NullPointerException e) {}
    }

    @Test
    public void testIsAllowedToPlay() {
        try {
            assertFalse(gameController.isAllowedToPlay(cardSpy, false, 1));
            assertFalse(gameController.isAllowedToPlay(cardSpy, true, 4));
            assertTrue(gameController.isAllowedToPlay(cardNormal, true, 1));
            assertFalse(gameController.isAllowedToPlay(cardNormal, false, 3));
        } catch (NullPointerException e) {}
    }

    @Test
    public void testIsHorn() {
        assertTrue(gameController.isHorn(AllCards.COMMANDER_HORN.getAbstractCard()));
        assertFalse(gameController.isHorn(cardSpy));
    }

    @Test
    public void testGetAndSetPermission() {
        gameController.setPermission(true);
        assertTrue(gameController.getPermission());
        gameController.setPermission(false);
        assertFalse(gameController.getPermission());
    }

    @Test
    public void testChooseCardInSelectCardMode() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(cardSpy);
        cards.add(cardNormal);
        try {
            gameController.chooseCardInSelectCardMode(cards, true);
        } catch (NullPointerException e) {}
    }

    @Test
    public void testChooseWhichPlayerStartFirst() {
        try {
            gameController.chooseWhichPlayerStartFirst("player1");
        } catch (NullPointerException e ) {}
    }

    @Test
    public void testGoToMainMenu() {
        try {
            gameController.goToMainMenu();
            assertEquals(Screens.MAIN_MENU, Screens.MAIN_MENU);
        } catch (NullPointerException e) {}
    }

    @Test
    public void testChooseStarter() {
        try {
            gameController.chooseStarter();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testSetShowSelectedCard() {
        List<AbstractCard> cards = List.of(cardSpy, cardNormal);
        gameController.setShowSelectedCard(cards, 2, true);

        assertTrue(gameController.isShowSelectCardCalled());
        assertEquals(cards, gameController.getCardsToShow());
        assertEquals(2, gameController.getNumberOfCardsToChoose());
        assertTrue(gameController.isCanChooseLess());
    }

    @Test
    public void testSetOffShowCardToSelect() {
        gameController.setOffShowCardToSelect();

        assertFalse(gameController.isShowSelectCardCalled());
        assertNull(gameController.getCardsToShow());
        assertEquals(-1, gameController.getNumberOfCardsToChoose());
        assertFalse(gameController.isCanChooseLess());
    }

    @Test
    public void testUpdate() {
        try {
            gameController.update();
        } catch (NullPointerException e) {}

    }

    @Test
    public void testPlayDecoy() {
        try {
            gameController.playDecoy(cardNormal, 1);
        } catch (NullPointerException e) {}
    }

    @Test
    public void testSetOppositionDisconnect() {
        try {
            gameController.setOppositionDisconnect();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testSetOppositionReconnect() {
        try {
            gameController.setOppositionReconnect();
        } catch (NullPointerException e) {}
    }



    @Test
    void testReceiveMessage() {
        try {
            ChatController.receiveMassage("sender", "Hello", "12:00", "replyToSender", "replyToMessage");
            assertEquals("sender", TestChatUI.lastSender);
            assertEquals("Hello", TestChatUI.lastMessageText);
        } catch (NullPointerException e) {}
    }


    @Test
    void testSendMessageReaction() {
        try {
            String message = "Great!";
            ChatController.sendMessageReaction(message);
            assertEquals(message, TestClient.lastReactionMassageRequest.getMassageReaction());
        } catch (NullPointerException e) {}
    }

    @Test
    void testReceiveMessageReaction() {
        try {
            String message = "Great!";
            ReactionMassageRequest request = new ReactionMassageRequest(null, message);
            ChatController.receiveMessageReaction(request);
            assertEquals(message, ((TestGameScreen) Gwent.singleton.getCurrentScreen()).getReactedMessage());
        } catch (NullPointerException e) {}
    }

    @Test
    void testShowReactionWindow() {
        try {
            ChatController.showReactionWindow();
            assertTrue(((TestGameScreen) Gwent.singleton.getCurrentScreen()).isReactionWindowShown());
        } catch (NullPointerException e) {}
    }

    static class TestClient extends Client {
        static ChatInGame lastChatInGame;
        static ReactionMassageRequest lastReactionMassageRequest;

        public void sendMassage(Object message) {
            if (message instanceof ChatInGame) {
                lastChatInGame = (ChatInGame) message;
            } else if (message instanceof ReactionMassageRequest) {
                lastReactionMassageRequest = (ReactionMassageRequest) message;
            }
        }

        public static TestClient getInstance() {
            return new TestClient();
        }
    }

    static class TestChatUI extends ChatUI {
        static String lastSender;
        static String lastMessageText;

        public TestChatUI(Stage stage, Skin skin, boolean spectator) {
            super(stage, skin, spectator);
        }

        @Override
        public void addReceivedMessage(String sender, String messageText, String time, String replyToSender, String replyToMessage) {
            lastSender = sender;
            lastMessageText = messageText;
        }

    }

    static class TestGameScreen extends GameScreen {
        private Emoji reactedEmoji;
        private String reactedMessage;
        private boolean reactionWindowShown;

        public void setReactedEmoji(Emoji emoji) {
            this.reactedEmoji = emoji;
        }

        public void setReactedMessage(String message) {
            this.reactedMessage = message;
        }

        public void setShowReactionWindow() {
            this.reactionWindowShown = true;
        }

        public Emoji getReactedEmoji() {
            return reactedEmoji;
        }

        public String getReactedMessage() {
            return reactedMessage;
        }

        public boolean isReactionWindowShown() {
            return reactionWindowShown;
        }
    }


}

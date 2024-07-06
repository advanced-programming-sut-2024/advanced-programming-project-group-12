package com.mygdx.game.model.user;

import com.google.gson.Gson;
import com.mygdx.game.model.game.Faction;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;
import com.mygdx.game.model.game.card.CommanderCard;
import com.mygdx.game.model.game.card.CommanderCards;
import com.mygdx.game.model.network.massage.clientRequest.postSignInRequest.ClientFriendRequest;
import com.mygdx.game.model.network.massage.serverResponse.preGameRosponse.InviteUserToPlay;

import javax.xml.stream.FactoryConfigurationError;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class User {
    // static fields
    private static User loggedInUser;
    private static User toBeSignedUp;
    private static ArrayList<User> users = new ArrayList<>();

    // instance fields
    private String username;
    private String nickname;
    private String password;
    private String email;
    private HashMap<SecurityQuestion, String> securityQuestion;

    private ArrayList<Game> allGamePlayed;
    private UserInfo userInfo;

    private Faction faction;
    private String leader;
    private ArrayList <String> deck;

    private ArrayList<User> friends;
    private ArrayList<FriendRequest> receivedFriendRequests;
    private ArrayList<FriendRequest> sentFriendRequests;

    private Player player;

    //constructors
    public User(String username, String nickname, String password, String email) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.faction = Faction.NORTHERN_REALMS;
        this.securityQuestion = new HashMap<>();
        this.allGamePlayed = new ArrayList<>();
        this.userInfo = new UserInfo();
        this.deck = initializeRandomDeck();
        this.friends = new ArrayList<>();
        this.receivedFriendRequests = new ArrayList<>();
        this.sentFriendRequests = new ArrayList<>();
        this.save();
    }

    // static methods
    public static ArrayList<User> getUsers() {
        return users;
    }
    private ArrayList<String> initializeRandomDeck() {
        ArrayList<String> deckToAdd = new ArrayList<>();
        int numberOfUnitCardsAdded = 0;
        int numberOfSpecialCardsAdded = 0;
        for (AbstractCard card : AllCards.getFactionCardsByFaction(Faction.NORTHERN_REALMS)) {
            if (card.getFaction() == Faction.SPECIAL || card.getFaction() == Faction.WEATHER) {
                if (numberOfSpecialCardsAdded < 10) {
                    deckToAdd.add(card.getName());
                    numberOfSpecialCardsAdded ++;
                }
            } else if (numberOfUnitCardsAdded < 22) {
                deckToAdd.add(card.getName());
                numberOfUnitCardsAdded ++;
            }
        }
        return deckToAdd;
    }

    public static User getToBeSignedUp() {
        return toBeSignedUp;
    }

    public static void setToBeSignedUp(User toBeSignedUp) {
        User.toBeSignedUp = toBeSignedUp;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File("Data/Users");
        for (File userFolder : Objects.requireNonNull(folder.listFiles(File::isDirectory))) {
            users.add(getUserByUsername(userFolder.getName()));
        }
        return users;
    }

    public static List<User> usersForScoreBoardByNumber(int number) {
        /**
         * each number like i returns the i+1th division in allUsers ranking
         * ex: 0 gives top 10 players
         */
        return users.subList(number * 10, number * 10 + 10);
    }


    //getter and setter methods
    public void setSecurityQuestion(SecurityQuestion question, String answer) {
        this.securityQuestion.put(question, answer);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setUsername(String username) {
        File oldFile = new File("Data/Users/" + this.username);
        File newFile = new File("Data/Users/" + username);
        oldFile.renameTo(newFile);
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public HashMap<SecurityQuestion, String> getSecurityQuestion() {
        return securityQuestion;
    }
    //instance methods
    public static void updateRanking() {
        // Implementation
    }

    public boolean doesPasswordMatch(String password) {
        return this.password.equals(password);
    }

    //this part is about saving user data and loading it

    public static User getUserByUsername(String username) {
        File file = new File("Data/Users/" + username + "/data.json");
        if(!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            reader.close();
            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() {
        File file = new File("Data/Users/" + username + "/data.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeUser(User user) {
        try {
            deleteDirectory(Paths.get("Data/Users/" + user.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(path);
    }

    public void updateInfo() {
        if (this.username.equals("_Guest_"))
            return;
        User.removeUser(this);
        this.save();
    }

    public Faction getFaction() {
        return this.faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public LinkedList<AbstractCard> getDeckAsCard() {
        LinkedList<AbstractCard> deckCards = new LinkedList<>();
        for (String cardName : deck) {
            deckCards.add(AllCards.getCardByCardName(cardName));
        }
        return deckCards;
    }

    public ArrayList<String> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<String> deck) {
        this.deck = deck;
    }

    public void setDeck(List<AbstractCard> selectedCards) {
        deck.clear();
        for (AbstractCard card : selectedCards) {
            deck.add(card.getName());
        }
        save();
    }
    public void resetDeck() {
        deck.clear();
        save();
    }

    public void addGame(Game game) {
        allGamePlayed.add(game);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Game> getAllGamePlayed() {
        return allGamePlayed;
    }

    public ArrayList<FriendRequest> getReceivedFriendRequests() {
        return receivedFriendRequests;
    }
    public void setReceivedFriendRequests(ArrayList<FriendRequest> receivedFriendRequests) {
        this.receivedFriendRequests = receivedFriendRequests;
    }

    public ArrayList<FriendRequest> getSentFriendRequests() {
        return sentFriendRequests;
    }
    public void setLeader(AbstractCard leader) {
        this.leader = leader.getName();
        save(); // Save the user's data after setting the leader
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public String getLeader() {
        return leader;
    }
    public CommanderCard getLeaderAsCard() {
        return CommanderCards.getCardByCardName(leader);
    }

    public void sendMassage(InviteUserToPlay inviteUserToPlay) {
        return ;
    }


    public void addFriendRequest(FriendRequest friendRequest) {
        receivedFriendRequests.add(friendRequest);
    }

    public void requestAccepted(FriendRequest friendRequest) {
        friendRequest.setStatus("accepted");
    }

    public void requestRejected(FriendRequest friendRequest) {
        friendRequest.setStatus("rejected");
    }

    public int getScore() {
        return getUserInfo().getGamesWon();
    }
}

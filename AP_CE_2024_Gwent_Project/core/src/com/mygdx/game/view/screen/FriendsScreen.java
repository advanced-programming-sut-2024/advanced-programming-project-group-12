package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.FriendsController;
import com.mygdx.game.controller.local.GameRequestController;
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendsScreen implements Screen {
    private User loggedInUser;
    private final Stage stage;
    private final Table table;
    private final FriendsController controller;
    private TextureRegion backgroundTexture;

    // UI elements for searching users and displaying profiles
    private TextField searchField;
    private TextButton searchButton;
    private TextButton viewRequestsButton;
    private TextButton viewFriendsButton;
    private TextButton back;
    private Skin skin;

    public FriendsScreen() {
        this.loggedInUser = User.getLoggedInUser();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        controller = new FriendsController();
        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("backgrounds/profile_menu_background.jpg")));

        // Initialize UI elements
        skin = Gwent.singleton.getSkin();
        searchField = new TextField("", skin);
        searchButton = new TextButton("Search", skin);
        viewRequestsButton = new TextButton("View Friend Requests", skin);
        viewFriendsButton = new TextButton("View Friends", skin);
        back = new TextButton("Back", skin);

        // Layout for back button
        Table bottomTable = new Table();
        bottomTable.bottom().left().pad(10);
        bottomTable.add(back).width(200);
        stage.addActor(bottomTable);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gwent.singleton.changeScreen(Screens.PROFILE_MENU);
            }
        });

        searchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = searchField.getText();
                User user = User.getUserByUsername(username);
                table.clear();
                addSearchUI();
                if (user != null) {
                    table.add(showUserProfile(user)).colspan(4).expand().fill();
                } else {
                    table.add(new Label("User not found", skin)).colspan(4);
                }
            }
        });

        viewRequestsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                refreshRequests();
            }
        });

        viewFriendsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showFriendsList();
            }
        });

        addSearchUI();
    }

    private void addSearchUI() {
        table.clear();
        table.top().padTop(20);

        // Top center layout for viewRequestsButton and viewFriendsButton
        Table topTable = new Table();
        topTable.add(viewRequestsButton).width(570).padRight(20);
        topTable.add(viewFriendsButton).width(400);
        table.add(topTable).colspan(4).center().row();

        // Center layout for searchField and searchButton
        Table searchTable = new Table();
        searchTable.add(searchField).width(500).padRight(10);
        searchTable.add(searchButton).width(300);
        table.add(searchTable).colspan(4).padTop(50).center().row();
    }

    private static boolean requestInfoReceived = false;
    private static Map<String, Map<String, FriendRequest>> requestsHashMap = new HashMap<>();

    public static void setRequestInfoReceived(boolean requestInfoReceived) {
        FriendsScreen.requestInfoReceived = requestInfoReceived;
    }

    public static void setRequestsHashMap(Map<String, Map<String, FriendRequest>> requestsHashMap) {
        FriendsScreen.requestsHashMap = requestsHashMap;
    }

    private Table showUserProfile(User user) {
        Table profileTable = new Table();
        profileTable.add(new Label(user.getUsername() + " a.k.a " + user.getNickname(), skin, "subtitle"));
        profileTable.row();
        profileTable.add(new Label("Faction: " + user.getFaction().getName(), skin, "subtitle")).padRight(20).row();
        profileTable.add(new Label("Wins: " + user.getUserInfo().getWins(), skin, "subtitle")).padRight(20).row();
        profileTable.add(new Label("Losses: " + user.getUserInfo().getLosses(), skin, "subtitle")).padRight(20).row();
        profileTable.row();

        // Determine if there's an existing friend request
        FriendRequest existingRequest = null;
        if (loggedInUser.getSentFriendRequests() != null) {
            for (FriendRequest request : loggedInUser.getSentFriendRequests()) {
                if (request.getToUser().getUsername().equals(user.getUsername())) {
                    existingRequest = request;
                    break;
                }
            }
        }

        TextButton addFriendButton = new TextButton("Send a friend request", skin);
        addFriendButton.setDisabled(existingRequest != null);
        addFriendButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.sendFriendRequest(loggedInUser, user);
                Gdx.app.log("FriendsScreen", "Friend request sent to " + user.getUsername());
                addFriendButton.setDisabled(true);
            }
        });

        profileTable.add(addFriendButton).right();
        profileTable.row();

        // Display request status if there's an existing request
        if (existingRequest != null) {
            Label requestStatusLabel = new Label("Friend request status: " + existingRequest.getStatus(), skin);
            profileTable.add(requestStatusLabel).row();
        }

        return profileTable;
    }

    private Table showAllRequests() {
        Table requestsTable = new Table();

        if (requestsHashMap == null || requestsHashMap.isEmpty()) {
            Gdx.app.log("FriendsScreen", "No friend requests.");
            requestsTable.add(new Label("No friend requests.", skin)).colspan(4).center().row();
            return requestsTable;
        }

        for (Map<String, FriendRequest> requestMap : requestsHashMap.values()) {
            for (FriendRequest request : requestMap.values()) {
                Table requestRow = new Table();
                if (request.getFromUser().getUsername().equals(loggedInUser.getUsername())) {
                    // If logged-in user is the sender, show the status
                    requestRow.add(new Label("To: " + request.getToUsername(), skin)).padRight(20);
                    requestRow.add(new Label("Status: " + request.getStatus(), skin));
                } else {
                    // If logged-in user is the receiver, show the status
                    requestRow.add(new Label("From: " + request.getFromUsername(), skin)).padRight(20);
                    requestRow.add(new Label("Status: " + request.getStatus(), skin));

                    if ("pending".equals(request.getStatus())) {
                        // If the request is pending, show accept/reject buttons
                        TextButton acceptButton = new TextButton("Accept", skin);
                        TextButton rejectButton = new TextButton("Reject", skin);

                        acceptButton.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                controller.acceptFriendRequest(loggedInUser, request);
                                refreshRequests();
                            }
                        });

                        rejectButton.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent event, Actor actor) {
                                controller.rejectFriendRequest(loggedInUser, request);
                                refreshRequests();
                            }
                        });

                        requestRow.add(acceptButton).padRight(10);
                        requestRow.add(rejectButton);
                    }
                }

                requestsTable.add(requestRow).padBottom(10).row();
            }
        }

        return requestsTable;
    }

    private void refreshRequests() {
        table.clear();
        addSearchUI();
        table.add(showAllRequests()).expand().fill().colspan(4).row();
    }

    private void showFriendsList() {
        table.clear();
        addSearchUI();

        ArrayList<String> friends = loggedInUser.getFriendsList();
        if (friends.isEmpty()) {
            table.add(new Label("You have no friends", skin)).colspan(4).center().row();
            return;
        }

        for (String friend : friends) {
            Table friendRow = new Table();
            friendRow.add(new Label(friend + " a.k.a " + User.getUserByUsername(friend).getNickname(), skin)).padRight(20);

            TextButton viewProfileButton = new TextButton("View Profile", skin);
            viewProfileButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    table.clear();
                    addSearchUI();
                    table.add(showUserProfile(User.getUserByUsername(friend))).colspan(4).expand().fill();
                }
            });

            TextButton playButton = new TextButton("Play", skin);
            playButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameRequestController controller1 = new GameRequestController();
                    controller1.sendGameRequest(friend);
                    Gdx.app.log("FriendsScreen", "Play button clicked for " + friend);
                }
            });

            friendRow.add(viewProfileButton).padRight(10);
            friendRow.add(playButton);
            table.add(friendRow).padBottom(10).row();
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act();
        stage.draw();
        if (requestInfoReceived) {
            refreshRequests();
            setRequestInfoReceived(false);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}

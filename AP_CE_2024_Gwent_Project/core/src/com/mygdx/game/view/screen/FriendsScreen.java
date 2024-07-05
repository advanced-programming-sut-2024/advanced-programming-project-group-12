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
import com.mygdx.game.model.user.FriendRequest;
import com.mygdx.game.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

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
        back = new TextButton("Back", skin);
        table.add(back).width(200).padRight(70);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gwent.singleton.setScreen(new MainMenuScreen());
            }
        });

        searchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = searchField.getText();
                //put this to server side shit
                User user = User.getUserByUsername(username);
                table.clear();
                table.add(searchField).width(300).padRight(100);
                table.add(searchButton).width(200);
                table.add(viewRequestsButton).width(550).padLeft(50);
                table.row();
                if (user != null) {
                    table.add(showUserProfile(user)).colspan(3).expand().fill();
                } else {
                    table.add(new Label("User not found", skin)).colspan(3);
                }
            }
        });

        viewRequestsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.clear();
                table.add(searchField).width(300).padRight(100);
                table.add(searchButton).width(200);
                table.add(viewRequestsButton).width(550).padLeft(50);
                table.row();
                controller.getFriendRequests();
            }
        });

        // Layout
        table.add(searchField).width(300).padRight(100);
        table.add(searchButton).width(200);
        table.add(viewRequestsButton).width(550).padLeft(50);
        table.row();
    }
    private static boolean requestInfoReceived = false;
    private static HashMap<String, HashMap<String, FriendRequest>> requestsHashMap = new HashMap<>();

    private Table showUserProfile(User user) {
        Table profileTable = new Table();
        profileTable.add(new Label(user.getUsername() + " a.k.a " + user.getNickname(), skin,  "subtitle"));
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
            Label statusLabel = new Label("Friend request " + existingRequest.getStatus(), skin);
            profileTable.add(statusLabel).colspan(2);
            profileTable.row();
        }

        return profileTable;
    }

    private Table showPendingRequests() {

        Table requestsTable = new Table();

        // TODO : show the friend requests received from server

        return requestsTable;
    }

    private void refreshRequestsTable() {
        table.clear();
        table.add(searchField).width(300).padRight(100);
        table.add(searchButton).width(200);
        table.add(viewRequestsButton).width(550).padLeft(50);
        table.row();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch batch = Gwent.singleton.getBatch();
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        if (requestInfoReceived) {
            table.add(showPendingRequests()).colspan(3).expand().fill();
            requestInfoReceived = false;
        }
        stage.act(delta);
        stage.draw();
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

package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.GameRequestController;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

import java.util.ArrayList;
import java.util.List;

public class GameRequestScreen implements Screen {
    // essentials
    private final GameRequestController controller;
    private final Stage stage;
    private final Table table;
    private final SpriteBatch batch;
    private final Texture background;
    private final Skin skin;
    private boolean requestSent;
    private static boolean showRequestWindow;
    private static String from;
    private final Window requestWindow;
    private final List<String> receivedRequests;
    // fields & buttons
    private final TextField nameField;
    private final Button sendAGameRequestButton;
    private final Button backButton;
    private Button acceptGameRequestButton;
    private Button rejectGameRequestButton;
    private Button randoGameButton;
    private Button listOfRandomGamesButton;
    // labels
    private final Label requestSentLabel;
    private final Label errorLabel;
    private final Label requestListLabel;

    private String gameType;
    private SelectBox<String> gameTypeSelectBox;
    public ArrayList<String> listOfRandomGames = null;


    public GameRequestScreen() {
        this.controller = new GameRequestController();
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("backgrounds/main_background.jpg"));
        this.skin = Gwent.singleton.getSkin();

        this.nameField = new TextField("", skin);
        this.sendAGameRequestButton = new TextButton("Play Game", skin);
        this.randoGameButton = new TextButton("Start Random Game", skin);
        this.listOfRandomGamesButton = new TextButton("List of Random Games", skin);
        this.requestSentLabel = new Label("", skin);
        this.errorLabel = new Label("", skin);
        this.backButton = new TextButton("Back", skin);
        this.requestListLabel = new Label("", skin);
        this.requestWindow = new Window("Game Requests", skin);
        requestWindow.add(requestListLabel).pad(10);
        requestWindow.setSize(1000, 400);
        requestWindow.setPosition(Gdx.graphics.getWidth() / 2f - 600, Gdx.graphics.getHeight() / 2f - 250);
        requestWindow.setVisible(false);
        stage.addActor(requestWindow);

        this.receivedRequests = new ArrayList<>();

        table.setFillParent(true);
        table.add(nameField).width(500).height(80).pad(10);
        table.add(sendAGameRequestButton).width(350).height(80);
        table.row();
        // Add a select box to the table
        gameTypeSelectBox = new SelectBox<>(skin);
        gameTypeSelectBox.setItems("Public", "Private");
        table.add(gameTypeSelectBox).width(300).height(80).pad(10);
        table.row();
        table.add(randoGameButton).width(500).height(100).pad(10);
        table.add(listOfRandomGamesButton).width(600).height(100).pad(10);
        table.row();
        table.add(requestSentLabel);
        table.row();
        table.add(errorLabel);
        table.row();
        table.add(backButton).padLeft(330);
        table.row();
        stage.addActor(table);


        sendAGameRequestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = nameField.getText();
                if (!userIsMyFriend(username)) {
                    Dialog dialog = new Dialog("Error", Gwent.singleton.skin) {
                        public void result(Object obj) {
                        }
                    };
                    dialog.row();
                    dialog.text("You can only send game requests to you friends").row();
                    dialog.button("OK", true);
                    dialog.show(stage);
                }
                else if (!requestSent) {
                    String name = nameField.getText();
                    if (controller.userExists(name)) {
                        String sender = User.getLoggedInUser().getUsername();
                        gameType = gameTypeSelectBox.getSelected();
                        controller.sendGameRequest(name, gameType.equals("Public"));
                        requestSent = true;
                        requestSentLabel.setText("Request sent to " + name);
                        errorLabel.setText("");
                        System.out.println("Request sent");
                    } else {
                        errorLabel.setText("User not found.");
                    }
                }
            }
        });
        listOfRandomGamesButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        controller.sendListOfRandomGamesRequest();
                    }
                });
        randoGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sendRandomGameRequest();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.PRE_GAME_MENU);
            }
        });

        requestSent = false;
    }

    public static void showRequestWindow(String from1) {
        from = from1;
        showRequestWindow = true;
    }

    private void updateRequestWindow() {
        if (showRequestWindow) {
            initializeRequestWindow();
        }
    }

    private void initializeRequestWindow () {
        receivedRequests.add("Game request from: " + from);
        requestListLabel.setText(String.join("\n", receivedRequests));
        acceptGameRequestButton = new TextButton("Accept", skin);
        rejectGameRequestButton = new TextButton("Reject", skin);
        requestWindow.add(acceptGameRequestButton).pad(10);
        requestWindow.add(rejectGameRequestButton).pad(10);
        acceptGameRequestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    controller.acceptGameRequest(from);
                requestWindow.setVisible(false);
            }
        });
        rejectGameRequestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    controller.rejectGameRequest(from);
                requestWindow.setVisible(false);
            }
        });
        requestWindow.setVisible(true);
        showRequestWindow = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateRequestWindow();

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
        if(listOfRandomGames != null) {
            showListOfRandomGames();
            listOfRandomGames = null;
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        background.dispose();
    }
    public void showListOfRandomGames() {
        Window listOfRandomGamesWindow = new Window("List of Random Games", skin);
        listOfRandomGamesWindow.setSize(800, 600);
        listOfRandomGamesWindow.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 300);
        listOfRandomGamesWindow.setVisible(false);
        stage.addActor(listOfRandomGamesWindow);

        Table table = new Table();
        table.setFillParent(true);

        for (String game : listOfRandomGames) {
            Label gameLabel = new Label(game, skin);
            Button playButton = new TextButton("Play", skin);
            table.add(gameLabel).width(300).height(90).pad(10);
            table.add(playButton).width(200).height(90).pad(10);
            table.row();

            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Join the selected game
                    controller.joinRandomGame(game);
                    listOfRandomGamesWindow.setVisible(false);
                }
            });
        }
        Button closeButton = new TextButton("close", skin);
        table.add(closeButton).width(200).height(100);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Join the selected game
                listOfRandomGamesWindow.setVisible(false);
            }
        });

        listOfRandomGamesWindow.add(table);
        listOfRandomGamesWindow.setVisible(true);

    }
    public static boolean userIsMyFriend(String user) {
        return User.getLoggedInUser().getFriends().contains(user);
    }
    public GameRequestController getController() {
        return controller;
    }
}

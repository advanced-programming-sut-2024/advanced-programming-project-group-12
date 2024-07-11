package com.mygdx.game.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Gwent;
import com.mygdx.game.controller.local.ChatController;
import com.mygdx.game.controller.local.TournamentController;
import com.mygdx.game.model.network.Client;
import com.mygdx.game.model.user.User;
import com.mygdx.game.view.Screens;

public class PreTournamentScreen implements Screen {
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;

    private TextButton joinTournamentButton;
    private TextButton startButton;
    private TextButton addPlayerTextButton;
    private TextButton backButton;
    private TextField playerTextField;
    public PreTournamentScreen() {
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("bg/pre-tournament-background.png"));

        backButton = new TextButton("Back", Gwent.singleton.skin);
        backButton.setSize(200, 120);
        backButton.setPosition((float) Gwent.WIDTH /2 - backButton.getWidth() / 2, 100);
        stage.addActor(backButton);

        joinTournamentButton = new TextButton("Join Existing Tournament", Gwent.singleton.skin);
        joinTournamentButton.setSize(700, 120);
        joinTournamentButton.setPosition((float) Gwent.WIDTH /2 - joinTournamentButton.getWidth() / 2, 300);
        stage.addActor(joinTournamentButton);


        startButton = new TextButton("Start New Tournament", Gwent.singleton.skin);
        startButton.setSize(700, 120);
        startButton.setPosition((float) Gwent.WIDTH /2 - startButton.getWidth() / 2, 500);
        stage.addActor(startButton);


        playerTextField = new TextField("", Gwent.singleton.skin);
        playerTextField.setMessageText("player username");
        playerTextField.setSize(600, 120);
        playerTextField.setPosition((float) Gwent.WIDTH /2 - 1 * playerTextField.getWidth() , 700);
        stage.addActor(playerTextField);


        addPlayerTextButton = new TextButton("Add Player", Gwent.singleton.skin);
        addPlayerTextButton.setSize(300, 120);
        addPlayerTextButton.setPosition((float) Gwent.WIDTH /2 +  addPlayerTextButton.getWidth(), 700);
        stage.addActor(addPlayerTextButton);
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {
        startButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               handleStart();
           }
        });
        addPlayerTextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newPlayerUsername = playerTextField.getText();
                addNewPlayer(newPlayerUsername);
            }
        });
        joinTournamentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinTournament();
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gwent.singleton.changeScreen(Screens.PRE_GAME_MENU);
            }
        });


    }

    private void joinTournament() {
        if(TournamentController.getInstance().isPlayerAlreadyAdded(Client.getInstance().getUser().getUsername())) {
            Gwent.singleton.changeScreen(Screens.TOURNAMENT);
        } else {
            showError("you are not in any tournament!");
        }
    }

    private void addNewPlayer(String newPlayerUsername) {
        User user = User.getUserByUsername(newPlayerUsername);
        if(TournamentController.getInstance().isParticipantsCompleted()) {
            showError("players are now completed please start game!");
        } else if(user == null) {
            showError("This user does not exist!");
        } else if(TournamentController.getInstance().isPlayerAlreadyAdded(newPlayerUsername)) {
          showError("User is Already in Tournament!");
        } else {
            TournamentController.getInstance().addToParticipants(user);
            showError("user added to tournament successfully!");
        }
    }

    private void handleStart() {
        if(TournamentController.getInstance().isParticipantsCompleted()) {
            TournamentController.getInstance().startTournament();
            Gwent.singleton.changeScreen(Screens.TOURNAMENT);
        } else {
            showError("you should choose 8 players to start a tournament!");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    private void showError(String message) {
        Dialog errorDialog = new Dialog("Error", Gwent.singleton.getSkin());
        errorDialog.setColor(Color.RED);
        errorDialog.text(message);
        errorDialog.button("OK");
        errorDialog.show(stage);
    }
}

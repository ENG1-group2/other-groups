package com.badlogic.UniSim2;

import com.badlogic.gdx.Screen;

/**
 * This screen should be shown when the game ends.
 */
public class EndScreen implements Screen {
    private Main game;
    private StretchViewport viewport;
    private Stage stage;
    private Label scoreLabel;
    private final Skin skin;
    private int score;



    public EndScreen(Main game, int score){
        this.game = game;
        this.viewport = game.getViewport();
        this.stage = new Stage(this.viewport);
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.score = score;
        createScoreLabel();
    }

    // Adds a label to the middle of the screen displaying the score that the player
    // managed to get throughout the game.
    private void createScoreLabel() {
        // Initialize scoreLabel
        scoreLabel = new Label("Score : " + score, skin);
        scoreLabel.setFontScale(3); 
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setColor(Consts.TIMER_COLOR);
        
        // Position the label at the top center of the screen
        scoreLabel.setPosition(Consts.SCORE_LABEL_X, Consts.SCORE_LABEL_Y, Align.center);

        // Add the label to the stage
        stage.addActor(scoreLabel);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
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
        stage.dispose();
        skin.dispose();
    }
}

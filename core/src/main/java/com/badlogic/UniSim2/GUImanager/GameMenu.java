package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Consts;
import com.badlogic.UniSim2.GameScreen;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildingmanager.BuildingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

/**
 * This is the game menu that is shown by the {@link GameScreen}. It contains 
 * the {@link Timer timer} for the game and {@link BuildingMenu the building menu}
 * which can be used to place new buildings. 
 */
public class GameMenu {
    private Stage stage;
    private final Skin skin;
    private BuildingMenu buildingMenu;
    private Timer timer;
    private Label timerLabel;
    private boolean isPaused;
    
    public GameMenu(Main game, Timer timer, BuildingManager buildings){
        stage = new Stage(game.getViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json")); 
        buildingMenu = new BuildingMenu(stage, buildings);
        this.timer = timer;
        isPaused = false;
        createMenu();
    }

    /**
     * Activates the input processor needed for the menu to use inputs.
     */
    public void activate() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createMenu(){
        buildingMenu.createBuildingMenu();
        createTimerLabel();
        //createPausedLabel();
    }

    // Adds a label at the top of the screen displaying the time
    private void createTimerLabel(){

        // Initialize timerLabel
        timerLabel = new Label("00:00", skin);
        timerLabel.setFontScale(3); 
        timerLabel.setAlignment(Align.center);
        timerLabel.setColor(Consts.TIMER_COLOR);
        
        // Position the label at the top center of the screen
        timerLabel.setPosition(Consts.TIMER_X, Consts.TIMER_Y, Align.center);

        // Add the label to the stage
        stage.addActor(timerLabel);
    }

    // Updates the time shown on the label to the time played
    private void updateTimerLabel(){
        float elapsedTime = timer.getElapsedTime();
        int minutes = (int) (elapsedTime / 60); 
        int seconds = (int) (elapsedTime % 60);
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }


    /**
     * Should be called when the game is paused.
     */
    public void pause() {
        timerLabel.setText("PAUSED");
        isPaused = true;
    }

    /**
     * Should be called when the game is resumed.
     */
    public void resume() {
        updateTimerLabel();
        isPaused = false;
    }
    
    /**
     * 
     */
    public void input(){
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void draw(){
        if (isPaused == false) {
            updateTimerLabel();    
        }
        buildingMenu.draw();
        stage.draw();
    }

    public boolean getPaused(){
        return isPaused;
    }

    public void dispose(){
        buildingMenu.dispose();
        stage.dispose();
        skin.dispose();
    }
}

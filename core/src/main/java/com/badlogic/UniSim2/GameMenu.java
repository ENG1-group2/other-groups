package com.badlogic.UniSim2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

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
    private boolean paused;
    private Game game;
    
    public GameMenu(StretchViewport viewport, BuildingManager buildings, Game game){

        this.game = game;
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json")); 
        buildingMenu = new BuildingMenu(stage, buildings);
        timer = new Timer();
        paused = true;
        createMenu();
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

    // Changes paused when the space bar is pressed
    private void checkPause(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            paused = !paused;
        }
    }

    // Checks if the game has ended
    private void checkEndGame(){
        boolean gameEnded = timer.getReachedMaxTime();
        if(gameEnded){
            game.setScreen(new StartScreen(game)); // Returns to the start screen
        }
    }

    public void input(){
        stage.act(Gdx.graphics.getDeltaTime());
        // Checks if space bar has been pressed
        checkPause();
        // If unpaused
        if(!paused){
            buildingMenu.render();
            timer.update(); // Update the timer
            updateTimerLabel(); // Update the timer label to show the time played
            checkEndGame(); // Check if the game has ended
        }
        else{
            timerLabel.setText("PAUSED");
        }
    }

    public void draw(){
        buildingMenu.drawBuildingMenu();
        stage.draw();
    }

    public boolean getPaused(){
        return paused;
    }

    public void dispose(){
        buildingMenu.dispose();
    }
}

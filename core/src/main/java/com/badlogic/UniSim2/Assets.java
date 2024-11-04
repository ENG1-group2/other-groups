package com.badlogic.UniSim2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class is used to store all textures that will be used in the game.
 * All textures are made public static variable and should not be changed
 * by another other class.
 */
public class Assets {

    public static Sound click;
    public static Sound gameStart;
    public static Music music;

    public static Texture startBackgroundTexture;
    public static Texture startButtonUpTexture;
    public static Texture startButtonDownTexture;

    public static Texture backgroundTexture;
    public static Texture pathTexture;

    public static Texture menuBarTexture;

    public static Texture accomodationButtonUpTexture;
    public static Texture lectureHallButtonUpTexture;
    public static Texture libraryButtonUpTexture;
    public static Texture courseButtonUpTexture;
    public static Texture foodZoneButtonUpTexture;
    public static Texture recreationalButtonUpTexture;
    public static Texture natureButtonUpTexture;
    public static Texture[] buttonUpTextures;

    public static Texture accomodationButtonDownTexture;
    public static Texture lectureHallButtonDownTexture;
    public static Texture libraryButtonDownTexture;
    public static Texture courseButtonDownTexture;
    public static Texture foodZoneButtonDownTexture;
    public static Texture recreationalButtonDownTexture;
    public static Texture natureButtonDownTexture;
    public static Texture[] buttonDownTextures;

    public static Texture accomodationPlacedTexture;
    public static Texture accomodationCollisionTexture;
    public static Texture accomodationDraggingTexture;

    public static Texture lectureHallPlacedTexture;
    public static Texture lectureHallCollisionTexture;
    public static Texture lectureHallDraggingTexture;

    public static Texture libraryPlacedTexture;
    public static Texture libraryCollisionTexture;
    public static Texture libraryDraggingTexture;

    public static Texture coursePlacedTexture;
    public static Texture courseCollisionTexture;
    public static Texture courseDraggingTexture;

    public static Texture foodZonePlacedTexture;
    public static Texture foodZoneCollisionTexture;
    public static Texture foodZoneDraggingTexture;

    public static Texture recreationalPlacedTexture;
    public static Texture recreationalCollisionTexture;
    public static Texture recreationalDraggingTexture;

    public static Texture naturePlacedTexture;
    public static Texture natureCollisionTexture;
    public static Texture natureDraggingTexture;

    private Assets() {};

    /**
     * This method loads all the textures that might be used.
     * Note that this method should not be called before libgdx has called the
     * {@link Main#create()} method. 
     */
    public static void loadTextures(){

        // Sound effects and music
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        gameStart = Gdx.audio.newSound(Gdx.files.internal("gameStart.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.25f);

        // Start menu textures
        startBackgroundTexture = new Texture("startBackground.png");
        startButtonUpTexture = new Texture("startButtonUp.png");
        startButtonDownTexture = new Texture("startButtonDown.png");

        // Background texture
        backgroundTexture = new Texture("background.png");
        pathTexture = new Texture("path.png");

        menuBarTexture = new Texture("menuBar.png");

        // Building button textures when not hovering over
        accomodationButtonUpTexture = new Texture("accomodationButtonUp.png");
        lectureHallButtonUpTexture = new Texture("lectureHallButtonUp.png");
        libraryButtonUpTexture = new Texture("libraryButtonUp.png");
        courseButtonUpTexture = new Texture("courseButtonUp.png");
        foodZoneButtonUpTexture = new Texture("foodZoneButtonUp.png");
        recreationalButtonUpTexture = new Texture("recreationalButtonUp.png");
        natureButtonUpTexture = new Texture("natureButtonUp.png");

        buttonUpTextures = new Texture[]
        {
            accomodationButtonUpTexture,
            lectureHallButtonUpTexture,
            libraryButtonUpTexture,
            courseButtonUpTexture,
            foodZoneButtonUpTexture,
            recreationalButtonUpTexture,
            natureButtonUpTexture
        };

        // Building button textures when hovering over
        accomodationButtonDownTexture = new Texture("accomodationButtonDown.png");
        lectureHallButtonDownTexture = new Texture("lectureHallButtonDown.png");
        libraryButtonDownTexture = new Texture("libraryButtonDown.png");
        courseButtonDownTexture = new Texture("courseButtonDown.png");
        foodZoneButtonDownTexture = new Texture("foodZoneButtonDown.png");
        recreationalButtonDownTexture = new Texture("recreationalButtonDown.png");
        natureButtonDownTexture = new Texture("natureButtonDown.png");

        buttonDownTextures = new Texture[]
        {
            accomodationButtonDownTexture,
            lectureHallButtonDownTexture,
            libraryButtonDownTexture,
            courseButtonDownTexture,
            foodZoneButtonDownTexture,
            recreationalButtonDownTexture,
            natureButtonDownTexture
        };

        //Building textures
        accomodationPlacedTexture = new Texture("accomodationPlaced.png");
        accomodationCollisionTexture = new Texture("accomodationCollision.png");
        accomodationDraggingTexture = new Texture("accomodationDragging.png");

        lectureHallPlacedTexture = new Texture("lectureHallPlaced.png");
        lectureHallCollisionTexture = new Texture("lectureHallCollision.png");
        lectureHallDraggingTexture = new Texture("lectureHallDragging.png");

        libraryPlacedTexture = new Texture("libraryPlaced.png");
        libraryCollisionTexture = new Texture("libraryCollision.png");
        libraryDraggingTexture = new Texture("libraryDragging.png");

        coursePlacedTexture = new Texture("coursePlaced.png");
        courseCollisionTexture = new Texture("courseCollision.png");
        courseDraggingTexture = new Texture("courseDragging.png");
        
        foodZonePlacedTexture = new Texture("foodZonePlaced.png");
        foodZoneCollisionTexture = new Texture("foodZoneCollision.png");
        foodZoneDraggingTexture = new Texture("foodZoneDragging.png");

        recreationalPlacedTexture = new Texture("recreationalPlaced.png");
        recreationalCollisionTexture = new Texture("recreationalCollision.png");
        recreationalDraggingTexture = new Texture("recreationalDragging.png");

        naturePlacedTexture = new Texture("naturePlaced.png");
        natureCollisionTexture = new Texture("natureCollision.png");
        natureDraggingTexture = new Texture("natureDragging.png");
    }
}

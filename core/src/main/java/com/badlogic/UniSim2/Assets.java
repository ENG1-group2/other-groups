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

        // =======================================
        // SOUNDS EFFECTS AND MUSIC
        // =======================================
        click =     Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));
        gameStart = Gdx.audio.newSound(Gdx.files.internal("sounds/gameStart.mp3"));
        music =     Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.25f);

        // Start menu textures
        startBackgroundTexture =    new Texture("startBackground.png");
        startButtonUpTexture =      new Texture("startButtonUp.png");
        startButtonDownTexture =    new Texture("startButtonDown.png");

        // Background texture
        backgroundTexture = new Texture("background.png");
        pathTexture =       new Texture("path.png");

        menuBarTexture = new Texture("menuBar.png");

        // =======================================
        // BUTTON TEXTURES
        // =======================================
        // Building button textures when not hovering over
        accomodationButtonUpTexture =   new Texture("textures/buttons/accomodationButtonUp.png");
        lectureHallButtonUpTexture =    new Texture("textures/buttons/lectureHallButtonUp.png");
        libraryButtonUpTexture =        new Texture("textures/buttons/libraryButtonUp.png");
        courseButtonUpTexture =         new Texture("textures/buttons/courseButtonUp.png");
        foodZoneButtonUpTexture =       new Texture("textures/buttons/foodZoneButtonUp.png");
        recreationalButtonUpTexture =   new Texture("textures/buttons/recreationalButtonUp.png");
        natureButtonUpTexture =         new Texture("textures/buttons/natureButtonUp.png");
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
        accomodationButtonDownTexture = new Texture("textures/buttons/accomodationButtonDown.png");
        lectureHallButtonDownTexture =  new Texture("textures/buttons/lectureHallButtonDown.png");
        libraryButtonDownTexture =      new Texture("textures/buttons/libraryButtonDown.png");
        courseButtonDownTexture =       new Texture("textures/buttons/courseButtonDown.png");
        foodZoneButtonDownTexture =     new Texture("textures/buttons/foodZoneButtonDown.png");
        recreationalButtonDownTexture = new Texture("textures/buttons/recreationalButtonDown.png");
        natureButtonDownTexture =       new Texture("textures/buttons/natureButtonDown.png");
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

        // =======================================
        // BUILDING SPRITE TEXTURES
        // =======================================
        accomodationPlacedTexture =     new Texture("textures/buildings/accomodationPlaced.png");
        accomodationCollisionTexture =  new Texture("textures/buildings/accomodationCollision.png");
        accomodationDraggingTexture =   new Texture("textures/buildings/accomodationDragging.png");

        lectureHallPlacedTexture =      new Texture("textures/buildings/lectureHallPlaced.png");
        lectureHallCollisionTexture =   new Texture("textures/buildings/lectureHallCollision.png");
        lectureHallDraggingTexture =    new Texture("textures/buildings/lectureHallDragging.png");

        libraryPlacedTexture =          new Texture("textures/buildings/libraryPlaced.png");
        libraryCollisionTexture =       new Texture("textures/buildings/libraryCollision.png");
        libraryDraggingTexture =        new Texture("textures/buildings/libraryDragging.png");

        coursePlacedTexture =           new Texture("textures/buildings/coursePlaced.png");
        courseCollisionTexture =        new Texture("textures/buildings/courseCollision.png");
        courseDraggingTexture =         new Texture("textures/buildings/courseDragging.png");

        foodZonePlacedTexture =         new Texture("textures/buildings/foodZonePlaced.png");
        foodZoneCollisionTexture =      new Texture("textures/buildings/foodZoneCollision.png");
        foodZoneDraggingTexture =       new Texture("textures/buildings/foodZoneDragging.png");

        recreationalPlacedTexture =     new Texture("textures/buildings/recreationalPlaced.png");
        recreationalCollisionTexture =  new Texture("textures/buildings/recreationalCollision.png");
        recreationalDraggingTexture =   new Texture("textures/buildings/recreationalDragging.png");

        naturePlacedTexture =           new Texture("textures/buildings/naturePlaced.png");
        natureCollisionTexture =        new Texture("textures/buildings/natureCollision.png");
        natureDraggingTexture =         new Texture("textures/buildings/natureDragging.png");
    }
}

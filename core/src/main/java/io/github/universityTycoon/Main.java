package io.github.universityTycoon;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    PlayerInputHandler playerInputHandler = new PlayerInputHandler();
    GameModel gameModel = new GameModel(playerInputHandler);
    UILogic uiLogic = new UILogic();

    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
    @Override
    public void render() {

    }
}

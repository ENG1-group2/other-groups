package eng1.unisim.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import eng1.unisim.models.Player;
import eng1.unisim.models.Building;
import eng1.unisim.ui.*;

// manages all user interface elements and views in the game
public class UIManager {
    // ui components for different game states and information display
    private final HUDView hudView;
    private final BuildingInventoryView buildingInventoryView;
    private final NotificationView notificationView;
    private final PauseMenuView pauseMenuView;
    private final EndGameView endGameView;
    private final BitmapFont font;
    // main stage that holds most ui elements
    private final Stage mainStage;

    // callback interface for building selection events
    public interface BuildingSelectionCallback {
        void onBuildingSelected(Building building);
    }

    // initializes all ui components and sets up the main stage
    public UIManager(Player player, Runnable onRestartGame, BuildingSelectionCallback buildingCallback,
                     Runnable onPauseToggle) {
        font = new BitmapFont();
        font.getData().setScale(1.5f);

        mainStage = new Stage(new ScreenViewport());

        hudView = new HUDView(font, player);
        buildingInventoryView = new BuildingInventoryView(font, buildingCallback);
        notificationView = new NotificationView(font);
        pauseMenuView = new PauseMenuView(font, onPauseToggle);
        endGameView = new EndGameView(font, player, onRestartGame);

        // add permanent ui elements to the main stage
        mainStage.addActor(hudView);
        mainStage.addActor(buildingInventoryView);
        mainStage.addActor(notificationView);
    }

    // methods to control pause menu visibility
    public void showPauseMenu() {
        pauseMenuView.show();
    }

    public void hidePauseMenu() {
        pauseMenuView.hide();
    }

    // updates the building inventory display
    public void updateBuildingCount(String buildingType, int count) {
        buildingInventoryView.updateBuildingCount(buildingType, count);
    }

    // displays temporary notification messages
    public void showNotification(String message) {
        notificationView.showMessage(message);
    }

    // methods to control end game screen visibility
    public void showEndGameScreen() {
        endGameView.show();
    }

    public void hideEndGameScreen() {
        endGameView.hide();
    }

    // updates the heads-up display with current game time
    public void updateHUD(int timeLeft) {
        hudView.update(timeLeft);
    }

    // handles rendering of all ui components
    public void render() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        // update and draw main stage elements
        mainStage.act(delta);
        mainStage.draw();

        // render overlay menus separately
        pauseMenuView.render(delta);
        endGameView.render(delta);
    }

    // handles window resize events for all ui components
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
        pauseMenuView.resize(width, height);
        endGameView.resize(width, height);
    }

    // cleanup method to prevent memory leaks
    public void dispose() {
        mainStage.dispose();
        pauseMenuView.dispose();
        endGameView.dispose();
        font.dispose();
    }

    // getter methods for different stage access
    public Stage getStage() {
        return mainStage;
    }

    public Stage getPauseMenuStage() {
        return pauseMenuView.getStage();
    }

    public Stage getEndGameStage() {
        return endGameView.getStage();
    }
}

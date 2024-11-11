package eng1.unisim.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import eng1.unisim.models.Player;
import eng1.unisim.models.Building;
import eng1.unisim.ui.*;

public class UIManager {
    private final HUDView hudView;
    private final BuildingInventoryView buildingInventoryView;
    private final NotificationView notificationView;
    private final PauseMenuView pauseMenuView;
    private final EndGameView endGameView;
    private final BitmapFont font;
    private final Stage mainStage;

    public interface BuildingSelectionCallback {
        void onBuildingSelected(Building building);
    }

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

        // view get added to the main stage
        mainStage.addActor(hudView);
        mainStage.addActor(buildingInventoryView);
        mainStage.addActor(notificationView);
    }

    public void showPauseMenu() {
        pauseMenuView.show();
    }

    public void hidePauseMenu() {
        pauseMenuView.hide();
    }

    public void updateBuildingCount(String buildingType, int count) {
        buildingInventoryView.updateBuildingCount(buildingType, count);
    }

    public void showNotification(String message) {
        notificationView.showMessage(message);
    }

    public void showEndGameScreen() {
        endGameView.show();
    }

    public void hideEndGameScreen() {
        endGameView.hide();
    }

    public void updateHUD(int timeLeft) {
        hudView.update(timeLeft);
    }

    public void render() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        mainStage.act(delta);
        mainStage.draw();

        pauseMenuView.render(delta);
        endGameView.render(delta);
    }

    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
        pauseMenuView.resize(width, height);
        endGameView.resize(width, height);
    }

    public void dispose() {
        mainStage.dispose();
        pauseMenuView.dispose();
        endGameView.dispose();
        font.dispose();
    }

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

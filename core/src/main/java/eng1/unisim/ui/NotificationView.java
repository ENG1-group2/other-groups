package eng1.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * handles temporary popup notifications that appear at the top of the screen
 * extends table to organize ui elements in libgdx
 */
public class NotificationView extends Table {
    // the label that displays the notification text
    private final Label notificationLabel;
    private final Label placementLabel;
    // tracks how long the current notification should remain visible
    private float notificationTimer = 0;
    // how long notifications stay on screen (in seconds)
    private static final float NOTIFICATION_DURATION = 2f;

    /**
     * creates a new notification view
     * @param font the bitmap font used to display the text
     */
    public NotificationView(BitmapFont font) {
        // create notification label (red for errors/warnings)
        Label.LabelStyle errorStyle = new Label.LabelStyle(font, Color.RED);
        notificationLabel = new Label("", errorStyle);
        notificationLabel.setVisible(false);

        // create placement instruction label (white for information)
        Label.LabelStyle infoStyle = new Label.LabelStyle(font, Color.WHITE);
        placementLabel = new Label("Press ESC to cancel building placement", infoStyle);
        placementLabel.setVisible(false);

        // main container to take up full screen
        this.setFillParent(true);

        // separate containers for each type of message
        Table errorContainer = new Table();
        errorContainer.top();
        errorContainer.add(notificationLabel).padTop(50);

        Table infoContainer = new Table();
        infoContainer.top();
        infoContainer.add(placementLabel).padTop(100); // Position below error messages

        this.add(errorContainer).expandX().fillX().row();
        this.add(infoContainer).expandX().fillX();
    }

    /**
     * displays a new notification message
     * @param message the text to show in the notification
     */
    public void showMessage(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        notificationTimer = NOTIFICATION_DURATION;
    }

    public void showPlacementMessage() {
        placementLabel.setVisible(true);
    }

    public void hidePlacementMessage() {
        placementLabel.setVisible(false);
    }

    /**
     * updates the notification timer and hides expired notifications
     * called automatically by libgdx's scene2d
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (notificationTimer > 0) {
            notificationTimer -= delta;
            if (notificationTimer <= 0) {
                notificationLabel.setVisible(false);
            }
        }
    }
}

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
    // tracks how long the current notification should remain visible
    private float notificationTimer = 0;
    // how long notifications stay on screen (in seconds)
    private static final float NOTIFICATION_DURATION = 2f;

    /**
     * creates a new notification view
     * @param font the bitmap font used to display the text
     */
    public NotificationView(BitmapFont font) {
        // create a red label style for the notifications
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        notificationLabel = new Label("", style);
        notificationLabel.setVisible(false);

        // position the notification at the top of the screen with some padding
        this.setFillParent(true);
        this.top().padTop(50);
        this.add(notificationLabel);
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

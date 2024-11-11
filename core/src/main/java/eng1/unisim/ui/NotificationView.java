package eng1.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class NotificationView extends Table {
    private final Label notificationLabel;
    private float notificationTimer = 0;
    private static final float NOTIFICATION_DURATION = 2f;

    public NotificationView(BitmapFont font) {
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        notificationLabel = new Label("", style);
        notificationLabel.setVisible(false);

        this.setFillParent(true);
        this.top().padTop(50);
        this.add(notificationLabel);
    }

    public void showMessage(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        notificationTimer = NOTIFICATION_DURATION;
    }

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

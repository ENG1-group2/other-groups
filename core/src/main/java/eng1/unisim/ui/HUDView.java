package eng1.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import eng1.unisim.models.Player;

public class HUDView extends Table {
    private final Label timeLabel;
    private final Label satisfactionLabel;
    private final Label fundsLabel;
    private final Player player;

    public HUDView(BitmapFont font, Player player) {
        this.player = player;

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        timeLabel = new Label("Time: 0", labelStyle);
        satisfactionLabel = new Label("Satisfaction: " + player.getSatisfaction(), labelStyle);
        fundsLabel = new Label("Funds: $" + player.getFunds(), labelStyle);

        this.top().right();
        this.setFillParent(true);
        this.pad(10);
        this.add(timeLabel).padBottom(5).row();
        this.add(satisfactionLabel).padBottom(5).row();
        this.add(fundsLabel);
    }

    public void update(int timeLeft) {
        timeLabel.setText("Time Left: " + timeLeft);
        satisfactionLabel.setText("Satisfaction: " + player.getSatisfaction());
        fundsLabel.setText("Funds: $" + player.getFunds());
    }
}

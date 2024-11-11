package eng1.unisim.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import eng1.unisim.models.Player;

// displays game stats in the top-right corner of the screen
public class HUDView extends Table {
    // labels to show time, satisfaction and funds
    private final Label timeLabel;
    private final Label satisfactionLabel;
    private final Label fundsLabel;
    // reference to player to get latest stats
    private final Player player;

    // creates the hud with a given font and player reference
    public HUDView(BitmapFont font, Player player) {
        this.player = player;

        // set up the style for all labels with white text
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        
        // create labels with initial values
        timeLabel = new Label("Time: 0", labelStyle);
        satisfactionLabel = new Label("Satisfaction: " + player.getSatisfaction(), labelStyle);
        fundsLabel = new Label("Funds: $" + player.getFunds(), labelStyle);

        // position the table in top-right corner
        this.top().right();
        this.setFillParent(true);
        this.pad(10);
        
        // add labels vertically with some padding
        this.add(timeLabel).padBottom(5).row();
        this.add(satisfactionLabel).padBottom(5).row();
        this.add(fundsLabel);
    }

    // updates all labels with latest values
    public void update(int timeLeft) {
        timeLabel.setText("Time Left: " + timeLeft);
        satisfactionLabel.setText("Satisfaction: " + player.getSatisfaction());
        fundsLabel.setText("Funds: $" + player.getFunds());
    }
}

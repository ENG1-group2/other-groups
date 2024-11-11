package github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Renderer {

    final private OrthographicCamera camera;
    final private ShapeRenderer shapeRenderer;
    final private BitmapFont font;
    final private SpriteBatch spriteBatch;

    public Renderer(OrthographicCamera camera) {
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();

        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void update() {
        drawScreen();
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        spriteBatch.dispose();
    }

    public void drawRect(Vector2 position, Vector2 size, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.x, position.y, size.x, size.y);
        shapeRenderer.end();
    }

    public void drawText(String text, Vector2 position, Color color, float fontSize) {
        spriteBatch.begin();
        font.getData().setScale(fontSize);
        font.setColor(color);
        font.draw(spriteBatch, text, position.x, position.y);
        spriteBatch.end();
        font.getData().setScale(1.0f);
    }

    private void drawScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        shapeRenderer.end();
    }
}

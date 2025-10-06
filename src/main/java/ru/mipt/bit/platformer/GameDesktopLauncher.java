package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.entity.RenderBehavior;
import ru.mipt.bit.platformer.entity.RotateBehavior;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.Tree;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.ControlHandler;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private GameField gameField;
    private ControlHandler controlHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        gameField = new GameField(levelRenderer);

        
        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        Tank tank = new Tank(tankRenderBehavior, new MoveBehavior(MOVEMENT_SPEED, true, tileMovement, tankRenderBehavior));
        gameField.addMovableEntity(tank);
        gameField.addRenderableEntity(tank);

        RenderBehavior treeRenderBehavior = new RenderBehavior(new GridPoint2(1, 3), new TextureRegion(new Texture("images/greenTree.png")), groundLayer, new RotateBehavior(0f));
        gameField.addRenderableEntity(new Tree(treeRenderBehavior));

        controlHandler = new ControlHandler(tank, gameField);
    }

    @Override
    public void render() {
        clearScreen();

        controlHandler.handleControlInput();

        gameField.updateEntities(Gdx.graphics.getDeltaTime());

        gameField.renderLevel();

        batch.begin();

        gameField.renderEntities(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}

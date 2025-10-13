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
import ru.mipt.bit.platformer.entity.MovingRenderBehavior;
import ru.mipt.bit.platformer.entity.TankGraphics;
import ru.mipt.bit.platformer.entity.TankModel;
import ru.mipt.bit.platformer.entity.TreeGraphics;
import ru.mipt.bit.platformer.entity.TreeModel;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.ControlHandler;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private TiledMapTileLayer layer;

    private GameField gameField;
    private ControlHandler controlHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        layer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(layer, Interpolation.smooth);

        gameField = new GameField(createSingleLayerMapRenderer(level, batch));

        final float MOVEMENT_SPEED = 0.4f;
        GridPoint2 tankPosition = new GridPoint2(1, 1);
        MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, MOVEMENT_SPEED, 0f);
        TankModel tankModel = new TankModel(tankMoveBehavior);
        TankGraphics tankGraphics = new TankGraphics(new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement), tankModel);
        gameField.addMovableEntity(tankModel);
        gameField.addRenderableEntity(tankGraphics);

        RenderBehavior treeRenderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/greenTree.png")));
        gameField.addRenderableEntity(new TreeGraphics(treeRenderBehavior, new TreeModel(new GridPoint2(1, 3))));

        controlHandler = new ControlHandler(tankModel, gameField);
    }

    @Override
    public void render() {
        controlHandler.handleControlInput();

        gameField.moveEntities(Gdx.graphics.getDeltaTime());

        clearScreen();

        gameField.renderLevel();

        batch.begin();

        gameField.renderEntities(batch, layer);

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

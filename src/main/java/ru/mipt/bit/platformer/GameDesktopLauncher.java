package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.config.Config;
import ru.mipt.bit.platformer.entity.MapState;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.TankCommandGenerator;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static String levelFilePath = "./src/main/resources/level_design.txt";

    private Batch batch;

    private TiledMap level;
    private TiledMapTileLayer layer;
    private TileMovement tileMovement;

    private Mover mover;
    private Renderer renderer;
    private ControlHandler controlHandler;
    private AIControlHandler aiControlHandler;

    @Override
    public void create() {
        Config config = new Config();
        batch = new SpriteBatch();

        initLevelStructures();
        initHandlingOnTick();

        MapState mapState = new MapState(layer.getWidth(), layer.getHeight(), config);
        mapState.initGameObjects(renderer, mover, tileMovement);

        controlHandler = config.getControlHandler(mapState.getPlayerTank());
        aiControlHandler = new AIControlHandler(TankCommandGenerator.create(mapState.getAITanks()));
    }

    @Override
    public void render() {
        processActions();
        processRendering();
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

    private void processActions() {
        controlHandler.handle(Gdx.input);
        aiControlHandler.handle();
        mover.moveEntities(Gdx.graphics.getDeltaTime());
    }
    
    private void processRendering() {
        clearScreen();

        renderer.renderLevel();

        batch.begin();

        renderer.renderEntities(batch, layer);

        batch.end();
    }

    private void initLevelStructures() {
        level = new TmxMapLoader().load("level.tmx");
        layer = getSingleLayer(level);
        tileMovement = new TileMovement(layer, Interpolation.smooth);
    }

    private void initHandlingOnTick() {
        mover = new Mover();
        renderer = new Renderer(createSingleLayerMapRenderer(level, batch));
    }
}

package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;
import ru.mipt.bit.platformer.config.Config;
import ru.mipt.bit.platformer.entity.HealthBarDecorator;
import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.MapState;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.MoveEntityCommandGenerator;
import ru.mipt.bit.platformer.util.ObstacleType;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameDesktopLauncher implements ApplicationListener {
    private static String levelFilePath = "./src/main/resources/level_design.txt";

    final int TREES_COUNT = 0;
    final int AI_TANKS_COUNT = 3;

    private Batch batch;

    private TiledMap level;
    private TiledMapTileLayer layer;
    private TileMovement tileMovement;

    private Mover mover;
    private Renderer renderer;
    private MapState mapState;
    private ControlHandler controlHandler;
    private AIControlHandler aiControlHandler;

    private MovableEntity playerTank;

    @Override
    public void create() {
        batch = new SpriteBatch();

        generateLevel();
    
        mover = new Mover();
        renderer = new Renderer(createSingleLayerMapRenderer(level, batch));

        Map<ObstacleType, Integer> obstacleCounts = Map.of(
            ObstacleType.TREE, TREES_COUNT,
            ObstacleType.TANK, AI_TANKS_COUNT + 1   // plus player's tank
        );

        LevelGenerator levelGenerator = new LevelGeneratorRandom(layer.getWidth(), layer.getHeight(), obstacleCounts);
        Map<ObstacleType, Set<GridPoint2>> obstaclesUniquePositions;
        try {
            obstaclesUniquePositions = levelGenerator.getUniqueObstaclesPositions();
        } catch (IOException e) {
           System.out.printf("caught exception %s, returning...\n", e.getMessage());
           return;
        }

        mapState = new MapState(layer.getWidth(), layer.getHeight());
        
        for (GridPoint2 treePosition: obstaclesUniquePositions.get(ObstacleType.TREE)) {
            RenderBehavior treeRenderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/greenTree.png")));
            TreeModel treeModel = new TreeModel(treePosition);
            renderer.addRenderableEntity(new TreeGraphics(treeRenderBehavior, treeModel));
            mapState.addObstacle(ObstacleType.TREE, treeModel);
        }

        List<MovableEntity> tanks = new ArrayList<>();
        for (GridPoint2 tankPosition: obstaclesUniquePositions.get(ObstacleType.TANK)) {
            MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, mapState.getMovementSpeed(), 0f, mapState);
            TankModel tankModel = new TankModel(tankMoveBehavior);
            tanks.add(tankModel);
            mover.addMovableEntity(tankModel);
            mapState.addObstacle(ObstacleType.TANK, tankModel);

            MovingRenderBehavior tankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
            TankGraphics tankGraphics = new TankGraphics(tankMovingRenderBehavior, tankModel);
            final float INITIAL_HEALTH = 100.f;
            renderer.addRenderableEntity(new HealthBarDecorator(tankGraphics, new HealthBarModel(INITIAL_HEALTH)));
        }

        Config config = new Config();
        playerTank = tanks.get(0); // tanks.get(0) is player tank
        controlHandler = config.getControlHandler(playerTank);
        aiControlHandler = new AIControlHandler(MoveEntityCommandGenerator.create(tanks.subList(1, tanks.size()))); // without player tank
    }

    private void generateLevel() {
        level = new TmxMapLoader().load("level.tmx");
        layer = getSingleLayer(level);
        tileMovement = new TileMovement(layer, Interpolation.smooth);
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
}

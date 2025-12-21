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
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.config.Config;
import ru.mipt.bit.platformer.entity.HealthBarDecorator;
import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.MapModel;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.level.LevelGenerator.LevelData;
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.MoveEntityCommandGenerator;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;

    private TiledMap level;
    private TiledMapTileLayer layer;

    private Mover mover;
    private Renderer renderer;
    private ControlHandler controlHandler;
    private AIControlHandler aiControlHandler;

    private static String levelFilePath = "./src/main/resources/level_design.txt";

    @Override
    public void create() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        layer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(layer, Interpolation.smooth);
    
        mover = new Mover();
        renderer = new Renderer(createSingleLayerMapRenderer(level, batch));

        final int TREE_COUNT = 0;
        final int TANKS_COUNT = 3;

        LevelGenerator levelGenerator = new LevelGeneratorRandom(layer.getWidth(), layer.getHeight(), TREE_COUNT, TANKS_COUNT);
        // LevelGenerator levelGenerator = new LevelGeneratorFileBased(layer.getWidth(), layer.getHeight(), levelFilePath);
        LevelData levelObjectsPositions;
        List<MovableEntity> aiTanks = new ArrayList<>();
        try {
            levelObjectsPositions = levelGenerator.generateLevel();

            MapModel mapModel = new MapModel(layer.getWidth(), layer.getHeight());
            
            for (GridPoint2 treePosition: levelObjectsPositions.getTreePositions()) {
                RenderBehavior treeRenderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/greenTree.png")));
                TreeModel treeModel = new TreeModel(treePosition);
                renderer.addRenderableEntity(new TreeGraphics(treeRenderBehavior, treeModel));
                mapModel.addObstacle(treeModel);
            }

            MoveBehavior playerTankMoveBehavior = new MoveBehavior(new GridPoint2(1, 1), mapModel.getMovementSpeed(), 0f, mapModel);
            TankModel playerTank = new TankModel(playerTankMoveBehavior);
            mapModel.setMainTank(playerTank);
            mapModel.addObstacle(playerTank);
            mover.addMovableEntity(playerTank);
            MovingRenderBehavior playerTankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
            TankGraphics playerTankGraphics = new TankGraphics(playerTankMovingRenderBehavior, playerTank);
            renderer.addRenderableEntity(new HealthBarDecorator(playerTankGraphics, new HealthBarModel(100, true)));

            for (GridPoint2 tankPosition: levelObjectsPositions.getTankPositions()) {
                MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, mapModel.getMovementSpeed(), 0f, mapModel);
                TankModel tankModel = new TankModel(tankMoveBehavior);
                    aiTanks.add(tankModel);
                mover.addMovableEntity(tankModel);
                mapModel.addObstacle(tankModel);

                MovingRenderBehavior tankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
                TankGraphics tankGraphics = new TankGraphics(tankMovingRenderBehavior, tankModel);
                renderer.addRenderableEntity(new HealthBarDecorator(tankGraphics, new HealthBarModel(80, true)));
            }

            Config config = new Config();
            controlHandler = config.controlHandler(mapModel);
            aiControlHandler = new AIControlHandler(MoveEntityCommandGenerator.create(aiTanks));
        } catch (IOException e) {
           System.out.printf("caught exception %s, returning...\n", e.getMessage());
           return;
        }
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

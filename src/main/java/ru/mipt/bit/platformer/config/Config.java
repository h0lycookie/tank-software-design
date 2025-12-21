package ru.mipt.bit.platformer.config;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

import java.beans.BeanProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.command.MoveEntityCommand;
import ru.mipt.bit.platformer.entity.HealthBarDecorator;
import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.MapModel;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.entity.MovingRenderBehavior;
import ru.mipt.bit.platformer.entity.RenderBehavior;
import ru.mipt.bit.platformer.entity.TankGraphics;
import ru.mipt.bit.platformer.entity.TankModel;
import ru.mipt.bit.platformer.entity.TreeGraphics;
import ru.mipt.bit.platformer.entity.TreeModel;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.level.LevelGenerator.LevelData;
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.MoveEntityCommandGenerator;
import ru.mipt.bit.platformer.util.TileMovement;

public class Config {
    private Batch batch;

    private TiledMap level;
    private TiledMapTileLayer layer;

    private Mover mover;
    private Renderer renderer;
    private ControlHandler controlHandler;
    private AIControlHandler aiControlHandler;

    public Config() {
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

            MoveBehavior playerTankMoveBehavior = new MoveBehavior(new GridPoint2(1, 1), mapModel.getMovementSpeed(), 0f, mapModel);    // remove
            TankModel playerTank = new TankModel(playerTankMoveBehavior);
            mapModel.setMainTank(playerTank);
            mapModel.addObstacle(playerTank);
            mover.addMovableEntity(playerTank);
            MovingRenderBehavior playerTankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
            TankGraphics playerTankGraphics = new TankGraphics(playerTankMovingRenderBehavior, playerTank);
            renderer.addRenderableEntity(new HealthBarDecorator(playerTankGraphics, new HealthBarModel(100, true)), controlHandler);

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

            controlHandler = config.controlHandler();
            aiControlHandler = new AIControlHandler(MoveEntityCommandGenerator.create(aiTanks));
        } catch (IOException e) {
           System.out.printf("caught exception %s, returning...\n", e.getMessage());
           return;
        }
    }

    public ControlHandler controlHandler() {
        ControlHandler controlHandler = new ControlHandler();

        // Random rng = new Random();
        // MoveBehavior tankMoveBehavior = new MoveBehavior(new GridPoint2(rng.nextInt(mapModel.getWidth()), rng.nextInt(mapModel.getHeight())), mapModel.getMovementSpeed(), 0f, mapModel);
        Map<Direction, List<Integer>> controls = Map.of(
            Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
            Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
            Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
            Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
        
        );  // direction already contains keys, but it should not: what if we rebind keys?

        controls.forEach((direction, keys) ->
            controlHandler.addButtonAction(keys,
                    new MoveEntityCommand(*maintank*, direction), true));

        // controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), new ToggleHealthBarCommand(new HealthBarModel(true)), false);
        // controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), new ShotCommand(playerTank), false);
        
        return controlHandler;
    }
}

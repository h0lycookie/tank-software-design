package ru.mipt.bit.platformer.config;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

import java.beans.BeanProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import ru.mipt.bit.platformer.command.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.entity.HealthBarDecorator;
import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.MapState;
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
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.MoveEntityCommandGenerator;
import ru.mipt.bit.platformer.util.ObstacleType;
import ru.mipt.bit.platformer.util.TileMovement;

public class Config {
    // final int TREES_COUNT = 0;
    // final int AI_TANKS_COUNT = 3;

    // private Batch batch;

    // private TiledMap level;
    // private TiledMapTileLayer layer;
    // private TileMovement tileMovement;

    // private Mover mover;
    // private Renderer renderer;
    // private MapState mapState;
    // private ControlHandler controlHandler;
    // private AIControlHandler aiControlHandler;

    // private MovableEntity playerTank;

    // public Config() {
    //     batch = new SpriteBatch();

    //     generateLevel();
    
    //     mover = new Mover();
    //     renderer = new Renderer(createSingleLayerMapRenderer(level, batch));

    //     Map<ObstacleType, Integer> obstacleCounts = Map.of(
    //         ObstacleType.TREE, TREES_COUNT,
    //         ObstacleType.TANK, AI_TANKS_COUNT + 1   // plus player's tank
    //     );

    //     LevelGenerator levelGenerator = new LevelGeneratorRandom(layer.getWidth(), layer.getHeight(), obstacleCounts);
    //     Map<ObstacleType, Set<GridPoint2>> obstaclesUniquePositions;
    //     try {
    //         obstaclesUniquePositions = levelGenerator.getUniqueObstaclesPositions();
    //     } catch (IOException e) {
    //        System.out.printf("caught exception %s, returning...\n", e.getMessage());
    //        return;
    //     }

    //     mapState = new MapState(layer.getWidth(), layer.getHeight());
        
    //     for (GridPoint2 treePosition: obstaclesUniquePositions.get(ObstacleType.TREE)) {
    //         RenderBehavior treeRenderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/greenTree.png")));
    //         TreeModel treeModel = new TreeModel(treePosition);
    //         renderer.addRenderableEntity(new TreeGraphics(treeRenderBehavior, treeModel));
    //         mapState.addObstacle(ObstacleType.TREE, treeModel);
    //     }

    //     List<MovableEntity> tanks = new ArrayList<>();
    //     for (GridPoint2 tankPosition: obstaclesUniquePositions.get(ObstacleType.TANK)) {
    //         MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, mapState.getMovementSpeed(), 0f, mapState);
    //         TankModel tankModel = new TankModel(tankMoveBehavior);
    //         tanks.add(tankModel);
    //         mover.addMovableEntity(tankModel);
    //         mapState.addObstacle(ObstacleType.TANK, tankModel);

    //         MovingRenderBehavior tankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
    //         TankGraphics tankGraphics = new TankGraphics(tankMovingRenderBehavior, tankModel);
    //         renderer.addRenderableEntity(new HealthBarDecorator(tankGraphics, new HealthBarModel(80)));
    //     }

    //     playerTank = tanks.get(0); // tanks.get(0) is player tank   
    //     aiControlHandler = new AIControlHandler(MoveEntityCommandGenerator.create(tanks.subList(1, tanks.size()))); // tanks.get(0) is player tank
    // }

    // private void generateLevel() {
    //     level = new TmxMapLoader().load("level.tmx");
    //     layer = getSingleLayer(level);
    //     tileMovement = new TileMovement(layer, Interpolation.smooth);
    // }

    public ControlHandler getControlHandler(MovableEntity playerTank) {
        ControlHandler controlHandler = new ControlHandler();

        Map<Direction, List<Integer>> controls = Map.of(
            Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
            Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
            Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
            Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
        
        );

        controls.forEach((direction, keys) ->
            controlHandler.addButtonAction(keys,
                    new MoveEntityCommand(playerTank, direction), true));


        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), new ToggleHealthBarCommand(), false);
        // controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), new ShotCommand(playerTank), false);
        
        return controlHandler;
    }
}

package ru.mipt.bit.platformer.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.config.Config;
import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.ObjectType;
import ru.mipt.bit.platformer.util.TileMovement;

public class MapState {
    private int width;
    private int height;
    private Config config;
    
    Map<ObjectType, List<CollidableEntity>> obstaclesByType;
    MovableEntity playerTank;
    List<MovableEntity> aiTanks;

    public MapState(int width, int height, Config config) {
        this.width = width;
        this.height = height;
        this.config = config;
        this.obstaclesByType = new HashMap<>();
    }

    public void initGameObjects(Renderer renderer, Mover mover, TileMovement tileMovement) {
        Map<ObjectType, Set<GridPoint2>> objectsUniquePositions = getObjectsUniquePositions(config);

        initTrees(renderer, objectsUniquePositions);
        initTanks(renderer, mover, tileMovement, objectsUniquePositions);
    }

    public boolean isPositionTaken(GridPoint2 position) {
        for (List<CollidableEntity> obstaclesOfType: obstaclesByType.values()) {
            if (obstaclesOfType.stream().anyMatch(obstacle -> obstacle.getCollisionPositions().contains(position))) {
                return true;
            }
        }
        
        return false;
    }

    public boolean isOutOfBounds(GridPoint2 position) {
        return !(position.x >= 0 && position.x < width &&
                 position.y >= 0 && position.y < height);
    }

    public MovableEntity getPlayerTank() {
        return playerTank;
    }

    public List<MovableEntity> getAITanks() {
        return aiTanks;
    }

    private void addObject(ObjectType type, CollidableEntity obstacle) {
        List<CollidableEntity> obstacles = getObjects(type);
        if (obstacles == null) {
            obstacles = new ArrayList<>();
            obstaclesByType.put(type, obstacles);
        }
        obstacles.add(obstacle);
    }

    private List<CollidableEntity> getObjects(ObjectType type) {
        return obstaclesByType.get(type);
    }

    private Map<ObjectType, Set<GridPoint2>> getObjectsUniquePositions(Config config) {
        LevelGenerator levelGenerator = new LevelGeneratorRandom(width, height, config.getObstaclesCount());
        Map<ObjectType, Set<GridPoint2>> obstaclesUniquePositions = null;
        try {
            obstaclesUniquePositions = levelGenerator.getUniqueObjectsPositions();
        } catch (IOException e) {
           System.out.printf("caught exception %s, returning...\n", e.getMessage());
        }

        return obstaclesUniquePositions;
    }

    private void initTrees(Renderer renderer, Map<ObjectType, Set<GridPoint2>> objectsUniquePositions) {
        for (GridPoint2 treePosition: objectsUniquePositions.get(ObjectType.TREE)) {
            RenderBehavior treeRenderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/greenTree.png")));
            TreeModel treeModel = new TreeModel(treePosition);
            renderer.addRenderableEntity(new TreeGraphics(treeRenderBehavior, treeModel));
            addObject(ObjectType.TREE, treeModel);
        }
    }

    private void initTanks(Renderer renderer, Mover mover, TileMovement tileMovement, Map<ObjectType, Set<GridPoint2>> objectsUniquePositions) {
        List<MovableEntity> tanks = new ArrayList<>();
        for (GridPoint2 tankPosition: objectsUniquePositions.get(ObjectType.TANK)) {
            MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, config.getMovementSpeed(), 0f, this);
            TankModel tankModel = new TankModel(tankMoveBehavior);
            tanks.add(tankModel);
            mover.addMovableEntity(tankModel);
            addObject(ObjectType.TANK, tankModel);

            MovingRenderBehavior tankMovingRenderBehavior = new MovingRenderBehavior(new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
            TankGraphics tankGraphics = new TankGraphics(tankMovingRenderBehavior, tankModel);
            renderer.addRenderableEntity(new HealthBarDecorator(tankGraphics, new HealthBarModel(config.getTankInitialHealth())));
        }

        playerTank = tanks.get(0);
        aiTanks = tanks.subList(1, tanks.size());
    }
}

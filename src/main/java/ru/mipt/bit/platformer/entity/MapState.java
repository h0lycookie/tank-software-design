package ru.mipt.bit.platformer.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableShootsEntity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.level.LevelGenerator;
import ru.mipt.bit.platformer.level.LevelGeneratorRandom;
import ru.mipt.bit.platformer.util.ObjectType;
import ru.mipt.bit.platformer.util.TileMovement;

@Component
public class MapState {
    private int width;
    private int height;
    
    @Autowired
    private GameConfig gameConfig;
    
    Map<ObjectType, Set<CollidableEntity>> objectsByType;
    MovableShootsEntity playerTank;
    Set<TankModel> aiTanks;

    public MapState() {
        this.objectsByType = new ConcurrentHashMap<>();
        this.aiTanks = new HashSet<>();
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void initGameObjects(Renderer renderer, Mover mover, TileMovement tileMovement) {
        Observer observer = new MortalObserver(renderer, mover, tileMovement);

        Map<ObjectType, Set<GridPoint2>> objectsUniquePositions = getObjectsUniquePositions();

        initTrees(renderer, objectsUniquePositions);
        initTanks(renderer, mover, tileMovement, objectsUniquePositions, observer);
    }
    
    public boolean isPositionTaken(GridPoint2 position) {
        for (Set<CollidableEntity> objectsOfType: objectsByType.values()) {
            if (objectsOfType.stream().anyMatch(object -> object.getCollisionPositions().contains(position))) {
                return true;
            }
        }
        return false;
    }
    
    public CollidableEntity positionTakenBy(GridPoint2 position) {
        for (Set<CollidableEntity> objectsOfType: objectsByType.values()) {
            for (CollidableEntity object: objectsOfType) {
                if (object.getPosition().equals(position)) {
                    return object;
                }
            }
        }
        return null;
    }

    public void removeEntity(CollidableEntity entity) {
        for (Set<CollidableEntity> objectsOfType: objectsByType.values()) {
            if (objectsOfType.remove(entity)) {
                return;
            }
        }
    }

    public boolean isOutOfBounds(GridPoint2 position) {
        return !(position.x >= 0 && position.x < width &&
                 position.y >= 0 && position.y < height);
    }

    public MovableShootsEntity getPlayerTank() {
        return playerTank;
    }

    public Set<TankModel> getAITanks() {
        return aiTanks;
    }

    public void addObject(ObjectType type, CollidableEntity object) {
        Set<CollidableEntity> objects = getObjects(type);
        if (objects == null) {
            objects = new HashSet<>();
            objectsByType.put(type, objects);
        }
        objects.add(object);
    }

    public void removeObject(ObjectType type, CollidableEntity object) {
        Set<CollidableEntity> objects = getObjects(type);
        if (objects == null) {
            return;
        }
        objects.remove(object);
    }

    private Set<CollidableEntity> getObjects(ObjectType type) {
        return objectsByType.get(type);
    }
    
    private Map<ObjectType, Set<GridPoint2>> getObjectsUniquePositions() {
        Map<ObjectType, Integer> obstaclesCount = Map.of(
            ObjectType.TREE, gameConfig.getTreesCount(),
            ObjectType.TANK, gameConfig.getAiTanksCount() + 1
        );
        
        LevelGenerator levelGenerator = new LevelGeneratorRandom(width, height, obstaclesCount);
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
            RenderBehavior treeRenderBehavior = new RenderBehavior(
                new TextureRegion(new Texture("images/greenTree.png")));
            TreeModel treeModel = new TreeModel(treePosition);
            renderer.addRenderableEntity(new TreeGraphics(treeRenderBehavior, treeModel));
            addObject(ObjectType.TREE, treeModel);
        }
    }

    private void initTanks(Renderer renderer, Mover mover, TileMovement tileMovement, 
                          Map<ObjectType, Set<GridPoint2>> objectsUniquePositions, Observer observer) {
        List<TankModel> tanks = new ArrayList<>();
        for (GridPoint2 tankPosition: objectsUniquePositions.get(ObjectType.TANK)) {
            MoveBehavior tankMoveBehavior = new MoveBehavior(tankPosition, 
                gameConfig.getTankMovementSpeed(), 0f, this);
            TankModel tankModel = new TankModel(tankMoveBehavior, gameConfig.getInitialHealth());
            tankModel.setObserver(observer);
            tanks.add(tankModel);
            mover.addMovableEntity(tankModel);
            addObject(ObjectType.TANK, tankModel);

            MovingRenderBehavior tankMovingRenderBehavior = new MovingRenderBehavior(
                new RenderBehavior(new TextureRegion(new Texture("images/tank_blue.png"))), tileMovement);
            TankGraphics tankGraphics = new TankGraphics(tankMovingRenderBehavior, tankModel);
            renderer.addRenderableEntity(new HealthBarDecorator(tankGraphics));
        }

        playerTank = tanks.get(0);
        aiTanks.addAll(tanks.subList(1, tanks.size()));
    }
}

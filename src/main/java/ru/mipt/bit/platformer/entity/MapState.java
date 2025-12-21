package ru.mipt.bit.platformer.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.util.ObstacleType;

public class MapState {
    private final float MOVEMENT_SPEED = 0.4f;

    private int width;
    private int height;
    
    Map<ObstacleType, List<CollidableEntity>> obstaclesByType;

    public MapState(int width, int height) {
        this.width = width;
        this.height = height;
        this.obstaclesByType = new HashMap<>();
    }

    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addObstacle(ObstacleType type, CollidableEntity obstacle) {
        List<CollidableEntity> obstacles = getObstacles(type);
        if (obstacles == null) {
            obstacles = new ArrayList<>();
            obstaclesByType.put(type, obstacles);
        }
        obstacles.add(obstacle);
    }

    public List<CollidableEntity> getObstacles(ObstacleType type) {
        return obstaclesByType.get(type);
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
}

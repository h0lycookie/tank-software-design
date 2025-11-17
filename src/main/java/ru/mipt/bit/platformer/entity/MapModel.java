package ru.mipt.bit.platformer.entity;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.Entity;

public class MapModel {
    private Collection<Entity> obstacles;
    int width;
    int height;

    public MapModel(int width, int height) {
        this.obstacles = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    public void addObstacle(Entity obstacle) {
        obstacles.add(obstacle);
    }

    public boolean isPositionTaken(GridPoint2 position) {
        return obstacles.stream().anyMatch(entity -> position.equals(entity.getPosition()));
    }

    public boolean isOutOfBounds(GridPoint2 position) {
        return !(position.x >= 0 && position.x < width &&
                 position.y >= 0 && position.y < height);
    }
}

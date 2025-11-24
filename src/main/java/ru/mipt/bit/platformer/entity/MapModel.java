package ru.mipt.bit.platformer.entity;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.Entity;

@Component
public class MapModel {
    private final float MOVEMENT_SPEED = 0.4f;

    private Collection<Entity> obstacles = new ArrayList<>();
    private int width;
    private int height;

    public MapModel(int width, int height) {
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

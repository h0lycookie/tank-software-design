package ru.mipt.bit.platformer.entity;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;

public class MapModel {
    private final float MOVEMENT_SPEED = 0.4f;

    private Collection<CollidableEntity> obstacles = new ArrayList<>();
    private int width;
    private int height;

    TankModel mainTank;

    public MapModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setMainTank(TankModel tank) {
        mainTank = tank;
    }
    public TankModel getMainTank() {
        return mainTank;
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

    public void addObstacle(CollidableEntity obstacle) {
        obstacles.add(obstacle);
    }

    public boolean isPositionTaken(GridPoint2 position) {
        return obstacles.stream().anyMatch(entity -> entity.getCollisionPositions().contains(position));
    }

    public boolean isOutOfBounds(GridPoint2 position) {
        return !(position.x >= 0 && position.x < width &&
                 position.y >= 0 && position.y < height);
    }
}

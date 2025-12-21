package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.util.Direction;

public class TankModel implements MovableEntity, CollidableEntity {
    private static final float INITIAL_COOLDOWN = 1.0f;

    private MoveBehavior moveBehavior;
    private float cooldown;
    private Observer observer;

    public TankModel(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    @Override
    public void move(float deltaTime) {
        moveBehavior.move(deltaTime);
    }

    @Override
    public void prepareMovement(Direction direction) {
        moveBehavior.prepareMovement(direction);
    }

    @Override
    public GridPoint2 getPosition() {
        return moveBehavior.getPosition();
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        return moveBehavior.getCollisionPositions();
    }

    public GridPoint2 getDestinationPosition() {
        return moveBehavior.getDestinationPosition();
    }

    public float getRotation() {
    return moveBehavior.getRotation();
    }

    public float getMovementProgress() {
        return moveBehavior.getMovementProgress();
    }
}

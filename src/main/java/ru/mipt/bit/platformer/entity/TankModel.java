package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.Direction;

public class TankModel implements MovableEntity {
    private MoveBehavior moveBehavior;

    public TankModel(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    @Override
    public void move(float deltaTime) {
        moveBehavior.move(deltaTime);
    }

    @Override
    public void prepareMovement(Direction direction, Renderer renderer) {
        moveBehavior.prepareMovement(direction, renderer);
    }

    @Override
    public GridPoint2 getPosition() {
        return moveBehavior.getPosition();
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

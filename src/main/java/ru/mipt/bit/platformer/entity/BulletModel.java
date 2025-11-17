package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.util.Direction;

public class BulletModel implements MovableEntity {
    private MoveBehavior moveBehavior;

    public BulletModel(MoveBehavior moveBehavior) {
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
}

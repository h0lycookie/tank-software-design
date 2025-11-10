package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.Direction;

public class HealthBarModel implements MovableEntity {
    private MoveBehavior moveBehavior;
    private boolean visible;

    public HealthBarModel(MoveBehavior moveBehavior, boolean visible) {
        this.moveBehavior = moveBehavior;
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    public void switchVisibility() {
        visible = !visible;
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

    @Override
    public float getHealth() {
        return 100;
    }

    @Override
    public GridPoint2 getDestinationPosition() {
        
    }
        healthBarModel.getRotation(), healthBarModel.getMovementProgress()
}
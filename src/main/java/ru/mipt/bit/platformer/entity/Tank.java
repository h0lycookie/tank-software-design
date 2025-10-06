package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.Direction;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Tank implements MovableEntity, RenderableEntity {
    private RenderBehavior renderBehavior;
    private MoveBehavior moveBehavior;

    public Tank(RenderBehavior renderBehavior, MoveBehavior moveBehavior) {
        this.renderBehavior = renderBehavior;
        this.moveBehavior = moveBehavior;
    }

    @Override
    public void render(Batch batch) {
        renderBehavior.render(batch);
    }

    @Override
    public GridPoint2 getPosition() {
        return renderBehavior.getPosition();
    }

    @Override
    public boolean hasMoved() {
        return moveBehavior.hasMoved();
    }

    @Override
    public void move(float deltaTime) {
        moveBehavior.move(deltaTime);
    }

    @Override
    public void prepareMovement(Direction direction, GameField gameField) {
        moveBehavior.prepareMovement(direction, gameField);
    }
}
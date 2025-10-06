package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.Direction;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Tank implements MovableEntity, RenderableEntity {
    private RenderBehavior renderBehavior;
    private UpdateBehavior updateBehavior;

    public Tank(RenderBehavior renderBehavior, UpdateBehavior updateBehavior) {
        this.renderBehavior = renderBehavior;
        this.updateBehavior = updateBehavior;
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
        return updateBehavior.hasMoved();
    }

    @Override
    public void move(float deltaTime) {
        updateBehavior.move(deltaTime);
    }

    @Override
    public void prepareMovement(Direction direction, GameField gameField) {
        updateBehavior.prepareMovement(direction, gameField);
    }
}
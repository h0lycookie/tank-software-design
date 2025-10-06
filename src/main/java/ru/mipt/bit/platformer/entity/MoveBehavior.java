package ru.mipt.bit.platformer.entity;

import static com.badlogic.gdx.math.MathUtils.isEqual;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

public class MoveBehavior {
    private RenderBehavior renderBehavior;

    private GridPoint2 destinationPosition;
    private float movementSpeed;
    private float movementProgress = 1f;

    private boolean isFocused = false;

    private TileMovement tileMovement;

    public MoveBehavior(float movementSpeed, boolean isFocused, TileMovement tileMovement, RenderBehavior renderBehavior) {
        this.movementSpeed = movementSpeed;
        this.isFocused = isFocused;
        this.tileMovement = tileMovement;
        this.renderBehavior = renderBehavior;
        this.destinationPosition = renderBehavior.getPosition();
    }

    public void move(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isFocused) {
            tileMovement.moveRectangleBetweenTileCenters(renderBehavior.getRectangle(), renderBehavior.getPosition(), destinationPosition, movementProgress);
        }

        if (isEqual(movementProgress, 1f)) {
            renderBehavior.setPosition(destinationPosition);
        }
    }

    public boolean hasMoved() {
        return isEqual(movementProgress, 1f);
    }
    
    public void prepareMovement(Direction direction, GameField gameField) {
        renderBehavior.setRotation(direction.getRotation());
        GridPoint2 newPosition = direction.getNewPosition(renderBehavior.getPosition());
        if (!gameField.isPositionTaken(newPosition)) {
            destinationPosition.set(newPosition);
            movementProgress = 0f;
        }
    }

    public float getRotation() {
        return renderBehavior.getRotation();
    }

    public GridPoint2 getPosition() {
        return renderBehavior.getPosition();
    }

    public void setDestinationPosition(GridPoint2 desinationPosition) {
        this.destinationPosition = desinationPosition;
    }

    public GridPoint2 getDestinationPosition() {
        return destinationPosition.cpy();
    }
}

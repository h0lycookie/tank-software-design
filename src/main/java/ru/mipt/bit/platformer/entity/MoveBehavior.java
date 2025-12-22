package ru.mipt.bit.platformer.entity;

import static com.badlogic.gdx.math.MathUtils.isEqual;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class MoveBehavior {
    private static final float MAX_MOVEMENT_PROGRESS = 1f;
    private static final float MIN_MOVEMENT_PROGRESS = 0f;

    private GridPoint2 position;
    private GridPoint2 destinationPosition;
    private float rotation;
    private float movementSpeed;
    private float movementProgress = MAX_MOVEMENT_PROGRESS;
    private final MapState mapState; 

    public MoveBehavior(GridPoint2 position, float movementSpeed, float rotation, MapState mapState) {
        this.position = position.cpy();
        this.destinationPosition = position.cpy();
        this.rotation = rotation;
        this.movementSpeed = movementSpeed;
        this.mapState = mapState;
    }

    public void move(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (finishedMoving()) {
            position.set(destinationPosition);
        }
    }   
    
    public void prepareMovement(Direction direction) {
        if (finishedMoving()) {
            GridPoint2 newPosition = direction.getNewPosition(position);
            rotation = direction.getRotation();
            if (!mapState.isPositionTaken(newPosition) && !mapState.isOutOfBounds(newPosition)) {
                destinationPosition.set(newPosition);
                movementProgress = MIN_MOVEMENT_PROGRESS;
            }
        }
    }

    public void setDestinationPosition(GridPoint2 desinationPosition) {
        this.destinationPosition = desinationPosition.cpy();
    }

    public GridPoint2 getDestinationPosition() {
        return destinationPosition.cpy();
    }

    public GridPoint2 getPosition() {
        return position.cpy();
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public MapState getMapState() {
        return mapState;
    }

    public Set<GridPoint2> getCollisionPositions() {
        // Set.of throws if contains duplicates
        return (getPosition().equals(getDestinationPosition())) ? Set.of(getPosition()) : Set.of(getPosition(), getDestinationPosition());
    }

    public boolean isMoving() {
        return !finishedMoving();
    }

    private boolean finishedMoving() {
        return isEqual(movementProgress, MAX_MOVEMENT_PROGRESS);
    }
}

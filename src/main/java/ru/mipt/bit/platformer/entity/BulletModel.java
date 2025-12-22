package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.ObservableEntity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.GdxGameUtils;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class BulletModel implements MovableEntity, CollidableEntity, ObservableEntity {
    private static final float MAX_MOVEMENT_PROGRESS = 1f;
    private static final float MIN_MOVEMENT_PROGRESS = 0f;
    private static final float BULLET_DAMAGE = 25;

    private GridPoint2 position;
    private GridPoint2 destinationPosition;
    private float rotation;
    private Direction direction;
    private float movementSpeed;
    private float movementProgress = MAX_MOVEMENT_PROGRESS;
    private final MapState mapState; 
    private Observer observer;

    public BulletModel(GridPoint2 position, float movementSpeed, float rotation, MapState mapState) {
        this.position = position.cpy();
        this.destinationPosition = position.cpy();
        this.rotation = rotation;
        this.direction = Direction.getDirection(rotation);
        this.movementSpeed = movementSpeed;
        this.mapState = mapState;
    }


    @Override
    public void move(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (finishedMoving()) {
            position.set(destinationPosition);
            prepareMovement(direction);
        }
    }   
    
    @Override
    public void prepareMovement(Direction direction) {
        if (finishedMoving()) {
            GridPoint2 newPosition = this.direction.getNewPosition(position);
            if (!mapState.isPositionTaken(newPosition)) {
                destinationPosition.set(newPosition);
                movementProgress = MIN_MOVEMENT_PROGRESS;
            } else {
                handleCollision(newPosition);
            }
        }
    }

    @Override
    public GridPoint2 getPosition() {
        return position.cpy();
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        // Set.of throws if contains duplicates
        return (getPosition().equals(getDestinationPosition())) ? Set.of(getPosition()) : Set.of(getPosition(), getDestinationPosition());
    }

    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public void setDestinationPosition(GridPoint2 desinationPosition) {
        this.destinationPosition = desinationPosition.cpy();
    }

    public GridPoint2 getDestinationPosition() {
        return destinationPosition.cpy();
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

    public boolean isMoving() {
        return !finishedMoving();
    }

    private boolean finishedMoving() {
        return isEqual(movementProgress, MAX_MOVEMENT_PROGRESS);
    }
    
    public void destroy() {
        if (observer != null) {
            observer.onObjectDiscarded(this);
        }
        mapState.removeEntity(this);
    }

    private void handleCollision(GridPoint2 position) {
        destroy();
        CollidableEntity conflictEntity = mapState.positionTakenBy(position);
        if (conflictEntity != null) {
            if (conflictEntity instanceof BulletModel) {
                ((BulletModel) conflictEntity).destroy();
            } else if (conflictEntity instanceof HealthBarDecorator) {
                ((HealthBarDecorator) conflictEntity).damage(BULLET_DAMAGE);
            }
        }
    }
}

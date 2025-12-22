package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableShootsEntity;
import ru.mipt.bit.platformer.entity.interfaces.ObservableEntity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.ObjectType;

public class TankModel implements MovableShootsEntity, CollidableEntity, ObservableEntity {
    private static final float INITIAL_COOLDOWN = 1.0f;
    private float cooldown = INITIAL_COOLDOWN;

    private MoveBehavior moveBehavior;
    private BulletObserver observer;

    public TankModel(MoveBehavior moveBehavior) {
        this.moveBehavior = moveBehavior;
    }

    @Override
    public void move(float deltaTime) {
        updateCooldown(deltaTime);
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

    @Override
    public void shoot() {
        if (cooldown > 0) {
            return;
        }

        cooldown = INITIAL_COOLDOWN;
        Direction bulletDirection = Direction.getDirection(getRotation());
        GridPoint2 bulletCoordinates = moveBehavior.isMoving()
                ? new GridPoint2(getDestinationPosition())
                : new GridPoint2(getPosition()).add(bulletDirection.getDirectionVector());

        MapState mapState = moveBehavior.getMapState();
        BulletModel bullet = new BulletModel(bulletCoordinates, 0.8f, getRotation(), mapState);
        mapState.addObject(ObjectType.BULLET, bullet);
        observer.onObjectRegistered(bullet);
    }

    @Override
    public void setObserver(Observer observer) {
        if (observer instanceof BulletObserver) {
            this.observer = (BulletObserver) observer;
        } else {
            System.out.print("observer is not BulletObserver");
        }
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

    private void updateCooldown(float deltaTime) {
        cooldown -= deltaTime;
    }

    // private 

    private void destroy() {
        if (observer != null) {
            observer.onObjectDiscarded(this);
        }
        moveBehavior.getMapState().removeEntity(this);
    }
}

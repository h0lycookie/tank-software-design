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
    private static final float BULLET_MOVEMENT_SPEED = 0.3f;
    private static final float INITIAL_HEALTH = 100f;
    private float cooldown = INITIAL_COOLDOWN;
    private float health = INITIAL_HEALTH;

    private final MoveBehavior moveBehavior;
    private BulletObserver observer;

    public TankModel(MoveBehavior moveBehavior, float health) {
        this.moveBehavior = moveBehavior;
        this.health = health;
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
        BulletModel bullet = new BulletModel(bulletCoordinates, BULLET_MOVEMENT_SPEED, getRotation(), mapState);
        bullet.setObserver(observer);
        mapState.addObject(ObjectType.BULLET, bullet);
        observer.onObjectRegistered(bullet);
    }

    @Override
    public void setObserver(Observer observer) {
        if (observer instanceof BulletObserver bulletObserver) {
            this.observer = bulletObserver;
        } else {
            System.out.print("observer is not BulletObserver");
        }
    }

    @Override
    public float getHealth() {
        return health;
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

    public void receiveDamage(float damage) {;
        health = Math.max(0, health - damage);
        if (health <= 0) {
            destroy();
        }
    }

    private void updateCooldown(float deltaTime) {
        cooldown -= deltaTime;
    }

    private void destroy() {
        if (observer != null) {
            observer.onObjectDiscarded(this);
        }
        moveBehavior.getMapState().removeEntity(this);
    }
}

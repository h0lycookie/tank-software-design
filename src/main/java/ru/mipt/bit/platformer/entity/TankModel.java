package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.HealthableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.entity.interfaces.Shoots;
import ru.mipt.bit.platformer.util.Direction;

public class TankModel implements MovableEntity, HealthableEntity, Shoots {
    private static final float INITIAL_COOLDOWN = 1.0f;

    private MoveBehavior moveBehavior;
    private float health;
    private float cooldown;
    private Observer observer;

    public TankModel(MoveBehavior moveBehavior) {
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

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void shoot() {
        if (cooldown > 0) return;

        cooldown = INITIAL_COOLDOWN;
        Direction bulletDirection = Direction.getDirection(moveBehavior.getRotation());
        GridPoint2 bulletCoordinates = (moveBehavior.getMovementProgress() < 1f)
                ? new GridPoint2(moveBehavior.getDestinationPosition())
                : new GridPoint2(moveBehavior.getPosition()).add(bulletDirection.getDirectionVector());

        BulletModel bullet = new BulletModel(bulletCoordinates, bulletDirection, map);
        map.addBullet(bullet);
        observer.objectAppeared(bullet, "bullet");
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

    public void hit(int damage) {
        health = Math.max(0, health - damage);
        if (health <= 0) destroy();
    }

    private void destroy() {
        if (observer != null) {
            observer.objectDestroyed(this, "tank");
        }
        map.removeTank(this);
    }
}

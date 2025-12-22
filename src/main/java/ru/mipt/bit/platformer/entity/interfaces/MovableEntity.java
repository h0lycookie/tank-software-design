package ru.mipt.bit.platformer.entity.interfaces;

import ru.mipt.bit.platformer.util.Direction;

public interface MovableEntity extends PositionableEntity, HealthableEntity {
    public void move(float deltaTime);
    public void prepareMovement(Direction direction);
}

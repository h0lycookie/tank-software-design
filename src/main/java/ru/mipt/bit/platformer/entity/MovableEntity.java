package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.util.Direction;

public interface MovableEntity {
    public void move(float deltaTime);

    public boolean hasMoved();

    public void prepareMovement(Direction direction, GameField gameField);
}

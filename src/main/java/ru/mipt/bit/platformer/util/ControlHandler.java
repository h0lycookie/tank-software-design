package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.GameField;

public class ControlHandler {
    private MovableEntity focused;
    private final GameField gameField;

    public ControlHandler(MovableEntity focused, GameField gameField) {
        this.focused = focused;
        this.gameField = gameField;
    }

    public void handleControlInput() {
        for (Direction direction: Direction.values()) {
            if (direction.isKeyPressed()) {
                focused.prepareMovement(direction, gameField);
            }
        }
    }
}
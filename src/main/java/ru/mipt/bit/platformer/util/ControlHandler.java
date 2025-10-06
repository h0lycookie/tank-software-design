package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.entity.MovableEntity;
import ru.mipt.bit.platformer.field.GameField;

public class ControlHandler {
    private MovableEntity focused;
    private final GameField gameField;

    public ControlHandler(Tank focused, GameField gameField) {
        this.focused = focused;
        this.gameField = gameField;
    }

    public void handleControlInput() {
        for (Direction direction: Direction.values()) {
            if (direction.isKeyPressed() && focused.hasMoved()) {
                focused.prepareMovement(direction, gameField);
            }
        }
    }
}
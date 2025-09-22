package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.field.GameField;

public class ControlHandler {
    private Tank focused;
    private final GameField gameField;

    public ControlHandler(Tank focused, GameField gameField) {
        this.focused = focused;
        focused.setFocused(true);
        this.gameField = gameField;
    }

    public void handleControlInput() {
        for (Direction direction: Direction.values()) {
            if (direction.isKeyPressed() && focused.hasMoved()) {
                focused.setRotation(direction.getRotation());
                GridPoint2 newPosition = direction.getNewPosition(focused.getPosition());
                if (!gameField.isPositionTaken(newPosition)) {
                    focused.setDestinationPosition(newPosition, direction);
                }
            }
        }
    }

    public void setFocused(Tank focused) {
        this.focused = focused;
    }
}
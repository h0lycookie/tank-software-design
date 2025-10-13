package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.field.GameField;
import ru.mipt.bit.platformer.field.Renderer;

public class ControlHandler {
    private MovableEntity focused;
    private final Renderer renderer;

    public ControlHandler(MovableEntity focused, Renderer renderer) {
        this.focused = focused;
        this.renderer = renderer;
    }

    public void handleControlInput() {
        for (Direction direction: Direction.values()) {
            if (direction.isKeyPressed()) {
                focused.prepareMovement(direction, renderer);
            }
        }
    }
}
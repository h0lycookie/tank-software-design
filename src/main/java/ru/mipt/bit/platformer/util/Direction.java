package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f),
    DOWN(new GridPoint2(0, -1), -90f),
    LEFT(new GridPoint2(-1, 0), -180f),
    RIGHT(new GridPoint2(1, 0), 0f);

    private final GridPoint2 direction;
    private final float rotation;

    Direction(GridPoint2 direction, float rotation) {
        this.direction = direction;
        this.rotation = rotation;
    }

    public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
        return currentPosition.cpy().add((int) direction.x, (int) direction.y);
    }

    public float getRotation() {
        return rotation;
    }

    public static Direction getDirection(float rotation) {
        for (Direction direction: values()) {
            if (Math.abs(direction.getRotation() - rotation) < 1e-5) {
                return direction;
            }
        }
        return null;
    }

    public GridPoint2 getDirectionVector() {
        return new GridPoint2(direction);
    }
}

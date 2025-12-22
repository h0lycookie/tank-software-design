package ru.mipt.bit.platformer.util;

import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP(new Vector2(0, 1), 90f),
    DOWN(new Vector2(0, -1), -90f),
    LEFT(new Vector2(-1, 0), -180f),
    RIGHT(new Vector2(1, 0), 0f);

    private final Vector2 direction;
    private final float rotation;

    Direction(Vector2 direction, float rotation) {
        this.direction = direction;
        this.rotation = rotation;
    }

    public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
        return currentPosition.cpy().add((int) direction.x, (int) direction.y);
    }

    public float getRotation() {
        return rotation;
    }
}

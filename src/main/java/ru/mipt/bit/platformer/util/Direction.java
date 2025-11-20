package ru.mipt.bit.platformer.util;

import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP(new Vector2(0, 1), 90f, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W)),
    DOWN(new Vector2(0, -1), -90f, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S)),
    LEFT(new Vector2(-1, 0), -180f, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A)),
    RIGHT(new Vector2(1, 0), 0f, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D));

    private final Vector2 direction;
    private final float rotation;
    private final Collection<Integer> keys;

    Direction(Vector2 direction, float rotation, Collection<Integer> keys) {
        this.direction = direction;
        this.rotation = rotation;
        this.keys = keys;
    }

    public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
        return currentPosition.cpy().add((int) direction.x, (int) direction.y);
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isKeyPressed() {
        return this.keys.stream().anyMatch(com.badlogic.gdx.Gdx.input::isKeyPressed);
    }
}

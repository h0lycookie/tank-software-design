package ru.mipt.bit.platformer.util;

import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(90f, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W)) {
        @Override
        public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.incrementedY(currentPosition);
        }
    },

    DOWN(-90f, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S)) {
        @Override
        public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.decrementedY(currentPosition);
        }
    },

    LEFT(-180f, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A)) {
        @Override
        public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.decrementedX(currentPosition);
        }
    },

    RIGHT(0f, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)) {
        @Override
        public GridPoint2 getNewPosition(GridPoint2 currentPosition) {
            return GdxGameUtils.incrementedX(currentPosition);
        }
    };

    private final float rotation;
    private final Collection<Integer> keys;

    Direction(float rotation, Collection<Integer> keys) {
        this.rotation = rotation;
        this.keys = keys;
    }

    public abstract GridPoint2 getNewPosition(GridPoint2 currentPosition);

    public float getRotation() {
        return rotation;
    }

    public boolean isKeyPressed() {
        return this.keys.stream().anyMatch(com.badlogic.gdx.Gdx.input::isKeyPressed);
    }
}

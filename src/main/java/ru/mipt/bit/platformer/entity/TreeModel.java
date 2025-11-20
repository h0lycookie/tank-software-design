package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.entity.interfaces.Entity;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModel implements Entity {
    GridPoint2 position;

    public TreeModel(GridPoint2 position) {
        this.position = position;
    }

    @Override
    public GridPoint2 getPosition() {
        return position;
    }
}

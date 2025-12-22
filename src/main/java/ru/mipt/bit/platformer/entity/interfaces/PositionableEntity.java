package ru.mipt.bit.platformer.entity.interfaces;

import com.badlogic.gdx.math.GridPoint2;

public interface PositionableEntity extends Entity {
    public GridPoint2 getPosition();
}

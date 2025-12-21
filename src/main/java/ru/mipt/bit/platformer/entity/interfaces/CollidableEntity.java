package ru.mipt.bit.platformer.entity.interfaces;

import java.util.Collection;

import com.badlogic.gdx.math.GridPoint2;

public interface CollidableEntity extends PositionableEntity {
    Collection<GridPoint2> getCollisionPositions();
}

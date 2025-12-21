package ru.mipt.bit.platformer.entity.interfaces;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

public interface CollidableEntity extends PositionableEntity {
    Set<GridPoint2> getCollisionPositions();
}

package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

public class TreeModel implements CollidableEntity {
    GridPoint2 position;

    public TreeModel(GridPoint2 position) {
        this.position = position;
    }

    @Override
    public GridPoint2 getPosition() {
        return position;
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        return Set.of(position);
    }
}

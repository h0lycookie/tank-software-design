package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;

import java.util.Collection;
import java.util.List;

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
    public Collection<GridPoint2> getCollisionPositions() {
        return List.of(position);
    }
}

package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;

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

    @Override
    public void onHit(float damage) {}
}

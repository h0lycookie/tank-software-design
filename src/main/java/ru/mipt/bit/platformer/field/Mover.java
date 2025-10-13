package ru.mipt.bit.platformer.field;

import java.util.ArrayList;
import java.util.Collection;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;

public class Mover {
    private final Collection<MovableEntity> movableEntities;

    public Mover() {
        this.movableEntities = new ArrayList<>();
    }

    public void addMovableEntity(MovableEntity entity) {
        movableEntities.add(entity);
    }

    public void moveEntities(float deltaTime) {
        for (MovableEntity entity: movableEntities) {
            entity.move(deltaTime);
        }
    }

    public Collection<MovableEntity> getMovableEntities() {
        return movableEntities;
    }
}

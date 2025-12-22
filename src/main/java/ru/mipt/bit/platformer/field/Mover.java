package ru.mipt.bit.platformer.field;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;

public class Mover {
    private final Set<MovableEntity> movableEntities;

    public Mover() {
        this.movableEntities = new CopyOnWriteArraySet<>();
    }

    public void addMovableEntity(MovableEntity entity) {
        movableEntities.add(entity);
    }

    public void removeMovableEntity(MovableEntity entity) {
        movableEntities.remove(entity);
    }

    public void moveEntities(float deltaTime) {
        for (MovableEntity entity: movableEntities) {
            entity.move(deltaTime);
        }
    }
}

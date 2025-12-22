package ru.mipt.bit.platformer.field;

import java.util.HashSet;
import java.util.Set;

import ru.mipt.bit.platformer.entity.interfaces.Entity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RemovableFrom;

public class Mover implements RemovableFrom {
    private final Set<MovableEntity> movableEntities;

    public Mover() {
        this.movableEntities = new HashSet<>();
    }

    @Override
    public void removeEntity(Entity entity) {
        movableEntities.remove(entity);
    }

    public void addMovableEntity(MovableEntity entity) {
        movableEntities.add(entity);
    }

    public void moveEntities(float deltaTime) {
        for (MovableEntity entity: Set.copyOf(movableEntities)) {
            entity.move(deltaTime);
        }
    }
}

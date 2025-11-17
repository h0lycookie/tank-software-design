package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.util.Direction;

public class MoveEntityCommand implements Command {
    private final MovableEntity entity;
    private final Direction direction;

    public MoveEntityCommand(MovableEntity entity, Direction direction) {
        this.entity = entity;
        this.direction = direction;
    }

    @Override
    public void execute() {
        entity.prepareMovement(direction);
    }
}

package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;

public class MoveTankCommand implements Command {

    private final MovableEntity tank;
    private final Direction direction;

    public MoveTankCommand(MovableEntity tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.move(direction, renderer);
    }
}

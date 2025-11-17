package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.command.MoveEntityCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MoveEntityCommandGenerator {
    public static List<Command> create(MovableEntity entity) {
        List<Command> commands = Arrays.stream(Direction.values())
                .map(direction -> new MoveEntityCommand(entity, direction))
                .collect(Collectors.toList());
        return commands;
    }

    public static List<List<Command>> create(Collection<MovableEntity> entities) {
        return entities.stream()
                .map(MoveEntityCommandGenerator::create)
                .collect(Collectors.toList());
    }
}
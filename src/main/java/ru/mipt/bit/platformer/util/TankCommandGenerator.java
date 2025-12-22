package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.entity.TankModel;
import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShotCommand;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TankCommandGenerator {
    public static List<Command> create(TankModel tankModel) {
        List<Command> commands = Arrays.stream(Direction.values())
                .map(direction -> new MoveTankCommand(tankModel, direction))
                .collect(Collectors.toList());
        commands.add(new ShotCommand(tankModel));
        return commands;
    }

    public static List<List<Command>> create(Collection<TankModel> entities ) {
        return entities.stream()
                .map(TankCommandGenerator::create)
                .collect(Collectors.toList());
    }
}
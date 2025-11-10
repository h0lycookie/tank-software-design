package ru.mipt.bit.platformer.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ru.mipt.bit.platformer.entity.interfaces.Command;

public class AIControlHandler {
    private final List<List<Command>> commands;

    public AIControlHandler(List<List<Command>> commands) {
        this.commands = commands;
    }

    public void control() {
        for (var commandsForAI : commands) {
            int action = ThreadLocalRandom.current().nextInt(commands.size());
            var command = commandsForAI.get(action);
            command.execute();
        }
    }
}

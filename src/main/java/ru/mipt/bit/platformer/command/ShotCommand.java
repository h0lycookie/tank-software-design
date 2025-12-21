package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.entity.interfaces.Shoots;

public class ShotCommand implements Command {
    private final Shoots model;

    public ShotCommand(Shoots model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.shoot();
    }
}

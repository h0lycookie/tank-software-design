package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.interfaces.Command;
import ru.mipt.bit.platformer.entity.interfaces.Shoots;

public class ShotCommand implements Command {
    private final Shoots shooter;

    public ShotCommand(Shoots shooter) {
        this.shooter = shooter;
    }

    @Override
    public void execute() {
        shooter.shoot();
    }
}

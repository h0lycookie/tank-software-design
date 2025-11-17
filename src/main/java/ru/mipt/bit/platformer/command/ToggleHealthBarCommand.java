package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.interfaces.Command;

public class ToggleHealthBarCommand implements Command {
    private final HealthBarModel healthBarModel;

    public ToggleHealthBarCommand(HealthBarModel healthBarModel) {
        this.healthBarModel = healthBarModel;
    }

    @Override
    public void execute() {
        healthBarModel.switchVisibility();
    }
}

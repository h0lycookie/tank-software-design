package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.entity.HealthBarsState;
import ru.mipt.bit.platformer.entity.interfaces.Command;

public class ToggleHealthBarCommand implements Command {
    public ToggleHealthBarCommand() {}

    @Override
    public void execute() {
        HealthBarsState.getInstance().toggleVisibility();
    }
}

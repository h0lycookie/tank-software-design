package ru.mipt.bit.platformer.util;

public class ToggleHealthBarCommand {
    private final HealthBarModel healthBarModel;

    public ToggleHealthBarCommand(HealthBarModel healthBarModel) {
        this.healthBarModel = healthBarModel;
    }

    @Override
    public void execute() {
        healthBarModel.switchVisibility();
    }
}

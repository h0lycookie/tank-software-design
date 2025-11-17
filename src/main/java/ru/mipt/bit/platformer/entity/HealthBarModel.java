package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.entity.interfaces.HealthableEntity;

public class HealthBarModel implements HealthableEntity {
    private boolean visible;

    public HealthBarModel(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    public void switchVisibility() {
        visible = !visible;
    }

    @Override
    public float getHealth() {
        return 100;
    }
}
package ru.mipt.bit.platformer.entity;

public class HealthBarsState {
    private boolean visible = true;

    private HealthBarsState() {
    }

    public void toggleVisibility() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }

    private static class SingletonHolder {
        public static final HealthBarsState HOLDER_INSTANCE = new HealthBarsState();
    }

    public static HealthBarsState getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
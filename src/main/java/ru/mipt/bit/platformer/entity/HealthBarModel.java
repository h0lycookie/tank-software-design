package ru.mipt.bit.platformer.entity;


public class HealthBarModel {
    private float health;
    private boolean visible;

    public HealthBarModel(float health, boolean visible) {
        this.health = health;
        this.visible = visible;
    }

    public float getHealth() {
        return health;
    }

    public boolean getVisible() {
        return visible;
    }

    public void switchVisibility() {
        visible = !visible;
    }
}
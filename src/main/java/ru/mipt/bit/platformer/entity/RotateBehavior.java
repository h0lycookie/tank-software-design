package ru.mipt.bit.platformer.entity;

public class RotateBehavior {
    private float rotation;

    public RotateBehavior(float rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }
}
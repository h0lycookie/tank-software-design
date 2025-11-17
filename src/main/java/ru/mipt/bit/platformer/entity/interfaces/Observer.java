package ru.mipt.bit.platformer.entity.interfaces;

public interface Observer {
    void objectAppeared(MovableEntity model, String name);
    void objectDestroyed(MovableEntity model, String name);
}

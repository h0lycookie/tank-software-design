package ru.mipt.bit.platformer.entity.interfaces;

public interface Observer {
    void onObjectRegistered(Entity entity);
    void onObjectDiscarded(Entity entity);
}

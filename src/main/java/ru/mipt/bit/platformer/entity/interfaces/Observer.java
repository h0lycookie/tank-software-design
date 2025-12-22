package ru.mipt.bit.platformer.entity.interfaces;

public interface Observer {
    public void onObjectRegistered(Entity entity);
    public void onObjectDiscarded(Entity entity);
}

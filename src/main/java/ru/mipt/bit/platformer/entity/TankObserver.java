package ru.mipt.bit.platformer.entity;

import ru.mipt.bit.platformer.entity.interfaces.Entity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;

public class TankObserver implements Observer {
    private final Renderer renderer;
    private final Mover mover;

    public TankObserver(Renderer renderer, Mover mover) {
        this.renderer = renderer;
        this.mover = mover;
    }

    @Override
    public void onObjectRegistered(Entity entity) {}

    @Override
    public void onObjectDiscarded(Entity entity) {
        if (entity instanceof TankModel tankModel) {
            renderer.removeRenderableEntityByModel(tankModel);
            mover.removeMovableEntity(tankModel);
        }
    }
}
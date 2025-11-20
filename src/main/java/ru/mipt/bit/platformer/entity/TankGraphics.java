package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class TankGraphics implements RenderableEntity {
    private MovingRenderBehavior movingRenderBehavior;
    private TankModel tankModel;

    public TankGraphics(MovingRenderBehavior renderBehavior, TankModel tankModel) {
        this.movingRenderBehavior = renderBehavior;
        this.tankModel = tankModel;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        movingRenderBehavior.render(batch, layer, tankModel.getPosition(),
        tankModel.getDestinationPosition(), tankModel.getRotation(), tankModel.getMovementProgress());
    }

    @Override
    public GridPoint2 getPosition() {
        return tankModel.getPosition();
    }
}

package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.PositionableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class TankGraphics implements RenderableEntity {
    private final MovingRenderBehavior movingRenderBehavior;
    private final TankModel tankModel;

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

    @Override
    public Rectangle getRectangle() {
        return movingRenderBehavior.getRectangle();
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        return tankModel.getCollisionPositions();
    }

    @Override
    public PositionableEntity getModel() {
        return tankModel;
    }

    @Override
    public void destroy() {}
}

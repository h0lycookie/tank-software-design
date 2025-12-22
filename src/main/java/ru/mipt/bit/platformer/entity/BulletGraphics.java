package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.CollidableEntity;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;;

public class BulletGraphics implements RenderableEntity {
    private MovingRenderBehavior movingRenderBehavior; 
    private BulletModel bulletModel;

    public BulletGraphics(MovingRenderBehavior movingRenderBehavior, BulletModel bulletModel) {
        this.movingRenderBehavior = movingRenderBehavior;
        this.bulletModel = bulletModel;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        movingRenderBehavior.render(batch, layer, bulletModel.getPosition(),
        bulletModel.getDestinationPosition(), bulletModel.getRotation(), bulletModel.getMovementProgress());
    }

    @Override
    public Rectangle getRectangle() {
        return movingRenderBehavior.getRectangle();
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        return bulletModel.getCollisionPositions();
    }

    @Override
    public GridPoint2 getPosition() {
        return bulletModel.getPosition();
    }

    @Override
    public CollidableEntity getModel() {
        return bulletModel;
    }

    @Override
    public void destroy() {
    }
}

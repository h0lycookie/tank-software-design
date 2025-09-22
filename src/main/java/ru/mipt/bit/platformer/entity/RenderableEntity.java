package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.GdxGameUtils;

public class RenderableEntity {
    protected GridPoint2 position;
    protected float rotation;
    protected Rectangle rectangle;
    protected TextureRegion sprite;

    protected RenderableEntity(GridPoint2 position, float rotation, TextureRegion sprite, TiledMapTileLayer groundLayer) {
        this.position = position;
        this.rotation = rotation;
        this.rectangle = GdxGameUtils.createBoundingRectangle(sprite);
        this.sprite = sprite;

        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, position);   
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, sprite, rectangle, rotation);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public GridPoint2 getPosition() {
        return new GridPoint2(position);    // returned type is passed with a reference; hence one can unintentionally change the returned value, so we create a new one
    }
}

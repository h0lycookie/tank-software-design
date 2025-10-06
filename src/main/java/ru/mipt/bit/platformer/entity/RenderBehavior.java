package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.GdxGameUtils;

public class RenderBehavior {
    private GridPoint2 position;
    private Rectangle rectangle;
    private TextureRegion sprite;
    private RotateBehavior rotateBehavior;

    public RenderBehavior(GridPoint2 position, TextureRegion sprite, TiledMapTileLayer groundLayer, RotateBehavior rotateBehavior) {
        this.position = position;
        this.rectangle = GdxGameUtils.createBoundingRectangle(sprite);
        this.sprite = sprite;
        this.rotateBehavior = rotateBehavior;

        GdxGameUtils.moveRectangleAtTileCenter(groundLayer, rectangle, position);   
    }

    public void render(Batch batch) {
        GdxGameUtils.drawTextureRegionUnscaled(batch, sprite, rectangle, rotateBehavior.getRotation());
    }

    public void setPosition(GridPoint2 position) {
        this.position.set(position);
    }

    public GridPoint2 getPosition() {
        return new GridPoint2(position);    // returned type is passed with a reference; hence one can unintentionally change the returned value, so we create a new one
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRotation(float rotation) {
        rotateBehavior.setRotation(rotation);
    }
}
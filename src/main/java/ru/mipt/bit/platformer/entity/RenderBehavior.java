package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.GdxGameUtils;

public class RenderBehavior {
    private Rectangle rectangle;
    private TextureRegion sprite;

    public RenderBehavior(TextureRegion sprite) {
        this.rectangle = GdxGameUtils.createBoundingRectangle(sprite);
        this.sprite = sprite;
    }

    public void render(Batch batch, TiledMapTileLayer layer, GridPoint2 position) {
        GdxGameUtils.moveRectangleAtTileCenter(layer, rectangle, position);
        GdxGameUtils.drawTextureRegionUnscaled(batch, sprite, rectangle, 0f);
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = new Rectangle(rectangle);
    }

    public Rectangle getRectangle() {
        return new Rectangle(rectangle);
    }

    public TextureRegion getSprite() {
        return new TextureRegion(sprite);
    }
}
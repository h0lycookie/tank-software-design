package ru.mipt.bit.platformer.entity.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public interface RenderableEntity extends PositionableEntity {
    public void render(Batch batch, TiledMapTileLayer layer);

    public Rectangle getRectangle();

    public PositionableEntity getModel();
};


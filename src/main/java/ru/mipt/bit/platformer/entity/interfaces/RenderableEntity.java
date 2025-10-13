package ru.mipt.bit.platformer.entity.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public interface RenderableEntity extends Entity {
    public void render(Batch batch, TiledMapTileLayer layer);
};

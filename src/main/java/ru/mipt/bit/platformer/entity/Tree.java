package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public class Tree extends RenderableEntity {
    public Tree(GridPoint2 position, TextureRegion sprite, TiledMapTileLayer groundLayer) {
        super(position, 0f, sprite, groundLayer);
    }
};

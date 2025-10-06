package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public interface RenderableEntity {
    public void render(Batch batch);
    public GridPoint2 getPosition();
};

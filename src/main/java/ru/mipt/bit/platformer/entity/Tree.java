package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public class Tree implements RenderableEntity {
    RenderBehavior renderBehavior;

    public Tree(RenderBehavior renderBehavior) {
        this.renderBehavior = renderBehavior;
    }

    @Override
    public void render(Batch batch) {
        renderBehavior.render(batch);
    }

    @Override
    public GridPoint2 getPosition() {
        return renderBehavior.getPosition();
    }
};

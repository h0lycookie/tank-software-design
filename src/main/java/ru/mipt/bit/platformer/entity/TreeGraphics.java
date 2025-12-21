package ru.mipt.bit.platformer.entity;

import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class TreeGraphics implements RenderableEntity {
    RenderBehavior renderBehavior;
    TreeModel treeModel;

    public TreeGraphics(RenderBehavior renderBehavior, TreeModel treeModel) {
        this.renderBehavior = renderBehavior;
        this.treeModel = treeModel;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        renderBehavior.render(batch, layer, treeModel.getPosition());
    } 

    @Override
    public GridPoint2 getPosition() {
        return treeModel.getPosition();
    }

    @Override
    public Rectangle getRectangle() {
        return renderBehavior.getRectangle();
    }

    @Override
    public Set<GridPoint2> getCollisionPositions() {
        return Set.of(getPosition());
    }
}

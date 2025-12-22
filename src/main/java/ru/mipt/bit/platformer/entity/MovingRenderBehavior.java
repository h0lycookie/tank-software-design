package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

public class MovingRenderBehavior {
    private final RenderBehavior renderBehavior;
    private final TileMovement tileMovement;

    public MovingRenderBehavior(RenderBehavior renderBehavior, TileMovement tileMovement) {
        this.renderBehavior = renderBehavior;
        this.tileMovement = tileMovement;
    }

    public void render(Batch batch, TiledMapTileLayer layer, GridPoint2 position, GridPoint2 destinationPosition, float rotation, float movementProgress) {
        renderBehavior.setRectangle(tileMovement.moveRectangleBetweenTileCenters(renderBehavior.getRectangle(), position, destinationPosition, movementProgress));
        GdxGameUtils.drawTextureRegionUnscaled(batch, renderBehavior.getSprite(), renderBehavior.getRectangle(), rotation);
    }

    public Rectangle getRectangle() {
        return renderBehavior.getRectangle();
    }
}
package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class HealthBarDecorator implements RenderableEntity {

    private static final int HEALTH_BAR_WIDTH = 70;
    private static final int HEALTH_BAR_HEIGHT = 16;
    private static final int HEALTH_BAR_OFFSET_Y = 90;

    private MovingRenderBehavior movingRenderBehavior;
    private final HealthBarModel healthBarModel;

    public HealthBarDecorator(MovingRenderBehavior movingRenderBehavior, HealthBarModel healthBarModel) {
        this.movingRenderBehavior = movingRenderBehavior;
        this.healthBarModel = healthBarModel;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        movingRenderBehavior.render(batch, layer, healthBarModel.getPosition(),
        healthBarModel.getDestinationPosition(), healthBarModel.getRotation(), healthBarModel.getMovementProgress());
    }

    @Override
    public GridPoint2 getPosition() {
        return healthBarModel.getPosition();
    }

    private TextureRegion createHealthBarTexture(float relativeHealth) {
        Pixmap pixmap = new Pixmap(HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, (int) (HEALTH_BAR_WIDTH * relativeHealth), HEALTH_BAR_HEIGHT);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }
}
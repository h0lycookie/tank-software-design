package ru.mipt.bit.platformer.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class HealthBarDecorator implements RenderableEntity {
    private static final int HEALTH_BAR_WIDTH = 70;
    private static final int HEALTH_BAR_HEIGHT = 16;
    private static final int HEALTH_BAR_OFFSET_Y = 90;
    private static final int MAX_POSSIBLE_HEALTH = 100;

    private RenderableEntity entity;
    private final HealthBarModel healthBarModel;

    public HealthBarDecorator(RenderableEntity entity, HealthBarModel healthBarModel) {
        this.entity = entity;
        this.healthBarModel = healthBarModel;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        if (!healthBarModel.getVisible()) {
            return;
        }

        float relativeHealth = healthBarModel.getHealth() / MAX_POSSIBLE_HEALTH;
        TextureRegion healthBarTexture = createHealthBarTexture(relativeHealth);
        Rectangle healthBarRectangle = createHealthBarRectangle();
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthBarTexture, healthBarRectangle, 0f);

        entity.render(batch, layer);
    }

    @Override
    public GridPoint2 getPosition() {
        return entity.getPosition().add(0, HEALTH_BAR_OFFSET_Y);
    }

    @Override
    public Rectangle getRectangle() {
        return entity.getRectangle();
    }

    @Override
    public Collection<GridPoint2> getCollisionPositions() {
        return Collections.emptyList();
    }

    public float getHealth() {
        return healthBarModel.getHealth();
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

    private Rectangle createHealthBarRectangle() {
        Rectangle rectangle = new Rectangle(entity.getRectangle());
        rectangle.y += HEALTH_BAR_OFFSET_Y;
        return rectangle;
    }
}
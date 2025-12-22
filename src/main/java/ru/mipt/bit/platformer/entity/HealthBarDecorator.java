package ru.mipt.bit.platformer.entity;

import java.util.Collections;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.HealthableEntity;
import ru.mipt.bit.platformer.entity.interfaces.PositionableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;
import ru.mipt.bit.platformer.util.GdxGameUtils;

public class HealthBarDecorator implements RenderableEntity {
    private static final int HEALTH_BAR_WIDTH = 70;
    private static final int HEALTH_BAR_HEIGHT = 16;
    private static final int HEALTH_BAR_OFFSET_Y = 90;
    private static final int MAX_POSSIBLE_HEALTH = 100;

    private final RenderableEntity entity;

    public HealthBarDecorator(RenderableEntity entity) {
        this.entity = entity;
    }

    @Override
    public void render(Batch batch, TiledMapTileLayer layer) {
        if (HealthBarsState.getInstance().isVisible()) {
            float relativeHealth = getHealth() / MAX_POSSIBLE_HEALTH;
            TextureRegion healthBarTexture = createHealthBarTexture(relativeHealth);
            Rectangle healthBarRectangle = createHealthBarRectangle();
            GdxGameUtils.drawTextureRegionUnscaled(batch, healthBarTexture, healthBarRectangle, 0f);
        }

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
    public Set<GridPoint2> getCollisionPositions() {
        return Collections.emptySet();
    }

    @Override
    public void destroy() {
        entity.destroy();
    }

    @Override
    public PositionableEntity getModel() {
        return entity.getModel();
    }

    private float getHealth() {
        return (entity.getModel() instanceof HealthableEntity) ? ((HealthableEntity) entity.getModel()).getHealth() : 0;
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
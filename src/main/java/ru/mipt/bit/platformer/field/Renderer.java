package ru.mipt.bit.platformer.field;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class Renderer {
    private final MapRenderer levelRenderer;
    private final Collection<RenderableEntity> renderableEntities;

    public Renderer(MapRenderer levelRenderer) {
        this.levelRenderer = levelRenderer;
        this.renderableEntities = new ArrayList<>();
    }

    public void addRenderableEntity(RenderableEntity entity) {
        renderableEntities.add(entity);
    }

    public void renderLevel() {
        levelRenderer.render();
    }

    public void renderEntities(Batch batch, TiledMapTileLayer layer) {
        for (RenderableEntity entity: renderableEntities) {
            entity.render(batch, layer);
        }
    }

    public boolean isPositionTaken(GridPoint2 position) {
        return renderableEntities.stream().anyMatch(entity -> position.equals(entity.getPosition()));
    }

    public Collection<RenderableEntity> getRenderableEntities() {
        return renderableEntities;
    }
}

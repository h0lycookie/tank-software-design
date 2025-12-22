package ru.mipt.bit.platformer.field;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.entity.interfaces.PositionableEntity;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class Renderer {
    private final MapRenderer levelRenderer;
    private final Set<RenderableEntity> renderableEntities;

    public Renderer(MapRenderer levelRenderer) {
        this.levelRenderer = levelRenderer;
        this.renderableEntities = new CopyOnWriteArraySet<>();
    }
    
    public void addRenderableEntity(RenderableEntity entity) {
        renderableEntities.add(entity);
    }

    public void removeRenderableEntity(RenderableEntity entity) {
        renderableEntities.remove(entity);
    }

    public void renderLevel() {
        levelRenderer.render();
    }

    public void renderEntities(Batch batch, TiledMapTileLayer layer) {
        for (RenderableEntity entity: renderableEntities) {
            entity.render(batch, layer);
        }
    }
    
    public void removeRenderableEntityByModel(PositionableEntity entity) {
        for (RenderableEntity renderableEntity : renderableEntities) {
            if (renderableEntity.getModel() == entity) {
                removeRenderableEntity(renderableEntity);
                return;
            }
        }
    }
}

package ru.mipt.bit.platformer.field;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import ru.mipt.bit.platformer.entity.interfaces.Entity;
import ru.mipt.bit.platformer.entity.interfaces.RemovableFrom;
import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;

public class Renderer implements RemovableFrom {
    private final MapRenderer levelRenderer;
    private final Set<RenderableEntity> renderableEntities;

    public Renderer(MapRenderer levelRenderer) {
        this.levelRenderer = levelRenderer;
        this.renderableEntities = new HashSet<>();
    }

    @Override
    public void removeEntity(Entity entity) {
        renderableEntities.remove(entity);
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
}

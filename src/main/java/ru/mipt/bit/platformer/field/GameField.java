package ru.mipt.bit.platformer.field;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.RenderableEntity;
import ru.mipt.bit.platformer.entity.MovableEntity;

public class GameField {
    private final MapRenderer levelRenderer;
    private final Collection<MovableEntity> updatableEntities;
    private final Collection<RenderableEntity> renderableEntities;

    public GameField(MapRenderer levelRenderer) {
        this.levelRenderer = levelRenderer;
        this.updatableEntities = new ArrayList<>();
        this.renderableEntities = new ArrayList<>();
    }

    public void addMovableEntity(MovableEntity entity) {
        updatableEntities.add(entity);
    }

    public void addRenderableEntity(RenderableEntity entity) {
        renderableEntities.add(entity);
    }

    public void renderLevel() {
        levelRenderer.render();
    }

    public void renderEntities(Batch batch) {
        for (RenderableEntity entity: renderableEntities) {
            entity.render(batch);
        }
    }

    public void updateEntities(float deltaTime) {
        for (MovableEntity entity: updatableEntities) {
            entity.move(deltaTime);
        }
    }

    public boolean isPositionTaken(GridPoint2 position) {
        return renderableEntities.stream().anyMatch(entity -> position.equals(entity.getPosition()));
    }
}

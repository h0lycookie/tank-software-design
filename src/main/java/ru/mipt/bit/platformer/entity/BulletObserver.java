package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.mipt.bit.platformer.entity.interfaces.Entity;
import ru.mipt.bit.platformer.entity.interfaces.Observer;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletObserver implements Observer {
    private final Renderer renderer;
    private final Mover mover;
    private final TileMovement tileMovement;

    public BulletObserver(Renderer renderer, Mover mover, TileMovement tileMovement) {
        this.renderer = renderer;
        this.mover = mover;
        this.tileMovement = tileMovement;
    }

    @Override
    public void onObjectRegistered(Entity entity) {
        if (entity instanceof BulletModel bulletModel) {
            RenderBehavior renderBehavior = new RenderBehavior(new TextureRegion(new Texture("images/bullet.png")));
            MovingRenderBehavior movingRenderBehavior = new MovingRenderBehavior(renderBehavior, tileMovement);
            BulletGraphics bulletGraphics = new BulletGraphics(movingRenderBehavior, bulletModel);
            mover.addMovableEntity(bulletModel);
            renderer.addRenderableEntity(bulletGraphics);
        }
    }

    @Override
    public void onObjectDiscarded(Entity entity) {
        if (entity instanceof BulletModel bulletModel) {
            renderer.removeRenderableEntityByModel(bulletModel);
            mover.removeMovableEntity(bulletModel);
        }
    }
}
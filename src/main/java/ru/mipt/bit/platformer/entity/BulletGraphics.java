package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.entity.interfaces.RenderableEntity;
import ru.mipt.bit.platformer.util.TileMovement;

// public class BulletGraphics implements RenderableEntity {

//     private final Texture texture;
//     private final TextureRegion graphics;
//     private MovingRenderBehavior movingRenderBehavior; 
//     private BulletModel bulletModel;

//     public BulletGraphics(String texturePath, MovingRenderBehavior movingRenderBehavior, BulletModel bulletModel) {
//         this.texture = new Texture(texturePath);
//         this.graphics = new TextureRegion(texture);
//         this.movingRenderBehavior = movingRenderBehavior;
//         this.bulletModel = bulletModel;
//     }

//     @Override
//     public void render(Batch batch, TiledMapTileLayer layer) {
//         movingRenderBehavior.render(batch, layer, bulletModel.getPosition(),
//         bulletModel.getDestinationPosition(), bulletModel.getRotation(), bulletModel.getMovementProgress());
//     }

//     @Override
//     public GridPoint2 getPosition() {
//         return bulletModel.getPosition();
//     }
// }

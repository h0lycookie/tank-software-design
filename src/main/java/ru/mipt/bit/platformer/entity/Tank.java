package ru.mipt.bit.platformer.entity;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.math.MathUtils.isEqual;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Tank extends RenderableEntity implements UpdatableEntity {
    private GridPoint2 destinationPosition;

    private float movementSpeed;
    private float movementProgress = 1f;

    private boolean isFocused = false;

    private TileMovement tileMovement;

    public Tank(GridPoint2 position, float movementSpeed, TextureRegion sprite, TiledMapTileLayer groundLayer, TileMovement tileMovement) {
        super(position, 0f, sprite, groundLayer);
        this.destinationPosition = new GridPoint2(position);
        this.movementSpeed = movementSpeed;
        this.tileMovement = tileMovement;
    }

    public void setDestinationPosition(GridPoint2 newPosition, Direction direction) {
        destinationPosition.set(newPosition);
        movementProgress = 0f;
        rotation = direction.getRotation();
    }

    public boolean hasMoved() {
        return isEqual(movementProgress, 1f);
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    @Override
    public void update(float deltaTime) {
        movementProgress = GdxGameUtils.continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isFocused) {
            tileMovement.moveRectangleBetweenTileCenters(rectangle, position, destinationPosition, movementProgress);
        }

        if (isEqual(movementProgress, 1f)) {
            position.set(destinationPosition);
        }
    }
}
package ru.mipt.bit.platformer;

import org.junit.Test;
import static org.junit.Assert.*;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.entity.RenderBehavior;
import ru.mipt.bit.platformer.field.GameField;

public class MoveBehaviorTest {
    @Test
    public void testHasMoved() {
        MoveBehavior moveBehavior = new MoveBehavior(0.4f, true, null, null);
        assertTrue(moveBehavior.hasMoved());
    }

    @Test
    public void testPrepareMovement() {
        Batch batch = new SpriteBatch();

        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        GameField gameField = new GameField(levelRenderer);
        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        MoveBehavior moveBehavior = new MoveBehavior(0.4f, true, tileMovement, renderBehavior);
        moveBehavior.prepareMovement(Direction.UP, gameField);

        assertEquals(moveBehavior.getRotation(), 90f, 0.01f);
        assertFalse(moveBehavior.hasMoved());
        assertEquals(moveBehavior.getDestinationPosition(), new GridPoint2(1, 2));
    }

    @Test
    public void testMove() {
        batch = new SpriteBatch();

        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        gameField = new GameField(levelRenderer);

        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        MoveBehavior moveBehavior = new MoveBehavior(0.4f, true, tileMovement, renderBehavior);

        moveBehavior.setDestinationPosition(new GridPoint2(2, 1));
        moveBehavior.move(1f);

        assertEquals(moveBehavior.getPosition(), new GridPoint2(2, 1));
    }
}

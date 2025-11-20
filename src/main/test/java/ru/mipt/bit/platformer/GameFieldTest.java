package ru.mipt.bit.platformer;

import org.junit.Test;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.entity.Tank;
import ru.mipt.bit.platformer.field.GameField;

import static org.junit.Assert.*;

public class GameFieldTest {
    @Test
    public void testAddMovableEntity() {
        Batch batch = new SpriteBatch();

        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        GameField gameField = new GameField(levelRenderer);

        final float MOVEMENT_SPEED = 0.4f;
        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        Tank tank = new Tank(tankRenderBehavior, new MoveBehavior(MOVEMENT_SPEED, true, tileMovement, tankRenderBehavior));
        gameField.addMovableEntity(tank);

        assertEquals(gameField.getMovableEntities().size(), 1);
    }

    @Test
    public void testAddRenderableEntity() {
        Batch batch = new SpriteBatch();

        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        GameField gameField = new GameField(levelRenderer);

        final float MOVEMENT_SPEED = 0.4f;
        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        Tank tank = new Tank(tankRenderBehavior, new MoveBehavior(MOVEMENT_SPEED, true, tileMovement, tankRenderBehavior));
        gameField.addRenderableEntity(tank);

        assertEquals(gameField.getRenderableEntities().size(), 1);
    }

    @Test
    public void testMoveEntities() {
        Batch batch = new SpriteBatch();

        TiledMap level = new TmxMapLoader().load("level.tmx");
        MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        GameField gameField = new GameField(levelRenderer);

        final float MOVEMENT_SPEED = 0.4f;
        RenderBehavior tankRenderBehavior = new RenderBehavior(new GridPoint2(1, 1), new TextureRegion(new Texture("images/tank_blue.png")), groundLayer, new RotateBehavior(0f));
        MoveBehavior tankMoveBehavior = new MoveBehavior(MOVEMENT_SPEED, true, tileMovement, tankRenderBehavior);
        tankMoveBehavior.prepareMovement(Direction.UP, gameField);
        Tank tank = new Tank(tankRenderBehavior, tankMoveBehavior);
        
        gameField.addMovableEntity(tank);
        gameField.moveEntities(1f);
        
        assertEquals(tank.getPosition(), new GridPoint2(1, 2));
    }
};
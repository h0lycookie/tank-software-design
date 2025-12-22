package ru.mipt.bit.platformer;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShotCommand;
import ru.mipt.bit.platformer.command.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.config.AppConfig;
import ru.mipt.bit.platformer.entity.MapState;
import ru.mipt.bit.platformer.entity.interfaces.MovableShootsEntity;
import ru.mipt.bit.platformer.field.Mover;
import ru.mipt.bit.platformer.field.Renderer;
import ru.mipt.bit.platformer.util.AIControlHandler;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.Direction;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import ru.mipt.bit.platformer.util.TankCommandGenerator;
import ru.mipt.bit.platformer.util.TileMovement;

public class GameDesktopLauncher implements ApplicationListener {
    private static final String LEVEL_FILE_PATH = "./src/main/resources/level_design.txt";

    private Batch batch;
    private TiledMap level;
    private TiledMapTileLayer layer;
    private TileMovement tileMovement;
    private Mover mover;
    private Renderer renderer;
    private ControlHandler controlHandler;
    private AIControlHandler aiControlHandler;
    
    private ApplicationContext context;
    private MapState mapState;

    @Override
    public void create() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        batch = new SpriteBatch();
        initLevelStructures();
        initHandlingOnTick();

        mapState = context.getBean(MapState.class);
        mapState.setWidth(layer.getWidth());
        mapState.setHeight(layer.getHeight());
        
        mapState.initGameObjects(renderer, mover, tileMovement);

        controlHandler = createControlHandler(mapState.getPlayerTank());
        aiControlHandler = new AIControlHandler(TankCommandGenerator.create(mapState.getAITanks()));
    }

    private ControlHandler createControlHandler(MovableShootsEntity playerTank) {
        ControlHandler controlHandler = new ControlHandler();

        Map<Direction, List<Integer>> controls = Map.of(
            Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
            Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
            Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
            Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
        );

        controls.forEach((direction, keys) ->
            controlHandler.addButtonAction(keys,
                    new MoveTankCommand(playerTank, direction), true));

        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), 
            new ToggleHealthBarCommand(), false);
        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), 
            new ShotCommand(playerTank), false);
        
        return controlHandler;
    }
    
    @Override
    public void render() {
        processActions();
        processRendering();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void processActions() {
        controlHandler.handle(Gdx.input);
        aiControlHandler.handle();
        mover.moveEntities(Gdx.graphics.getDeltaTime());
    }
    
    private void processRendering() {
        clearScreen();
        renderer.renderLevel();
        batch.begin();
        renderer.renderEntities(batch, layer);
        batch.end();
    }

    private void initLevelStructures() {
        level = new TmxMapLoader().load("level.tmx");
        layer = getSingleLayer(level);
        tileMovement = new TileMovement(layer, Interpolation.smooth);
    }

    private void initHandlingOnTick() {
        mover = new Mover();
        renderer = new Renderer(createSingleLayerMapRenderer(level, batch));
    }
}

package ru.mipt.bit.platformer.config;

import java.util.List;
import java.util.Map;
import ru.mipt.bit.platformer.command.MoveEntityCommand;
import ru.mipt.bit.platformer.command.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.entity.interfaces.MovableEntity;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.Direction;
import ru.mipt.bit.platformer.util.ObjectType;

public class Config {
    private final int TREES_COUNT = 0;
    private final int AI_TANKS_COUNT = 3;
    private final float INITIAL_HEALTH = 90.f;
    private final float MOVEMENT_SPEED = 0.4f;

    public ControlHandler getControlHandler(MovableEntity playerTank) {
        ControlHandler controlHandler = new ControlHandler();

        Map<Direction, List<Integer>> controls = Map.of(
            Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
            Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
            Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
            Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
        
        );

        controls.forEach((direction, keys) ->
            controlHandler.addButtonAction(keys,
                    new MoveEntityCommand(playerTank, direction), true));


        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), new ToggleHealthBarCommand(), false);
        // controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), new ShotCommand(playerTank), false);
        
        return controlHandler;
    }

    public Map<ObjectType, Integer> getObstaclesCount() {
        return Map.of(
            ObjectType.TREE, TREES_COUNT,
            ObjectType.TANK, AI_TANKS_COUNT + 1   // plus player's tank
        );
    }

    public float getTankInitialHealth() {
        return INITIAL_HEALTH;
    }

    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }
}

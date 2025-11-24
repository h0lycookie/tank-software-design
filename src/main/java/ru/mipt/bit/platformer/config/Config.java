package ru.mipt.bit.platformer.config;

import java.beans.BeanProperty;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.command.MoveEntityCommand;
import ru.mipt.bit.platformer.command.ShotCommand;
import ru.mipt.bit.platformer.command.ToggleHealthBarCommand;
import ru.mipt.bit.platformer.entity.HealthBarModel;
import ru.mipt.bit.platformer.entity.MapModel;
import ru.mipt.bit.platformer.entity.MoveBehavior;
import ru.mipt.bit.platformer.entity.TankModel;
import ru.mipt.bit.platformer.util.ControlHandler;
import ru.mipt.bit.platformer.util.Direction;

@org.springframework.context.annotation.Config
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ru.mipt.bit.platformer.entities")
public class Config {
    
    @Bean
    public ControlHandler controlHandler(MapModel mapModel) {
        ControlHandler controlHandler = new ControlHandler();

        Random rng;
        MoveBehavior tankMoveBehavior = new MoveBehavior(new GridPoint2(rng.nextInt(mapModel.getWidth()), rng.nextInt(mapModel.getHeight())), mapModel.getMovementSpeed(), 0f, mapModel);
        TankModel playerTank = new TankModel(tankMoveBehavior);
            Map<Direction, List<Integer>> controls = Map.of(
            Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
            Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
            Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
            Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
        );

        controls.forEach((direction, keys) ->
            controlHandler.addButtonAction(keys,
                    new MoveEntityCommand(playerTank, direction), true));

        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), new ToggleHealthBarCommand(new HealthBarModel(true)), false);
        controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), new ShotCommand(playerTank), false);
        
        return controlHandler;
    }
}

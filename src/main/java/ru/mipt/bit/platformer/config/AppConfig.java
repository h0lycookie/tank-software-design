package ru.mipt.bit.platformer.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.mipt.bit.platformer.util.ObjectType;

@Configuration
@ComponentScan(basePackages = "ru.mipt.bit.platformer") 
public class AppConfig {
    
    @Bean("treesCount")
    public int treesCount() {
        return 3;
    }
    
    @Bean("aiTanksCount")
    public int aiTanksCount() {
        return 3;
    }
    
    @Bean("initialHealth")
    public float initialHealth() {
        return 80f;
    }
    
    @Bean("tankMovementSpeed")
    public float tankMovementSpeed() {
        return 0.4f;
    }
    
    @Bean("bulletMovementSpeed")
    public float bulletMovementSpeed() {
        return 0.3f;
    }
    
    @Bean
    public GameConfig gameConfig() {
        return new GameConfig(
            treesCount(),
            aiTanksCount(),
            initialHealth(),
            tankMovementSpeed(),
            bulletMovementSpeed()
        );
    }
    
    @Bean
    public Map<ObjectType, Integer> obstaclesCount() {
        return Map.of(
            ObjectType.TREE, treesCount(),
            ObjectType.TANK, aiTanksCount() + 1   // plus player's tank
        );
    }
    
    // @Bean
    // public ControlHandler controlHandler(MovableShootsEntity playerTank) {
    //     ControlHandler controlHandler = new ControlHandler();

    //     Map<Direction, List<Integer>> controls = Map.of(
    //         Direction.UP, List.of(com.badlogic.gdx.Input.Keys.UP, com.badlogic.gdx.Input.Keys.W),
    //         Direction.LEFT, List.of(com.badlogic.gdx.Input.Keys.LEFT, com.badlogic.gdx.Input.Keys.A),
    //         Direction.DOWN, List.of(com.badlogic.gdx.Input.Keys.DOWN, com.badlogic.gdx.Input.Keys.S),
    //         Direction.RIGHT, List.of(com.badlogic.gdx.Input.Keys.RIGHT, com.badlogic.gdx.Input.Keys.D)
    //     );

    //     controls.forEach((direction, keys) ->
    //         controlHandler.addButtonAction(keys,
    //                 new MoveTankCommand(playerTank, direction), true));

    //     controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.L), 
    //         new ToggleHealthBarCommand(), false);
    //     controlHandler.addButtonAction(List.of(com.badlogic.gdx.Input.Keys.SPACE), 
    //         new ShotCommand(playerTank), false);
        
    //     return controlHandler;
    // }
}
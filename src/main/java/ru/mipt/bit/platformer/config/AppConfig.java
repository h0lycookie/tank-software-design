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
        return 5;
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
}
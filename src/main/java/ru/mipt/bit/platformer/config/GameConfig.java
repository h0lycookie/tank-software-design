package ru.mipt.bit.platformer.config;

import org.springframework.stereotype.Component;

@Component
public class GameConfig {
    private final int treesCount;
    private final int aiTanksCount;
    private final float initialHealth;
    private final float tankMovementSpeed;
    private final float bulletMovementSpeed;

    public GameConfig(
        int treesCount,
        int aiTanksCount,
        float initialHealth,
        float tankMovementSpeed,
        float bulletMovementSpeed) {
        
        this.treesCount = treesCount;
        this.aiTanksCount = aiTanksCount;
        this.initialHealth = initialHealth;
        this.tankMovementSpeed = tankMovementSpeed;
        this.bulletMovementSpeed = bulletMovementSpeed;
    }
    
    public int getTreesCount() { return treesCount; }
    public int getAiTanksCount() { return aiTanksCount; }
    public float getInitialHealth() { return initialHealth; }
    public float getTankMovementSpeed() { return tankMovementSpeed; }
    public float getBulletMovementSpeed() { return bulletMovementSpeed; }
}
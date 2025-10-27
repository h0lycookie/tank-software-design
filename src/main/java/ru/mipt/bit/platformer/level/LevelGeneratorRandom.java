package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGeneratorRandom extends LevelGenerator {
    private final Random rng;
    private final int treeCount;

    public LevelGeneratorRandom(int width, int height, int treeCount) {
        super(width, height);
        this.rng = new Random();
        this.treeCount = treeCount;
    }

    public LevelData generateLevel() {
        List<GridPoint2> treePositions = new ArrayList<>();
        GridPoint2 playerPosition;

        for (int i = 0; i < treeCount; ++i) {
            GridPoint2 position;
            do {
                position = new GridPoint2(rng.nextInt(width), rng.nextInt(height));
            } while (treePositions.contains(position));
            treePositions.add(position);
        }

        do {
            playerPosition = new GridPoint2(rng.nextInt(width), rng.nextInt(height));
        } while (treePositions.contains(playerPosition));

        return new LevelData(playerPosition, treePositions);
    }
}
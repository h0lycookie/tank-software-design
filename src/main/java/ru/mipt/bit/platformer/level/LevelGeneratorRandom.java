package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGeneratorRandom extends LevelGenerator {
    private final Random rng;
    private final int treeCount;
    private final int aiTanksCount;

    public LevelGeneratorRandom(int width, int height, int treeCount, int aiTanksCount) {
        super(width, height);
        this.rng = new Random();
        this.treeCount = treeCount;
        this.aiTanksCount = aiTanksCount;
    }

    public LevelData generateLevel() {
        List<GridPoint2> treePositions = new ArrayList<>();
        List<GridPoint2> tankPositions = new ArrayList<>(aiTanksCount);

        for (int i = 0; i < treeCount; ++i) {
            GridPoint2 position;
            do {
                position = new GridPoint2(rng.nextInt(width), rng.nextInt(height));
            } while (treePositions.contains(position));
            treePositions.add(position);
        }

        for (int i = 0; i < aiTanksCount; ++i) {
            GridPoint2 position;
            do {
                position = new GridPoint2(rng.nextInt(width), rng.nextInt(height));
            } while (tankPositions.contains(position) || treePositions.contains(position));
            tankPositions.add(position);
        }

        // do {
        //     playerPosition = new GridPoint2(rng.nextInt(width), rng.nextInt(height));
        // } while (treePositions.contains(playerPosition) || aiTanksPositions.contains(playerPosition));

        // return new LevelData(playerPosition, treePositions, aiTanksPositions);
        return new LevelData(treePositions, tankPositions);
    }
}
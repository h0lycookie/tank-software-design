package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.util.ObstacleType;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.math.GridPoint2;

public class LevelGeneratorRandom implements LevelGenerator {
    private final int width;
    private final int height;
    private final Map<ObstacleType, Integer> obstaclesCounts;

    public LevelGeneratorRandom(int width, int height, Map<ObstacleType, Integer> obstaclesCounts) {
        this.width = width;
        this.height = height;
        this.obstaclesCounts = obstaclesCounts;
    }

    @Override
    public Map<ObstacleType, Set<GridPoint2>> getUniqueObstaclesPositions() throws IOException {
        return generateUniqueObjectsPositions();
    }

    private Map<ObstacleType, Set<GridPoint2>> generateUniqueObjectsPositions() {
        Map<ObstacleType, Set<GridPoint2>> obstaclesPositionsByType = new HashMap<>();
        Set<GridPoint2> obstaclesUniquePositions = new HashSet<>();

        obstaclesCounts.forEach((obstacleType, obstaclesCountOfType) -> {
            Set<GridPoint2> obstaclesPositionsOfCurrentType = new HashSet<>();
            obstaclesPositionsByType.put(obstacleType, obstaclesPositionsOfCurrentType);

            for (int i = 0; i < obstaclesCountOfType; ++i) {
                GridPoint2 position;
                do {
                    position = generateCoordinates();
                }
                while (obstaclesUniquePositions.contains(position));
                obstaclesUniquePositions.add(position);
                obstaclesPositionsOfCurrentType.add(position);
            }
        });

        return obstaclesPositionsByType;
    }

    private GridPoint2 generateCoordinates() {
        return new GridPoint2(ThreadLocalRandom.current().nextInt(width), ThreadLocalRandom.current().nextInt(height));
    }
}
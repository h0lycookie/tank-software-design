package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.util.ObjectType;

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
    private final Map<ObjectType, Integer> obstaclesCounts;

    public LevelGeneratorRandom(int width, int height, Map<ObjectType, Integer> obstaclesCounts) {
        this.width = width;
        this.height = height;
        this.obstaclesCounts = obstaclesCounts;
    }

    @Override
    public Map<ObjectType, Set<GridPoint2>> getUniqueObjectsPositions() throws IOException {
        return generateUniqueObjectsPositions();
    }

    private Map<ObjectType, Set<GridPoint2>> generateUniqueObjectsPositions() {
        Map<ObjectType, Set<GridPoint2>> objectsPositionsByType = new HashMap<>();
        Set<GridPoint2> objectsUniquePositions = new HashSet<>();

        obstaclesCounts.forEach((objectType, obstaclesCountOfType) -> {
            Set<GridPoint2> objectsPositionsOfCurrentType = new HashSet<>();
            objectsPositionsByType.put(objectType, objectsPositionsOfCurrentType);

            for (int i = 0; i < obstaclesCountOfType; ++i) {
                GridPoint2 position;
                do {
                    position = generateCoordinates();
                }
                while (objectsUniquePositions.contains(position));
                objectsUniquePositions.add(position);
                objectsPositionsOfCurrentType.add(position);
            }
        });

        return objectsPositionsByType;
    }

    private GridPoint2 generateCoordinates() {
        return new GridPoint2(ThreadLocalRandom.current().nextInt(width), ThreadLocalRandom.current().nextInt(height));
    }
}
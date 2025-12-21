package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.util.ObstacleType;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

public interface LevelGenerator {
    public Map<ObstacleType, Set<GridPoint2>> getUniqueObstaclesPositions() throws IOException;
}
package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.util.ObjectType;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.GridPoint2;

public interface LevelGenerator {
    public Map<ObjectType, Set<GridPoint2>> getUniqueObjectsPositions() throws IOException;
}
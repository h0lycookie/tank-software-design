package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.util.ObjectType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LevelGeneratorFileBased implements LevelGenerator {
    private final int width;
    private final int height;
    private final String filePath;

    public LevelGeneratorFileBased(int width, int height, String filePath) {
        this.width = width;
        this.height = height;
        this.filePath = filePath;
    }

    @Override
    public Map<ObjectType, Set<GridPoint2>> getUniqueObjectsPositions() throws IOException {
        Map<ObjectType, Set<GridPoint2>> objectsPositions;
        try {
            objectsPositions = parseLevelMap();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse level Map \"" + filePath + "\"");
        }

        return objectsPositions;
    }

    private Map<ObjectType, Set<GridPoint2>> parseLevelMap() throws IOException {
        Map<ObjectType, Set<GridPoint2>> obstaclesPositions = new HashMap<>();
        List<String> fileLines = getfileLines(filePath);

        boolean foundPlayerPosition = false;
        for (int y = 0; y < height; ++y) {
            String line = fileLines.get(y);
            foundPlayerPosition |= parseLine(line, y, foundPlayerPosition, obstaclesPositions);
        }

        return obstaclesPositions;
    }

    private List<String> getfileLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (width != line.length())
                lines.add(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file \"" + filePath + "\"");
        }
        
        return lines;
    }

    private boolean parseLine(String line, int rowCount, boolean foundPlayerPosition, Map<ObjectType, Set<GridPoint2>> obstaclesPositions) {
        for (int x = 0; x < width; ++x) {
            char symbol = line.charAt(x);
            GridPoint2 position = new GridPoint2(x, (height - 1) - rowCount);

            switch (symbol) {
                case 'T':
                    putByObjectType(ObjectType.TREE, position, obstaclesPositions);
                    break;
                case 'P':
                    if (foundPlayerPosition) {
                        throw new IllegalArgumentException("discovered multiple players positions");
                    }
                    foundPlayerPosition = true;
                    putByObjectType(ObjectType.TANK, position, obstaclesPositions);
                    break;
                case 'X':
                    putByObjectType(ObjectType.TREE, position, obstaclesPositions);
                    break;
                case '_':
                    break;
                default:
                    throw new IllegalArgumentException("Unknown symbol '" + symbol + "' at (" + x + "," + rowCount + ")");
            }
        }

        return foundPlayerPosition;
    }

    private void putByObjectType(ObjectType type, GridPoint2 position, Map<ObjectType, Set<GridPoint2>> obstaclesPositions) {
        Set<GridPoint2> obstaclePositionsByType = obstaclesPositions.get(type);
        if (obstaclePositionsByType == null) {
            obstaclePositionsByType = new HashSet<>();
        }
        obstaclePositionsByType.add(position);
    }
}

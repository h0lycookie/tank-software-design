package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LevelGeneratorFileBased extends LevelGenerator {
    private final String filePath;

    public LevelGeneratorFileBased(int width, int height, String filePath) {
        super(width, height);
        this.filePath = filePath;
    }

    public LevelData generateLevel() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("level-describing file is empty");
        }

        int fileWidth = lines.get(0).length();
        int fileHeight = lines.size();
        if (fileWidth != width || fileHeight != height) {
            throw new IllegalArgumentException("level size mismatch: got (" + width + "," + height +
                    "); expected (" + fileWidth + "," + fileHeight + ")");
        }

        Collection<GridPoint2> treePositions = new ArrayList<>();
        Collection<GridPoint2> aiTankPositions = new ArrayList<>();
        GridPoint2 playerPosition = null;

        for (int y = 0; y < fileHeight; ++y) {
            String line = lines.get(y);
            for (int x = 0; x < fileWidth; ++x) {
                char symbol = line.charAt(x);
                GridPoint2 position = new GridPoint2(x, (height - 1) - y);

                switch (symbol) {
                    case 'T':
                        treePositions.add(position);
                        break;
                    case 'P':
                        if (playerPosition != null) {
                            throw new IllegalArgumentException("discovered multiple players positions");
                        }
                        playerPosition = position;
                        break;
                    case 'X':
                        aiTankPositions.add(position);
                        break;
                    case '_':
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown symbol '" + symbol + "' at (" + x + "," + y + ")");
                }
            }
        }

        if (playerPosition == null) {
            throw new IllegalArgumentException("No player position in file with level design");
        }

        return new LevelData(playerPosition, treePositions, aiTankPositions);
    }
}

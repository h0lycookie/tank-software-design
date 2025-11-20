package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LevelGeneratorFileBasedTest {

    @TempDir
    Path tempDir;

    @Test
    void testGenerateLevel_validFile_correctParsing() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, 
            "P__\n" +
            "_T_\n" +
            "__X"
        );

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 3, levelFile.toString());
        LevelData levelData = generator.generateLevel();

        assertEquals(new GridPoint2(0, 2), levelData.getPlayerPosition());
        
        Collection<GridPoint2> trees = levelData.getTreePositions();
        assertEquals(1, trees.size());
        assertTrue(trees.contains(new GridPoint2(1, 1)));
        
        Collection<GridPoint2> tanks = levelData.getAiTankPositions();
        assertEquals(1, tanks.size());
        assertTrue(tanks.contains(new GridPoint2(2, 0)));
    }

    @Test
    void testGenerateLevel_emptyFile_throwsException() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(0, 0, emptyFile.toString());
        assertThrows(IllegalArgumentException.class, generator::generateLevel);
    }

    @Test
    void testGenerateLevel_sizeMismatch_throwsException() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, "P_");

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 3, levelFile.toString());
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            generator::generateLevel
        );
        assertTrue(exception.getMessage().contains("level size mismatch"));
    }

    @Test
    void testGenerateLevel_multiplePlayers_throwsException() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, 
            "P_\n" +
            "P_"
        );

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(2, 2, levelFile.toString());
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            generator::generateLevel
        );
        assertTrue(exception.getMessage().contains("multiple players positions"));
    }

    @Test
    void testGenerateLevel_unknownSymbol_throwsException() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, "PZ");

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(2, 1, levelFile.toString());
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            generator::generateLevel
        );
        assertTrue(exception.getMessage().contains("Unknown symbol"));
    }

    @Test
    void testGenerateLevel_noPlayer_throwsException() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, "T_X");

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 1, levelFile.toString());
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            generator::generateLevel
        );
        assertTrue(exception.getMessage().contains("No player position"));
    }

    @Test
    void testGenerateLevel_complexLayout_correctParsing() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile,
            "___T\n" +
            "T_P_\n" +
            "X__T\n" +
            "_X__"
        );

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(4, 4, levelFile.toString());
        LevelData levelData = generator.generateLevel();

        assertEquals(new GridPoint2(2, 2), levelData.getPlayerPosition());

        Collection<GridPoint2> trees = levelData.getTreePositions();
        assertEquals(3, trees.size());
        
        Set<GridPoint2> expectedTrees = new HashSet<>();
        expectedTrees.add(new GridPoint2(3, 3));
        expectedTrees.add(new GridPoint2(0, 2));
        expectedTrees.add(new GridPoint2(3, 1));
        assertEquals(expectedTrees, new HashSet<>(trees));

        Collection<GridPoint2> tanks = levelData.getAiTankPositions();
        assertEquals(2, tanks.size());
        
        Set<GridPoint2> expectedTanks = new HashSet<>();
        expectedTanks.add(new GridPoint2(0, 1));
        expectedTanks.add(new GridPoint2(1, 0));
        assertEquals(expectedTanks, new HashSet<>(tanks));
    }

    @Test
    void testGenerateLevel_fileNotFound_throwsIOException() {
        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 3, "nonexistent.txt");
        assertThrows(IOException.class, generator::generateLevel);
    }

    @Test
    void testGenerateLevel_singleLineLevel_correctParsing() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, "PTX");

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 1, levelFile.toString());
        LevelData levelData = generator.generateLevel();

        assertEquals(new GridPoint2(0, 0), levelData.getPlayerPosition());
        
        Collection<GridPoint2> trees = levelData.getTreePositions();
        assertEquals(1, trees.size());
        assertTrue(trees.contains(new GridPoint2(1, 0)));
        
        Collection<GridPoint2> tanks = levelData.getAiTankPositions();
        assertEquals(1, tanks.size());
        assertTrue(tanks.contains(new GridPoint2(2, 0)));
    }

    @Test
    void testGenerateLevel_onlyEmptySpaces_throwsException() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, 
            "___\n" +
            "___\n" +
            "___"
        );

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 3, levelFile.toString());
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            generator::generateLevel
        );
        assertTrue(exception.getMessage().contains("No player position"));
    }

    @Test
    void testGenerateLevel_coordinateTransformation_correct() throws IOException {
        Path levelFile = tempDir.resolve("level.txt");
        Files.writeString(levelFile, 
            "P__\n" +
            "_T_\n" +
            "__X"
        );

        LevelGeneratorFileBased generator = new LevelGeneratorFileBased(3, 3, levelFile.toString());
        LevelData levelData = generator.generateLevel();
        
        assertEquals(new GridPoint2(0, 2), levelData.getPlayerPosition());
        assertEquals(new GridPoint2(1, 1), levelData.getTreePositions().iterator().next());
        assertEquals(new GridPoint2(2, 0), levelData.getAiTankPositions().iterator().next());
    }
}
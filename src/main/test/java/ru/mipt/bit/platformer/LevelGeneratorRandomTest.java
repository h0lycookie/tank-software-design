package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelGeneratorRandomTest {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final int TREES = 5;
    private static final int AI_TANKS = 3;
    
    private LevelGeneratorRandom generator;

    @BeforeEach
    void setUp() {
        generator = new LevelGeneratorRandom(WIDTH, HEIGHT, TREES, AI_TANKS);
    }

    @Test
    void testGenerateLevelCreatesCorrectStructure() {
        LevelData levelData = generator.generateLevel();

        assertNotNull(levelData.getPlayerPosition());
        assertNotNull(levelData.getTreePositions());
        assertNotNull(levelData.getAiTanksPositions());
    }

    @RepeatedTest(10)
    void testGenerateLevelRespectsLevelBounds() {
        LevelData levelData = generator.generateLevel();

        testAssertInBounds(levelData.getPlayerPosition());
        
        for (GridPoint2 tree : levelData.getTreePositions()) {
            testAssertInBounds(tree);
        }
        
        for (GridPoint2 tank : levelData.getAiTanksPositions()) {
            testAssertInBounds(tank);
        }
    }

    @RepeatedTest(10)
    void testGenerateLevelHasCorrectCounts() {
        LevelData levelData = generator.generateLevel();

        assertEquals(TREES, levelData.getTreePositions().size());
        assertEquals(AI_TANKS, levelData.getAiTanksPositions().size());
    }

    @RepeatedTest(10)
    void testGenerateLevelHasUniquePositions() {
        LevelData levelData = generator.generateLevel();

        List<GridPoint2> trees = levelData.getTreePositions();
        List<GridPoint2> tanks = levelData.getAiTanksPositions();
        GridPoint2 player = levelData.getPlayerPosition();

        assertTrue(trees.stream().noneMatch(p -> p.equals(player)));
        assertTrue(tanks.stream().noneMatch(p -> p.equals(player)));
        
        for (GridPoint2 tank : tanks) {
            assertFalse(trees.contains(tank));
        }
    }

    @Test
    void testGenerateLevelHandlesZeroObjects() {
        LevelGeneratorRandom emptyGenerator = new LevelGeneratorRandom(5, 5, 0, 0);
        LevelData levelData = emptyGenerator.generateLevel();

        assertEquals(0, levelData.getTreePositions().size());
        assertEquals(0, levelData.getAiTanksPositions().size());
        testAssertInBounds(levelData.getPlayerPosition());
    }

    @Test
    void testGenerateLevelHandlesFullLevel() {
        int smallGridSize = 2;
        LevelGeneratorRandom fullGenerator = new LevelGeneratorRandom(
            smallGridSize, smallGridSize, 3, 1
        );
        
        assertDoesNotThrow(fullGenerator::generateLevel);
    }

    private void testAssertInBounds(GridPoint2 point) {
        assertTrue(point.x >= 0 && point.x < WIDTH, 
            String.format("X coordinate %d out of bounds [0, %d)", point.x, WIDTH));
        assertTrue(point.y >= 0 && point.y < HEIGHT,
            String.format("Y coordinate %d out of bounds [0, %d)", point.y, HEIGHT));
    }
}
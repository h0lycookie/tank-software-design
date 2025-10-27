package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class LevelGenerator {
    protected final int width;
    protected final int height;

    protected LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract LevelData generateLevel() throws IOException;

    public static class LevelData {
        private final GridPoint2 playerPosition;
        private final Collection<GridPoint2> treePositions;

        public LevelData(GridPoint2 playerPosition, Collection<GridPoint2> treePositions) {
            this.playerPosition = playerPosition;
            this.treePositions = new ArrayList<>(treePositions);
        }

        public GridPoint2 getPlayerPosition() {
            return playerPosition;
        }

        public Collection<GridPoint2> getTreePositions() {
            return new ArrayList<>(treePositions);
        }
    }
}
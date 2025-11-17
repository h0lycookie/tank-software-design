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
        private final Collection<GridPoint2> treePositions;
        private final Collection<GridPoint2> tankPositions;

        public LevelData(Collection<GridPoint2> treePositions, Collection<GridPoint2> tankPositions) {
            this.treePositions = new ArrayList<>(treePositions);
            this.tankPositions = new ArrayList<>(tankPositions);
        }

        public Collection<GridPoint2> getTreePositions() {
            return new ArrayList<>(treePositions);
        }

        public Collection<GridPoint2> getTankPositions() {
            return new ArrayList<>(tankPositions);
        }
    }
}
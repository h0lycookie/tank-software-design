package ru.mipt.bit.platformer;

import org.junit.Test;
import static org.junit.Assert.*;

import com.badlogic.gdx.math.GridPoint2;

public class DirectionTest {

    @Test
    public void testUpGetNewPosition() {
        GridPoint2 position = new GridPoint2(1, 1);
        GridPoint2 newPosition = Direction.UP.getNewPosition(position);
        assertEquals(new GridPoint2(1, 2), newPosition);
    }

    @Test
    public void testDownGetNewPosition() {
        GridPoint2 position = new GridPoint2(1, 1);
        GridPoint2 newPosition = Direction.DOWN.getNewPosition(position);
        assertEquals(new GridPoint2(1, 0), newPosition);
    }

    @Test
    public void testLeftGetNewPosition() {
        GridPoint2 position = new GridPoint2(1, 1);
        GridPoint2 newPosition = Direction.LEFT.getNewPosition(position);
        assertEquals(new GridPoint2(0, 1), newPosition);
    }

    @Test
    public void testRightGetNewPosition() {
        GridPoint2 position = new GridPoint2(1, 1);
        GridPoint2 newPosition = Direction.RIGHT.getNewPosition(position);
        assertEquals(new GridPoint2(2, 1), newPosition);
    }

    @Test
    public void testGetRotation() {
        assertEquals(90f, Direction.UP.getRotation(), 0.01f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0.01f);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0.01f);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0.01f);
    }
}

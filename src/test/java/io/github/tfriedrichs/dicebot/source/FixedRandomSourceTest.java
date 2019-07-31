package io.github.tfriedrichs.dicebot.source;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedRandomSourceTest {

    private static final int[] VALUES = new int[]{0, 1, 2, 3, 4, 5};

    @Test
    void shouldThrowForEmptyBackingArray() {
        assertThrows(IllegalArgumentException.class, () -> new FixedRandomSource(new int[]{}));
    }

    @Test
    void shouldTakeValuesIfBackingArrayIsGreater() {
        FixedRandomSource source = new FixedRandomSource(VALUES);
        assertArrayEquals(new int[]{0, 1, 2}, source.get(3, 0, 0).toArray());
    }

    @Test
    void shouldWrapAroundIfBackingArrayIsSmaller() {
        FixedRandomSource source = new FixedRandomSource(VALUES);
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 0, 1}, source.get(8, 0, 0).toArray());
    }

    @Test
    void shouldContinueWhenConsecutiveCalls() {
        FixedRandomSource source = new FixedRandomSource(VALUES);
        assertArrayEquals(new int[]{0, 1, 2}, source.get(3, 0, 0).toArray());
        assertArrayEquals(new int[]{3, 4, 5, 0, 1}, source.get(5, 0, 0).toArray());
    }

    @Test
    void shouldHandleManyWrappings() {
        FixedRandomSource source = new FixedRandomSource(VALUES);
        assertArrayEquals(IntStream.iterate(0, i -> (i + 1) % 6).limit(200).toArray(),
            source.get(200, 0, 0).toArray());
    }
}

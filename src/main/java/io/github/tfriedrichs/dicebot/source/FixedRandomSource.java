package io.github.tfriedrichs.dicebot.source;

import java.util.stream.IntStream;

/**
 * {@link RandomSource} implementation using a fixed backing array. Consecutive calls of {@link
 * #get(int, int, int)} continue at the last position and wrap around if necessary. This class is
 * mainly useful as a test double.
 *
 * CAUTION: {@link #get(int, int, int)} does not guarantee that the bounds are satisfied.
 */
public class FixedRandomSource implements RandomSource {

    private final int[] source;

    private int currentIndex = 0;

    /**
     * Constructor.
     *
     * @param source The backing array.
     * @throws IllegalArgumentException When the backing array is empty.
     */
    public FixedRandomSource(int... source) {
        if (source.length == 0) {
            throw new IllegalArgumentException("Backing array must contain at least one element.");
        }
        this.source = source;
    }

    @Override
    public IntStream get(int number, int min, int max) {
        int start = currentIndex;
        currentIndex = (currentIndex + number) % source.length;
        return IntStream.iterate(start, i -> (i + 1) % source.length).map(i -> source[i])
            .limit(number);
    }
}

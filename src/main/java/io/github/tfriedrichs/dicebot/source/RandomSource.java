package io.github.tfriedrichs.dicebot.source;

import java.util.stream.IntStream;

/**
 * Interface for sources of random numbers.
 */
public interface RandomSource {

    /**
     * Gets a stream of random numbers between {@code min} (inclusive) and {@code max} (exclusive).
     *
     * @param number Number of random integers in the stream.
     * @param min Minimum value for each element.
     * @param max Maximum value for each element.
     * @return Stream containing random numbers.
     */
    IntStream get(int number, int min, int max);

    /**
     * Gets a single random number between {@code min} (inclusive) and {@code max} (exclusive).
     *
     * @param min Minimum value for each element.
     * @param max Maximum value for each element.
     * @return a random number.
     */
    default int get(int min, int max) {
        return get(1, min, max).limit(1).sum();
    }

}

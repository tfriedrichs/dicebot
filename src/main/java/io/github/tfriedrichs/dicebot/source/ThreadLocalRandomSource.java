package io.github.tfriedrichs.dicebot.source;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Implementation of a {@link RandomSource} using {@link ThreadLocalRandom}.
 */
public class ThreadLocalRandomSource implements RandomSource {

    @Override
    public IntStream get(int number, int min, int max) {
        return ThreadLocalRandom.current().ints(number, min, max);
    }
}

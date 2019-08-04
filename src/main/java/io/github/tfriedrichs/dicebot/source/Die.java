package io.github.tfriedrichs.dicebot.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * A representation a type of die. It consists of a number of sides, a source of randomness,
 * and a mapper which maps from results ranging from 0 to number of sides to the output domain.
 */
public class Die {

    public static final Die D4 = new Die(new ThreadLocalRandomSource(), 4);
    public static final Die D6 = new Die(new ThreadLocalRandomSource(), 6);
    public static final Die D8 = new Die(new ThreadLocalRandomSource(), 8);
    public static final Die D10 = new Die(new ThreadLocalRandomSource(), 10);
    public static final Die D12 = new Die(new ThreadLocalRandomSource(), 12);
    public static final Die D20 = new Die(new ThreadLocalRandomSource(), 20);
    public static final Die D100 = new Die(new ThreadLocalRandomSource(), 100);
    public static final Die FATE = new Die(new ThreadLocalRandomSource(), List.of(-1, 0, 1));

    private final RandomSource randomSource;
    private final int numberOfSides;
    private final IntUnaryOperator resultMapper;
    private final int min;
    private final int max;

    /**
     * Constructor of a die type with given sides.
     *
     * @param randomSource the source of randomness
     * @param sides        the number of side
     */
    public Die(RandomSource randomSource, Collection<Integer> sides) {
        if (sides.isEmpty()) {
            throw new IllegalArgumentException("Number of sides must be positive.");
        }
        List<Integer> choices = new ArrayList<>(sides);
        this.min = choices.stream().mapToInt(Integer::intValue).min().orElseThrow();
        this.max = choices.stream().mapToInt(Integer::intValue).max().orElseThrow();
        this.randomSource = randomSource;
        this.numberOfSides = choices.size();
        this.resultMapper = choices::get;
    }

    /**
     * Constructor of a die type with sides from 0 to {@code numberOfSides}.
     *
     * @param randomSource the source of randomness
     * @param numberOfSides the number of side
     */
    public Die(RandomSource randomSource, int numberOfSides) {
        if (numberOfSides < 1) {
            throw new IllegalArgumentException("Number of sides must be positive.");
        }
        this.min = 1;
        this.max = numberOfSides;
        this.randomSource = randomSource;
        this.numberOfSides = numberOfSides;
        this.resultMapper = i -> i;
    }

    /**
     * Roll this die once.
     *
     * @return the rolled value
     */
    public int roll() {
        return resultMapper.applyAsInt(randomSource.get(1, 1, numberOfSides + 1).sum());
    }

    /**
     * Roll this die for {@code numberOfRolls} times.
     *
     * @param numberOfRolls the number of rolls
     * @return a stream of the rolled values
     */
    public IntStream roll(int numberOfRolls) {
        return randomSource.get(numberOfRolls, 1, numberOfSides + 1).map(resultMapper);
    }

    /**
     * Gets the number of sides.
     *
     * @return the number of sides
     */
    public int getNumberOfSides() {
        return numberOfSides;
    }

    /**
     * Gets the minimum possible value.
     *
     * @return the minimal possible value
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum possible value.
     *
     * @return the maximum possible value
     */
    public int getMax() {
        return max;
    }
}

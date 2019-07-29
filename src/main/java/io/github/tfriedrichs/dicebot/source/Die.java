package io.github.tfriedrichs.dicebot.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

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

    public Die(RandomSource randomSource, int numberOfSides) {
        this.min = 1;
        this.max = numberOfSides;
        this.randomSource = randomSource;
        this.numberOfSides = numberOfSides;
        this.resultMapper = i -> i;
    }

    public int roll() {
        return resultMapper.applyAsInt(randomSource.get(1, 1, numberOfSides + 1).sum());
    }

    public IntStream roll(int numberOfRolls) {
        return randomSource.get(numberOfRolls, 1, numberOfSides + 1).map(resultMapper);
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

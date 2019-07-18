package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class ExplodeModifier implements DiceRollModifier {

    private static final int RECURSION_DEPTH = 1000;

    private final RandomSource randomSource;
    private final IntPredicate explodeIf;

    public ExplodeModifier(RandomSource randomSource, int explodeThreshold) {
        this(randomSource, roll -> roll >= explodeThreshold);
    }

    public ExplodeModifier(RandomSource randomSource,
        IntPredicate explodeIf) {
        this.randomSource = randomSource;
        this.explodeIf = explodeIf;
    }


    @Override
    public int[] modifyRoll(int[] roll, int min, int max) {
        int[] total = Arrays.copyOf(roll, roll.length);
        int[] current = roll;
        int depth = 0;
        while (true) {
            depth++;
            if (depth > RECURSION_DEPTH) {
                throw new RecursionDepthException("Explode recursion depth reached.");
            }
            int numberOfExplosions = Math
                .toIntExact(IntStream.of(current).filter(explodeIf).count());
            if (numberOfExplosions == 0) {
                break;
            }
            current = randomSource.get(numberOfExplosions, min, max).toArray();
            total = IntStream.concat(IntStream.of(total), IntStream.of(current)).toArray();
        }

        return total;
    }

}

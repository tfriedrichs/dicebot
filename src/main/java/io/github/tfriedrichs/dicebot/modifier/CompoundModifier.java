package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;

import java.util.Iterator;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompoundModifier implements DiceRollModifier {

    private static final int RECURSION_DEPTH = 1000;

    private final RandomSource randomSource;
    private final IntPredicate compoundIf;

    public CompoundModifier(RandomSource randomSource, int compoundThreshold) {
        this(randomSource, roll -> roll >= compoundThreshold);
    }

    public CompoundModifier(RandomSource randomSource,
                            IntPredicate compoundIf) {
        this.randomSource = randomSource;
        this.compoundIf = compoundIf;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, int min, int max) {
        DiceRoll total = new DiceRoll(roll);
        Set<Integer> compoundingIndices = IntStream.range(0, roll.getRolls().length)
                .filter(index -> compoundIf.test(roll.getRolls()[index]))
                .boxed()
                .collect(Collectors.toSet());
        int depth = 0;
        while (!compoundingIndices.isEmpty()) {
            depth++;
            if (depth > RECURSION_DEPTH) {
                throw new RecursionDepthException("Compound recursion depth reached.");
            }
            for (Iterator<Integer> iterator = compoundingIndices.iterator(); iterator.hasNext(); ) {
                Integer compoundingIndex = iterator.next();
                int newRoll = randomSource.get(min, max);
                total.getRolls()[compoundingIndex] += newRoll;
                if (!compoundIf.test(newRoll)) {
                    total.addMetaDataToRoll(compoundingIndex, DiceRoll.MetaData.COMPOUNDED);
                    iterator.remove();
                }
            }
        }

        return total;
    }
}

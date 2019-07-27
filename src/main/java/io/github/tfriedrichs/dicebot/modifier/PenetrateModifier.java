package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;

import java.util.function.IntPredicate;

public class PenetrateModifier implements DiceRollModifier {

    private static final int RECURSION_DEPTH = 1000;

    private final RandomSource randomSource;
    private final IntPredicate penetrateIf;

    public PenetrateModifier(RandomSource randomSource, int penetrateThreshold) {
        this(randomSource, roll -> roll >= penetrateThreshold);
    }

    public PenetrateModifier(RandomSource randomSource,
                             IntPredicate penetrateIf) {
        this.randomSource = randomSource;
        this.penetrateIf = penetrateIf;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, int min, int max) {
        DiceRoll total = roll;
        DiceRoll current = roll;
        int depth = 0;
        while (true) {
            depth++;
            if (depth > RECURSION_DEPTH) {
                throw new RecursionDepthException("Penetrate recursion depth reached.");
            }
            int numberOfPenetrations = 0;
            for (int i = 0; i < current.getRolls().length; i++) {
                if (!roll.getMetaDataForRoll(i).contains(DiceRoll.MetaData.DROPPED)
                        && penetrateIf.test(current.getRolls()[i])) {
                    numberOfPenetrations++;
                    current.addMetaDataToRoll(i, DiceRoll.MetaData.EXPLODED);
                }
            }
            if (numberOfPenetrations == 0) {
                break;
            }
            current = new DiceRoll(DiceRoll.MetaData.ADDED,
                    randomSource.get(numberOfPenetrations, min, max).map(i -> i - 1).toArray());
            total = DiceRoll.concat(total, current);
        }

        return total;
    }

}

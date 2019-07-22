package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import java.util.function.IntPredicate;

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
    public DiceRoll modifyRoll(DiceRoll roll, int min, int max) {
        DiceRoll total = roll;
        DiceRoll current = roll;
        int depth = 0;
        while (true) {
            depth++;
            if (depth > RECURSION_DEPTH) {
                throw new RecursionDepthException("Explode recursion depth reached.");
            }
            int numberOfExplosions = 0;
            for (int i = 0; i < current.getRolls().length; i++) {
                if (explodeIf.test(current.getRolls()[i])) {
                    numberOfExplosions++;
                    current.addMetaDataToRoll(i, MetaData.EXPLODED);
                }
            }
            if (numberOfExplosions == 0) {
                break;
            }
            current = new DiceRoll(MetaData.ADDED,
                randomSource.get(numberOfExplosions, min, max).toArray());
            total = DiceRoll.concat(total, current);
        }

        return total;
    }

}

package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import java.util.function.IntPredicate;

public class ExplodeModifier implements DiceRollModifier {

    private static final int RECURSION_DEPTH = 1000;

    private final IntPredicate explodeIf;

    public ExplodeModifier(int explodeThreshold) {
        this(roll -> roll >= explodeThreshold);
    }

    public ExplodeModifier(IntPredicate explodeIf) {
        this.explodeIf = explodeIf;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
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
                if (!roll.getMetaDataForRoll(i).contains(MetaData.DROPPED)
                    && explodeIf.test(current.getRolls()[i])) {
                    numberOfExplosions++;
                    current.addMetaDataToRoll(i, MetaData.EXPLODED);
                }
            }
            if (numberOfExplosions == 0) {
                break;
            }
            current = new DiceRoll(MetaData.ADDED,
                die.roll(numberOfExplosions).toArray());
            total = DiceRoll.concat(total, current);
        }

        return total;
    }

}

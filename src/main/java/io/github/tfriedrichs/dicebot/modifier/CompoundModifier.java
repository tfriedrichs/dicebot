package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.source.Die;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class CompoundModifier implements DiceRollModifier {

    private final int maxDepth;
    private final ComparisonSelector selector;

    public CompoundModifier(int maxDepth, ComparisonSelector selector) {
        this.maxDepth = maxDepth;
        this.selector = selector;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll total = new DiceRoll(roll);
        Set<Integer> compoundingIndices = selector.select(roll)
                .boxed()
                .collect(Collectors.toSet());
        int depth = 0;
        while (!compoundingIndices.isEmpty()) {
            depth++;
            if (depth > maxDepth) {
                return total;
            }
            for (Iterator<Integer> iterator = compoundingIndices.iterator(); iterator.hasNext(); ) {
                Integer compoundingIndex = iterator.next();
                int newRoll = die.roll();
                total.getRolls()[compoundingIndex] += newRoll;
                if (!selector.test(newRoll)) {
                    total.addMetaDataToRoll(compoundingIndex, DiceRoll.MetaData.COMPOUNDED);
                    iterator.remove();
                }
            }
        }

        return total;
    }
}

package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.source.Die;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link DiceRollModifier} that rolls a new die for each selected dice and compounds the results into a single die.
 * If the new die is selected again, then a new dice is rolled up to a maximum depth. This modifier only supports
 * {@link ComparisonSelector}, because {@link io.github.tfriedrichs.dicebot.selector.DirectionSelector} necessarily
 * leads to infinite recursion.
 * <p>
 * Example:
 * <p>
 * The selector selects all dice greater or equal to 5. The initial roll of 4d6 is [5, 2, 6, 4]. The selector selects
 * the indices 0 and 2. Two new rolls [6, 4] are rolled. The 6 is matched again and one new die [2] is rolled.
 * The results are then compounded to the result [13, 2, 10, 4].
 */
public class CompoundModifier implements DiceRollModifier {

    private final int maxDepth;
    private final ComparisonSelector selector;

    /**
     * Constructor.
     *
     * @param maxDepth the maximum compounding depth
     * @param selector the selector
     */
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

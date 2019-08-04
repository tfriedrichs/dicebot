package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.source.Die;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link DiceRollModifier} which rolls a new die for each selected dice subtracting one from the result,\
 * repeating this process until no matches are left or the maximum explode depth has been reached.
 * <p>
 * Example:
 * <p>
 * The selector selects all dice greater or equal to 5. The initial roll of 4d6 is [5, 2, 6, 4]. The selector selects
 * the indices 0 and 2. Two new rolls [6, 4] are rolled. The 6 is matched again and one new die [2] is rolled.
 * The results are then concatenated to the result [5, 2, 6, 4, 5, 4, 1].
 */
public class PenetrateModifier implements DiceRollModifier {

    private final int maxDepth;
    private final ComparisonSelector selector;

    public PenetrateModifier(int maxDepth, ComparisonSelector selector) {
        this.maxDepth = maxDepth;
        this.selector = selector;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll total = roll;
        DiceRoll current = new DiceRoll(roll);
        int depth = 0;
        int previousLength = 0;
        while (depth < maxDepth) {
            depth++;
            Set<Integer> explode = selector.select(current).boxed().collect(Collectors.toSet());
            current = new DiceRoll(DiceRoll.MetaData.ADDED,
                    die.roll(explode.size()).map(i -> i - 1).toArray());
            total = DiceRoll.concat(total, current);
            for (Integer index : explode) {
                total.addMetaDataToRoll(index + previousLength, DiceRoll.MetaData.EXPLODED);
            }
            previousLength = total.getRolls().length;
        }

        return total;
    }

}

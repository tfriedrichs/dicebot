package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.source.Die;

import java.util.Set;
import java.util.stream.Collectors;

public class ExplodeModifier implements DiceRollModifier {

    private final int maxDepth;
    private final ComparisonSelector selector;

    public ExplodeModifier(int maxDepth, ComparisonSelector selector) {
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
            current = new DiceRoll(MetaData.ADDED,
                    die.roll(explode.size()).toArray());
            total = DiceRoll.concat(total, current);
            for (Integer index : explode) {
                total.addMetaDataToRoll(index + previousLength, MetaData.EXPLODED);
            }
            previousLength = total.getRolls().length;
        }

        return total;
    }

}

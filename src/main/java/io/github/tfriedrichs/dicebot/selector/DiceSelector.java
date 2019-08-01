package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface DiceSelector {

    IntStream select(DiceRoll roll);

    default DiceSelector inverse() {
        return roll -> {
            Set<Integer> initial = this.select(roll).boxed().collect(Collectors.toSet());
            return IntStream.range(0, roll.getRolls().length)
                    .filter(index -> !initial.contains(index));
        };
    }

    enum DropMode {
        SKIP,
        IGNORE
    }

}

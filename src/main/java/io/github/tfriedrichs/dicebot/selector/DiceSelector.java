package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Interface for classes that select single dice from a {@link DiceRoll}.
 */
public interface DiceSelector {

    /**
     * Selects dice from a dice roll by some criteria.
     * <p>
     * Returns a stream of indices corresponding to the selected dice.
     *
     * @param roll the roll to select from
     * @return a stream containing the selected indices
     */
    IntStream select(DiceRoll roll);

    /**
     * Invert a given dice selector.
     * <p>
     * This selector returns exactly all indices that have not been selected by the argument.
     *
     * @return an inverted selector.
     */
    default DiceSelector inverse() {
        return roll -> {
            Set<Integer> initial = this.select(roll).boxed().collect(Collectors.toSet());
            return IntStream.range(0, roll.getRolls().length)
                    .filter(index -> !initial.contains(index));
        };
    }

}

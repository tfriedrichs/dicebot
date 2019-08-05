package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;

import java.util.function.BiPredicate;
import java.util.stream.IntStream;

/**
 * {@link DiceSelector} that selects dice by comparing their value against another value
 */
public class ComparisonSelector implements DiceSelector {

    private final DropMode dropMode;
    private final Mode mode;
    private final int comparisonPoint;

    /**
     * Constructor.
     *
     * @param mode            the comparison mode
     * @param dropMode        the drop strategy
     * @param comparisonPoint the value to compare against
     */
    public ComparisonSelector(Mode mode,
        DropMode dropMode, int comparisonPoint) {
        this.mode = mode;
        this.comparisonPoint = comparisonPoint;
        this.dropMode = dropMode;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        IntStream indices = IntStream.range(0, roll.getRolls().length);
        if (dropMode == DropMode.SKIP) {
            indices = indices
                .filter(index -> !roll.getMetaDataForRoll(index).contains(MetaData.DROPPED));
        }
        return indices
            .filter(index -> mode.test(roll.getRolls()[index], comparisonPoint));
    }

    /**
     * Tests whether a value would be selected by this selector.
     *
     * @param value the value to test
     * @return if it would be selected
     */
    public boolean test(int value) {
        return mode.test(value, comparisonPoint);
    }

    /**
     * Enum for the comparision mode.
     */
    public enum Mode {
        LESSER((i, t) -> i < t),
        LESSER_EQUALS((i, t) -> i <= t),
        EQUALS(Integer::equals),
        GREATER_EQUALS((i, t) -> i >= t),
        GREATER((i, t) -> i > t);

        private final BiPredicate<Integer, Integer> operator;

        /**
         * Constructor.
         *
         * @param operator the relation operator
         */
        Mode(BiPredicate<Integer, Integer> operator) {
            this.operator = operator;
        }

        /**
         * Whether a given number satisfies the relation compared to the comparison point.
         *
         * @param number the number to test
         * @param comparisonPoint the number to test against
         * @return whether the relation is satisfied
         */
        boolean test(int number, int comparisonPoint) {
            return operator.test(number, comparisonPoint);
        }


    }
}

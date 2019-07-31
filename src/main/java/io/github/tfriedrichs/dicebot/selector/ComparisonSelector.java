package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class ComparisonSelector implements DiceSelector {

    private final Mode mode;
    private final int comparisonPoint;

    public ComparisonSelector(Mode mode,
        int comparisonPoint) {
        this.mode = mode;
        this.comparisonPoint = comparisonPoint;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        return IntStream.range(0, roll.getRolls().length)
            .filter(index -> mode.test(roll.getRolls()[index], comparisonPoint));
    }

    public enum Mode {
        LESSER((i, t) -> i < t),
        LESSER_EQUALS((i, t) -> i <= t),
        EQUALS(Integer::equals),
        GREATER_EQUALS((i, t) -> i >= t),
        GREATER((i, t) -> i > t);

        private final BiPredicate<Integer, Integer> operator;

        Mode(BiPredicate<Integer, Integer> operator) {
            this.operator = operator;
        }

        boolean test(int number, int comparisonPoint) {
            return operator.test(number, comparisonPoint);
        }


    }
}

package io.github.tfriedrichs.dicebot.operator;

import java.util.function.DoubleToIntFunction;

/**
 * Enum for a rounding strategy.
 * <p>
 * This is needed because {@link io.github.tfriedrichs.dicebot.expression.DiceExpression} can only handle integer
 * values, while some operators like division may produce non-integer results.
 */
public enum RoundingStrategy {
    DOWN(a -> (int) Math.floor(a)),
    UP(a -> (int) Math.ceil(a)),
    NEAREST(a -> (int) Math.round(a));

    private final DoubleToIntFunction function;

    /**
     * Constructor.
     *
     * @param function the underlying rounding function
     */
    RoundingStrategy(DoubleToIntFunction function) {
        this.function = function;
    }

    /**
     * Round a given number.
     *
     * @param number the number to round
     * @return the rounded result.
     */
    public int apply(double number) {
        return function.applyAsInt(number);
    }
}

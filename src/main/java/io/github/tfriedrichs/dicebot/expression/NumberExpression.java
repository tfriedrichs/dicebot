package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.NumberResult;

/**
 * {@link DiceExpression} representing a single integer.
 * <p>
 * The result of a {@link #roll()} is calculated by returning the given value.
 */
public class NumberExpression implements DiceExpression {

    private final int value;

    /**
     * Constructor.
     *
     * @param value the value to represent
     */
    public NumberExpression(int value) {
        this.value = value;
    }

    @Override
    public DiceResult roll() {
        return new NumberResult(value);
    }
}

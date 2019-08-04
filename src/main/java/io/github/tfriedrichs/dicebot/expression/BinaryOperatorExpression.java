package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult;
import io.github.tfriedrichs.dicebot.result.DiceResult;

/**
 * {@link DiceExpression} for representing binary operators. This includes infix operators like {@code +}
 * or prefix functions like {@code max}.
 * <p>
 * The result of a {@link #roll()} is calculated by applying the operator on the rolled values of the operands.
 * This result is rounded according to the given rounding strategy if necessary.
 */
public class BinaryOperatorExpression implements DiceExpression {

    private final RoundingStrategy roundingStrategy;
    private final BinaryOperator operator;
    private final DiceExpression left;
    private final DiceExpression right;

    /**
     * Constructor.
     *
     * @param roundingStrategy the rounding strategy
     * @param operator         the binary operator
     * @param left             the left operand
     * @param right            the right operand
     */
    public BinaryOperatorExpression(RoundingStrategy roundingStrategy,
                                    BinaryOperator operator, DiceExpression left,
                                    DiceExpression right) {
        this.roundingStrategy = roundingStrategy;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public DiceResult roll() {
        return new BinaryOperatorResult(roundingStrategy, operator, left.roll(), right.roll());
    }
}

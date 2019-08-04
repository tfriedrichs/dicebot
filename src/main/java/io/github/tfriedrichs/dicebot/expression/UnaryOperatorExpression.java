package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.UnaryOperator;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.UnaryOperatorResult;

/**
 * {@link DiceExpression} for representing unary operators. This includes currently only prefix operators.
 * <p>
 * The result of a {@link #roll()} is calculated by applying the operator on the rolled value of the operand.
 * This result is rounded according to the given rounding strategy if necessary.
 */
public class UnaryOperatorExpression implements DiceExpression {

    private final UnaryOperator operator;
    private final DiceExpression inner;

    /**
     * Constructor.
     *
     * @param operator the unary operator
     * @param inner    the operand
     */
    public UnaryOperatorExpression(
            UnaryOperator operator,
            DiceExpression inner) {
        this.operator = operator;
        this.inner = inner;
    }

    @Override
    public DiceResult roll() {
        return new UnaryOperatorResult(operator, inner.roll());
    }
}

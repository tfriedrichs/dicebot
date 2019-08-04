package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;

/**
 * {@link DiceResult} from rolling a {@link io.github.tfriedrichs.dicebot.expression.BinaryOperatorExpression}.
 */
public class BinaryOperatorResult implements DiceResult {

    private final RoundingStrategy roundingStrategy;
    private final BinaryOperator operator;
    private final DiceResult left;
    private final DiceResult right;

    /**
     * Constructor.
     *
     * @param roundingStrategy the rounding strategy
     * @param operator         the operator of the expression
     * @param left             the result of the left operand
     * @param right            the result of the right operand
     */
    public BinaryOperatorResult(RoundingStrategy roundingStrategy,
                                BinaryOperator operator, DiceResult left,
                                DiceResult right) {
        this.roundingStrategy = roundingStrategy;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getValue() {
        return operator.apply(roundingStrategy, left.getValue(), right.getValue());
    }

    @Override
    public <T> T accept(DiceResultVisitor<T> visitor) {
        return visitor.visitBinaryOperator(this);
    }

    @Override
    public int getPrecedence() {
        return this.operator.getPrecedence();
    }

    /**
     * Gets the operator.
     *
     * @return the operator
     */
    public BinaryOperator getOperator() {
        return operator;
    }

    /**
     * Gets the result of the left operand.
     *
     * @return the result of the left operand
     */
    public DiceResult getLeft() {
        return left;
    }

    /**
     * Gets the result of the right operand.
     *
     * @return the result of the right operand
     */
    public DiceResult getRight() {
        return right;
    }

    @Override
    public String toString() {
        return operator + "(" + left + ", " + right + ")";
    }
}

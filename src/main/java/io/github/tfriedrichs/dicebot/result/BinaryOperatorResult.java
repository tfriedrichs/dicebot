package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;

public class BinaryOperatorResult implements DiceResult {

    private final RoundingStrategy roundingStrategy;
    private final BinaryOperator operator;
    private final DiceResult left;
    private final DiceResult right;

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

    public BinaryOperator getOperator() {
        return operator;
    }

    public DiceResult getLeft() {
        return left;
    }

    public DiceResult getRight() {
        return right;
    }

    @Override
    public String toString() {
        return operator + "(" + left + ", " + right + ")";
    }
}

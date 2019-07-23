package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult;
import io.github.tfriedrichs.dicebot.result.DiceResult;

public class BinaryOperatorExpression implements DiceExpression {

    private final RoundingStrategy roundingStrategy;
    private final BinaryOperator operator;
    private final DiceExpression left;
    private final DiceExpression right;

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

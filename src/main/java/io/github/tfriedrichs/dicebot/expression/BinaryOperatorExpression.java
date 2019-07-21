package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult;
import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult.Operator;
import io.github.tfriedrichs.dicebot.result.DiceResult;

public class BinaryOperatorExpression implements DiceExpression {

    private final Operator operator;
    private final DiceExpression left;
    private final DiceExpression right;

    public BinaryOperatorExpression(
        Operator operator, DiceExpression left,
        DiceExpression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public DiceResult roll() {
        return new BinaryOperatorResult(operator, left.roll(), right.roll());
    }
}

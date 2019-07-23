package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.UnaryOperator;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.UnaryOperatorResult;

public class UnaryOperatorExpression implements DiceExpression {

    private final UnaryOperator operator;
    private final DiceExpression inner;

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

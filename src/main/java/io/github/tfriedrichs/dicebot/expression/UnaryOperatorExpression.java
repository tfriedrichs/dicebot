package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.UnaryOperatorResult;
import io.github.tfriedrichs.dicebot.result.UnaryOperatorResult.Operator;

public class UnaryOperatorExpression implements DiceExpression {

    private final Operator operator;
    private final DiceExpression inner;

    public UnaryOperatorExpression(
        Operator operator,
        DiceExpression inner) {
        this.operator = operator;
        this.inner = inner;
    }

    @Override
    public DiceResult roll() {
        return new UnaryOperatorResult(operator, inner.roll());
    }
}

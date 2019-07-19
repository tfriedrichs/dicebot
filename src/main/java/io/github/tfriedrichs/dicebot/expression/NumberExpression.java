package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.DiceRoll;
import io.github.tfriedrichs.dicebot.evaluator.SumEvaluator;

public class NumberExpression implements DiceExpression {

    private final int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    @Override
    public DiceRoll roll() {
        return new DiceRoll(new SumEvaluator(), value);
    }
}

package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.NumberResult;

public class NumberExpression implements DiceExpression {

    private final int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    @Override
    public DiceResult roll() {
        return new NumberResult(value);
    }
}

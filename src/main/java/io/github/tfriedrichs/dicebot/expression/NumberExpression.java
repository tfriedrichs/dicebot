package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.DiceRollResult;
import io.github.tfriedrichs.dicebot.result.FixedNumberResult;

public class NumberExpression implements DiceExpression {

    private final int value;

    public NumberExpression(int value) {
        this.value = value;
    }

    @Override
    public DiceRollResult roll() {
        return new FixedNumberResult(value);
    }
}

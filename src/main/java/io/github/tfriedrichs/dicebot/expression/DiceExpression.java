package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult.Operator;
import io.github.tfriedrichs.dicebot.result.DiceResult;


public interface DiceExpression {

    DiceResult roll();

    static DiceExpression plus(DiceExpression left, DiceExpression right) {
        return new BinaryOperatorExpression(Operator.PLUS, left, right);
    }

    static DiceExpression minus(DiceExpression left, DiceExpression right) {
        return new BinaryOperatorExpression(Operator.MINUS, left, right);
    }

    static DiceExpression times(DiceExpression left, DiceExpression right) {
        return new BinaryOperatorExpression(Operator.TIMES, left, right);
    }

    static DiceExpression number(int value) {
        return new NumberExpression(value);
    }

}

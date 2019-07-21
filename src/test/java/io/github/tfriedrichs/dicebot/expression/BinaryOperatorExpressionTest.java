package io.github.tfriedrichs.dicebot.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult.Operator;
import org.junit.jupiter.api.Test;

class BinaryOperatorExpressionTest {

    @Test
    void plusShouldAdd() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression result = new BinaryOperatorExpression(Operator.PLUS, left, right);
        assertEquals(15, result.roll().getValue());
    }

    @Test
    void minusShouldSubtract() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression result = new BinaryOperatorExpression(Operator.MINUS, left, right);
        assertEquals(-9, result.roll().getValue());
    }


    @Test
    void timesShouldMultiply() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression result = new BinaryOperatorExpression(Operator.TIMES, left, right);
        assertEquals(36, result.roll().getValue());
    }

}
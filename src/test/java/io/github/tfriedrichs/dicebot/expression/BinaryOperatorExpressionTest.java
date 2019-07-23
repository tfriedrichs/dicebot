package io.github.tfriedrichs.dicebot.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import org.junit.jupiter.api.Test;

class BinaryOperatorExpressionTest {

    @Test
    void plusShouldAdd() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.DOWN,
            BinaryOperator.PLUS, left, right);
        assertEquals(15, expression.roll().getValue());
    }

    @Test
    void minusShouldSubtract() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.DOWN,
            BinaryOperator.MINUS, left, right);
        assertEquals(-9, expression.roll().getValue());
    }


    @Test
    void timesShouldMultiply() {
        DiceExpression left = new NumberExpression(3);
        DiceExpression right = new NumberExpression(12);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.DOWN,
            BinaryOperator.TIMES, left, right);
        assertEquals(36, expression.roll().getValue());
    }

    @Test
    void divideShouldDivide() {
        DiceExpression left = new NumberExpression(12);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.DOWN,
            BinaryOperator.DIVIDE, left, right);
        assertEquals(2, expression.roll().getValue());
    }

    @Test
    void roundDownShouldRoundDown() {
        DiceExpression left = new NumberExpression(13);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.DOWN,
            BinaryOperator.DIVIDE, left, right);
        assertEquals(2, expression.roll().getValue());
    }

    @Test
    void roundUpShouldRoundDown() {
        DiceExpression left = new NumberExpression(13);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.UP,
            BinaryOperator.DIVIDE, left, right);
        assertEquals(3, expression.roll().getValue());
    }

    @Test
    void roundNearestShouldRoundDownIfCloser() {
        DiceExpression left = new NumberExpression(13);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.NEAREST,
            BinaryOperator.DIVIDE, left, right);
        assertEquals(2, expression.roll().getValue());
    }

    @Test
    void roundNearestShouldRoundUpIfCloser() {
        DiceExpression left = new NumberExpression(15);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.NEAREST,
            BinaryOperator.DIVIDE, left, right);
        assertEquals(3, expression.roll().getValue());
    }

    @Test
    void maxShouldTakeMaximum() {
        DiceExpression left = new NumberExpression(14);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.NEAREST,
            BinaryOperator.MAX, left, right);
        assertEquals(14, expression.roll().getValue());
    }

    @Test
    void maxShouldTakeMinimum() {
        DiceExpression left = new NumberExpression(14);
        DiceExpression right = new NumberExpression(6);
        DiceExpression expression = new BinaryOperatorExpression(RoundingStrategy.NEAREST,
            BinaryOperator.MIN, left, right);
        assertEquals(6, expression.roll().getValue());
    }

}
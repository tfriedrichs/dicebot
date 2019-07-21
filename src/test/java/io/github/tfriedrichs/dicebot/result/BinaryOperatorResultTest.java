package io.github.tfriedrichs.dicebot.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult.Operator;
import org.junit.jupiter.api.Test;

class BinaryOperatorResultTest {

    @Test
    void plusShouldAdd() {
        DiceResult left = new FixedNumberResult(3);
        DiceResult right = new FixedNumberResult(12);
        DiceResult result = new BinaryOperatorResult(Operator.PLUS, left, right);
        assertEquals(15, result.getValue());
    }

    @Test
    void minusShouldSubtract() {
        DiceResult left = new FixedNumberResult(3);
        DiceResult right = new FixedNumberResult(12);
        DiceResult result = new BinaryOperatorResult(Operator.MINUS, left, right);
        assertEquals(-9, result.getValue());
    }


    @Test
    void timesShouldMultiply() {
        DiceResult left = new FixedNumberResult(3);
        DiceResult right = new FixedNumberResult(12);
        DiceResult result = new BinaryOperatorResult(Operator.TIMES, left, right);
        assertEquals(36, result.getValue());
    }

}
package io.github.tfriedrichs.dicebot.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.result.BinaryOperatorResult.Operator;
import org.junit.jupiter.api.Test;

class BinaryOperatorResultTest {

    @Test
    void plusShouldAdd() {
        DiceRollResult left = new FixedNumberResult(3);
        DiceRollResult right = new FixedNumberResult(12);
        DiceRollResult result = new BinaryOperatorResult(Operator.PLUS, left, right);
        assertEquals(15, result.evaluate());
    }

    @Test
    void minusShouldSubtract() {
        DiceRollResult left = new FixedNumberResult(3);
        DiceRollResult right = new FixedNumberResult(12);
        DiceRollResult result = new BinaryOperatorResult(Operator.MINUS, left, right);
        assertEquals(-9, result.evaluate());
    }


    @Test
    void timesShouldMultiply() {
        DiceRollResult left = new FixedNumberResult(3);
        DiceRollResult right = new FixedNumberResult(12);
        DiceRollResult result = new BinaryOperatorResult(Operator.TIMES, left, right);
        assertEquals(36, result.evaluate());
    }

}
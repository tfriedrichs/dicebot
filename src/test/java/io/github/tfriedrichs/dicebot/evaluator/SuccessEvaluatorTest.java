package io.github.tfriedrichs.dicebot.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import org.junit.jupiter.api.Test;

class SuccessEvaluatorTest {

    @Test
    void shouldReturnZeroForNoSuccesses() {
        DiceRoll roll = new DiceRoll(0, 1, 2, 3, 4);
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnZeroForEmptyRoll() {
        DiceRoll roll = new DiceRoll();
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnNumberOfSuccesses() {
        DiceRoll roll = new DiceRoll(0, 1, 5, 3, 6);
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(2, evaluator.evaluate(roll));
    }

}
package io.github.tfriedrichs.dicebot.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuccessEvaluatorTest {

    @Test
    void shouldReturnZeroForNoSuccesses() {
        int[] roll = new int[]{0, 1, 2, 3, 4};
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnZeroForEmptyRoll() {
        int[] roll = new int[]{};
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnNumberOfSuccesses() {
        int[] roll = new int[]{0, 1, 5, 3, 6};
        SuccessEvaluator evaluator = new SuccessEvaluator(5);
        assertEquals(2, evaluator.evaluate(roll));
    }

}
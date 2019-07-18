package io.github.tfriedrichs.dicebot.evaluator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SumEvaluatorTest {

    @Test
    void shouldReturnZeroForEmptyRoll() {
        int[] roll = new int[]{};
        SumEvaluator evaluator = new SumEvaluator();
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnSum() {
        int[] roll = new int[]{0, 1, 5, 3, 6};
        SumEvaluator evaluator = new SumEvaluator();
        assertEquals(15, evaluator.evaluate(roll));
    }

}
package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumEvaluatorTest {

    @Test
    void shouldReturnZeroForEmptyRoll() {
        DiceRoll roll = new DiceRoll();
        SumEvaluator evaluator = new SumEvaluator();
        assertEquals(0, evaluator.evaluate(roll));
    }

    @Test
    void shouldReturnSum() {
        DiceRoll roll = new DiceRoll(0, 1, 5, 3, 6);
        SumEvaluator evaluator = new SumEvaluator();
        assertEquals(15, evaluator.evaluate(roll));
    }

}
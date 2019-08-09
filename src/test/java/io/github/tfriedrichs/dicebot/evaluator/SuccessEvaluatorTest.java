package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.modifier.SuccessModifier;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import io.github.tfriedrichs.dicebot.source.Die;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuccessEvaluatorTest {

    @Test
    void shouldReturnZeroForNoSuccesses() {
        DiceRoll roll = new DiceRoll(0, 1, 2, 3, 4);
        SuccessEvaluator evaluator = new SuccessEvaluator();
        SuccessModifier modifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        assertEquals(0, evaluator.evaluate(modifier.modifyRoll(roll, Die.D20)));
    }

    @Test
    void shouldReturnZeroForEmptyRoll() {
        DiceRoll roll = new DiceRoll();
        SuccessEvaluator evaluator = new SuccessEvaluator();
        SuccessModifier modifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        assertEquals(0, evaluator.evaluate(modifier.modifyRoll(roll, Die.D20)));
    }

    @Test
    void shouldReturnNumberOfSuccesses() {
        DiceRoll roll = new DiceRoll(0, 1, 5, 3, 6);
        SuccessModifier modifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        SuccessEvaluator evaluator = new SuccessEvaluator();
        assertEquals(2, evaluator.evaluate(modifier.modifyRoll(roll, Die.D20)));
    }

    @Test
    void shouldIgnoreDroppedDice() {
        DiceRoll roll = new DiceRoll(0, 1, 5, 3, 6);
        roll.addMetaDataToRoll(2, DiceRoll.MetaData.DROPPED);
        SuccessModifier modifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        SuccessEvaluator evaluator = new SuccessEvaluator();
        assertEquals(1, evaluator.evaluate(modifier.modifyRoll(roll, Die.D20)));
    }

}
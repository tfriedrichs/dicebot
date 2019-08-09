package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.modifier.FailureModifier;
import io.github.tfriedrichs.dicebot.modifier.SuccessModifier;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import io.github.tfriedrichs.dicebot.source.Die;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuccessFailureEvaluatorTest {

    @Test
    void shouldReturnZeroForNoSuccessesAndNoFailures() {
        DiceRoll roll = new DiceRoll(3, 2, 2, 3, 4);
        SuccessFailureEvaluator evaluator = new SuccessFailureEvaluator();
        SuccessModifier successModifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        FailureModifier failureModifier = new FailureModifier(
            new ComparisonSelector(ComparisonSelector.Mode.LESSER_EQUALS,
                DropMode.SKIP, 1
            ));
        DiceRoll modified = successModifier.modifyRoll(failureModifier.modifyRoll(roll, Die.D20), Die.D20);
        assertEquals(0, evaluator.evaluate(modified));
    }

    @Test
    void shouldReturnZeroForEmptyRoll() {
        DiceRoll roll = new DiceRoll();
        SuccessFailureEvaluator evaluator = new SuccessFailureEvaluator();
        SuccessModifier successModifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        FailureModifier failureModifier = new FailureModifier(
            new ComparisonSelector(ComparisonSelector.Mode.LESSER_EQUALS,
                DropMode.SKIP, 1
            ));
        DiceRoll modified = successModifier.modifyRoll(failureModifier.modifyRoll(roll, Die.D20), Die.D20);
        assertEquals(0, evaluator.evaluate(modified));
    }

    @Test
    void shouldReturnNumberOfSuccessesMinusNumberOfFailures() {
        DiceRoll roll = new DiceRoll(1, 5, 5, 1, 1, 2, 3);
        SuccessFailureEvaluator evaluator = new SuccessFailureEvaluator();
        SuccessModifier successModifier = new SuccessModifier(
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        FailureModifier failureModifier = new FailureModifier(
            new ComparisonSelector(ComparisonSelector.Mode.LESSER_EQUALS,
                DropMode.SKIP, 1
            ));
        DiceRoll modified = successModifier.modifyRoll(failureModifier.modifyRoll(roll, Die.D20), Die.D20);
        assertEquals(-1, evaluator.evaluate(modified));
    }

}
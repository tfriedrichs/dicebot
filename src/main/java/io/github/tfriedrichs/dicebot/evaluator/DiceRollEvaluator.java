package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;


/**
 * Interface for classes that reduce a {@link DiceRoll} to an single number.
 * <p>
 * This number can then be used in further calculations.
 */
public interface DiceRollEvaluator {

    /**
     * Evaluate a given dice roll to a single number.
     * This should not modify the roll in any way.
     *
     * @param roll the given roll
     * @return the result of the evaluation
     */
    int evaluate(DiceRoll roll);

}

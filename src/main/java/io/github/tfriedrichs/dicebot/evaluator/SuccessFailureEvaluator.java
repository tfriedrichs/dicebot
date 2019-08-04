package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

/**
 * {@link DiceRollEvaluator} which counts the number of successes and subtracts the number of failures.
 */
public class SuccessFailureEvaluator extends CountingEvaluator {

    /**
     * Constructor.
     */
    public SuccessFailureEvaluator() {
        super(DiceRoll.MetaData.SUCCESS, DiceRoll.MetaData.FAILURE);
    }

}

package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

/**
 * {@link DiceRollEvaluator} which counts the occurrence of successes in a roll.
 */
public class SuccessEvaluator extends CountingEvaluator {

    /**
     * Constructor.
     */
    public SuccessEvaluator() {
        super(DiceRoll.MetaData.SUCCESS);
    }

}

package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

public class SuccessFailureEvaluator extends CountingEvaluator {

    public SuccessFailureEvaluator() {
        super(DiceRoll.MetaData.SUCCESS, DiceRoll.MetaData.FAILURE);
    }

}

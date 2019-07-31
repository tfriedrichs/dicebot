package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

public class SuccessEvaluator extends CountingEvaluator {

    public SuccessEvaluator() {
        super(DiceRoll.MetaData.SUCCESS);
    }

}

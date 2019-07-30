package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

public class FailureSuccessEvaluator implements DiceRollEvaluator {

    private final SuccessEvaluator successEvaluator;
    private final FailureEvaluator failureEvaluator;

    public FailureSuccessEvaluator(
        SuccessEvaluator successEvaluator,
        FailureEvaluator failureEvaluator) {
        this.successEvaluator = successEvaluator;
        this.failureEvaluator = failureEvaluator;
    }

    @Override
    public int evaluate(DiceRoll roll) {
        return successEvaluator.evaluate(roll) + failureEvaluator.evaluate(roll);
    }
}

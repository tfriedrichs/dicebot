package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import java.util.function.IntPredicate;

public class FailureEvaluator implements DiceRollEvaluator {

    private final IntPredicate failureId;

    public FailureEvaluator(int failureThreshold) {
        this(roll -> roll <= failureThreshold);
    }

    public FailureEvaluator(IntPredicate failureId) {
        this.failureId = failureId;
    }

    @Override
    public int evaluate(DiceRoll roll) {
        int result = 0;
        int[] rolls = roll.getRolls();
        for (int i = 0; i < rolls.length; i++) {
            if (!roll.getMetaDataForRoll(i).contains(MetaData.DROPPED)
                && failureId.test(rolls[i])) {
                roll.addMetaDataToRoll(i, MetaData.FAILURE);
                result--;
            }
        }
        return result;
    }

}

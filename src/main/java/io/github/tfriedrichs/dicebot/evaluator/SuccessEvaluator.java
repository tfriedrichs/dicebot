package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import java.util.function.IntPredicate;

public class SuccessEvaluator implements DiceRollEvaluator {

    private final IntPredicate successIf;

    public SuccessEvaluator(int successThreshold) {
        this(roll -> roll >= successThreshold);
    }

    public SuccessEvaluator(IntPredicate successIf) {
        this.successIf = successIf;
    }

    @Override
    public int evaluate(DiceRoll roll) {
        int result = 0;
        int[] rolls = roll.getRolls();
        for (int i = 0; i < rolls.length; i++) {
            if (!roll.getMetaDataForRoll(i).contains(MetaData.DROPPED)
                && successIf.test(rolls[i])) {
                roll.addMetaDataToRoll(i, MetaData.SUCCESS);
                result++;
            }
        }
        return result;
    }

}

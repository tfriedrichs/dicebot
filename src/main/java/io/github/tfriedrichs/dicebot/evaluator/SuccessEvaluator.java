package io.github.tfriedrichs.dicebot.evaluator;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class SuccessEvaluator implements DiceRollEvaluator {

    private final IntPredicate successIf;

    public SuccessEvaluator(int successThreshold) {
        this(roll -> roll >= successThreshold);
    }

    public SuccessEvaluator(IntPredicate successIf) {
        this.successIf = successIf;
    }

    @Override
    public int evaluate(int[] rolls) {
        return Math.toIntExact(IntStream.of(rolls).filter(successIf).count());
    }

}

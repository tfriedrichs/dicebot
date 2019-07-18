package io.github.tfriedrichs.dicebot.evaluator;

import java.util.stream.IntStream;

public class SumEvaluator implements DiceRollEvaluator {

    @Override
    public int evaluate(int[] rolls) {
        return IntStream.of(rolls).sum();
    }
}
